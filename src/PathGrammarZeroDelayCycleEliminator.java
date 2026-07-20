import java.util.*;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.JFactory;

public class PathGrammarZeroDelayCycleEliminator {

    private static int tmpCounter = 0; // 用于生成应对特殊语法的中间临时变量

    /**
     * 核心公开方法：消除文法中的所有零延迟环 (Zero-Delay Cycles)
     * @return true 如果文法中的产生式被修改、添加或删除；否则返回 false
     */
    public static boolean eliminateCycles(PathGrammar pg) {
        boolean changed = false;

        // Phase 1: 合并无条件瞬时转移构成的强连通分量 (SCCs)
        changed |= phase1_mergeUnconditionalSCCs(pg);

        // Phase 2: 采用 BDD 符号化引擎计算闭包旁路，打破有条件的瞬时死循环
        changed |= phase2_breakConditionalCycles(pg);

        return changed;
    }

    // ====================================================================================
    // --- Phase 1: 无条件 SCC 合并 (纯 AST 拓扑操作) ---
    // ====================================================================================

    private static boolean phase1_mergeUnconditionalSCCs(PathGrammar pg) {
        boolean changed = false;

        //收集无条件瞬时转移边
        List<PathGrammarProduction> uncondEdges = new ArrayList<>();
        for (PathGrammarProduction p : pg.productions) {
            checkDeprecatedType(p);
            if (p.type == PathGrammarProduction.Type.Variable) {
                uncondEdges.add(p);
            }
        }

        List<List<String>> sccs = findSCCs(new ArrayList<>(pg.getVariables()), uncondEdges);

        Set<PathGrammarProduction> toRemove = new HashSet<>();
        Set<PathGrammarProduction> toAdd = new HashSet<>();

        for (List<String> scc : sccs) {
            if (scc.size() > 1) {
                String rep = scc.contains(pg.start) ? pg.start : scc.get(0); // 保留下的代表性结点

                for (String node : scc) {
                    if (!node.equals(rep)) { //当前结点node不是代表结点
                        for (PathGrammarProduction p : pg.productions) {
                            boolean isModified = false;
                            String newLeft = p.leftVariable;

                            if (newLeft.equals(node)) {
                                newLeft = rep;  //指向代表结点
                                isModified = true; //表示p的左变量是node
                            }

                            if (isModified || referencesVariable(p, node)) { // p定义node，或者p引用node
                                toRemove.add(p);
                                PathGrammarProduction newP = createReplacedProduction(p, node, rep, newLeft); //将原来指向node的产生式重定向到rep，将原来从node出发的产生式改为从rep出发
                                if (newP != null) {
                                    toAdd.add(newP);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!toRemove.isEmpty()) {
            pg.productions.removeAll(toRemove);
            changed = true;
        }
        if (!toAdd.isEmpty()) {
            pg.productions.addAll(toAdd);
            changed = true;
        }

        // 清理合并后产生的无条件瞬时自环 (r -> r)
        boolean removedSelfLoops = pg.productions.removeIf(p -> p.type == PathGrammarProduction.Type.Variable
                && p.leftVariable.equals(p.rightItem1));
        changed |= removedSelfLoops;

        // SCC合并可能也会导致某些节点断连，清理一下死代码
        changed |= removeUnreachableProductions(pg);

        return changed;
    }

    // ====================================================================================
    // --- Phase 2: 基于 JavaBDD 的符号化有条件闭包短路 ---
    // ====================================================================================

    private static boolean phase2_breakConditionalCycles(PathGrammar pg) {
        boolean changed = false;
        // 初始化 BDD 翻译上下文 (维持生命周期，直到方法结束内存回收)
        BddContext ctx = new BddContext();

        while (true) {
            PathGrammarProduction backEdge = findOneBackEdge(pg);
            if (backEdge == null) break;

            bypassAndRemoveBackEdge(backEdge, pg, ctx);
            changed = true; // 只要处理了回边，就一定发生了改变
        }

        return changed;
    }

    /**
     * 在有条件的瞬时转移（Zero-Delay）中，通过计算“可达闭包”来构建旁路（Bypass），从而安全地打破并移除导致死循环的回边（Back Edge）。我们可以把这个过程想象成“修建高架桥来绕过环岛死胡同”：如果一条路（回边）会把你带入一个瞬间就能跑完但可能永远绕不出来的环岛（零延迟环），这个函数会计算出从这个环岛所有可能的“真实出口”（耗时产生式或零延迟终端产生式），然后直接从入口修一条直达这些出口的高架桥（旁路），并把原先那条通往死胡同的路拆掉。
     * @param backEdge
     * @param pg
     * @param ctx
     */
    private static void bypassAndRemoveBackEdge(PathGrammarProduction backEdge, PathGrammar pg, BddContext ctx) {
        //回边backEdge：v -> psiBack? u
        String v = backEdge.leftVariable;
        String u = getTargetVariableOfInstantEdge(backEdge);
        LDL psiBackAst = getConditionOfInstantEdge(backEdge);
        BDD psiBackBdd = ctx.toBdd(psiBackAst);

        // 使用 BDD 存储累计到达的条件
        Map<String, BDD> C = new HashMap<>(); // LXY: // 记录到达各个节点的累计条件ReachCond
        Set<String> visited = new HashSet<>();

        // DFS结合 BDD 寻找简单路径，计算可达闭包
        BDD startCond = ctx.factory.one(); // 初始条件为 TRUE
        computeClosureDFS(u, startCond, visited, C, pg, backEdge, ctx); // 从目标节点 u 开始，只沿着瞬时边（不需要消耗时间的边）进行深度优先搜索。在搜索过程中，它会把沿途所有的条件通过逻辑 AND 累加起来，并将到达每个节点 w 的最终组合条件存储在字典 C 中（即代码里的 C_uw）。
        startCond.free();

        List<PathGrammarProduction> newBypassProductions = new ArrayList<>();

        // 遍历语法图中的所有节点 w，查看从 u 到 w 是否存在瞬时路径。
        for (String w : pg.getVariables()) {
            BDD C_uw = C.get(w);
            if (C_uw == null || C_uw.isZero()) continue;

            // BDD 级的 And 操作: 如果存在从 u 到 w 的瞬时路径，它会将回边本身的条件 (psiBackBdd) 与到达 w 的路径条件 (C_uw) 进行逻辑 AND 组合，得到 bypassCondBdd。
            BDD bypassCondBdd = psiBackBdd.and(C_uw);

            // LXY: 如果这个组合后的条件在逻辑上是绝对不可能发生的（bypassCondBdd.isZero() 返回 true），那就说明这条路径是一条死路，直接跳过（continue），不为它生成任何多余的代码。
            if (bypassCondBdd.isZero()) continue;

            // 把经过极简化简的 BDD 条件重新翻译回系统能认识的 AST 树结构 (LDL)。
            LDL bypassCondAst = ctx.toLdl(bypassCondBdd);

            for (PathGrammarProduction p : pg.productions) {
                if (p.leftVariable.equals(w) && isLoadOrTermination(p)) {
                    // LXY: 寻找出口产生式p：在节点 w 处，寻找所有非瞬时的产生式p（即 isLoadOrTermination(p) 返回 true 的边，比如真正消耗时间的操作或终止符）。这些边就是“环岛的真实出口”。
                    // p: w->ax || w->empty || w->f? || w->a
                    List<PathGrammarProduction> bypassProds = buildBypassProductions(v, bypassCondAst, p, ctx); // 修高架桥：调用 buildBypassProductions，创建一个新的产生式：从起始点 v 直接跳转到这个产生式 p 的目标，并以刚才计算出的 bypassCondAst 作为触发条件。将这些新产生的“捷径”收集起来。
                    newBypassProductions.addAll(bypassProds);
                }
            }
            bypassCondBdd.free();
        }

        // 将新生成的旁路规则正式加入文法，并无情地删掉那条导致死循环的回边 (backEdge)。循环就此被打破。
        pg.productions.addAll(newBypassProductions);
        pg.productions.remove(backEdge);

        // 因为删掉了一条边，可能会导致某些中间节点再也无法被访问到，所以调用 removeUnreachableProductions 进行死代码清理。
        removeUnreachableProductions(pg);

        // 安全释放 BDD 内存
        psiBackBdd.free();
        for (BDD bdd : C.values()) {
            bdd.free();
        }
    }

    /**
     * 通过深度优先搜索走遍所有的瞬时路网，利用 BDD 的 AND 操作累加沿途条件，利用 BDD 的 OR 操作合并不同路线的条件。当它运行结束时，字典 C 里就装满了一份完美的“寻路指南”：只要查阅 C.get(目标)，就能立刻知道从起点瞬间移动到目标点，到底需要满足什么最精简的前提条件。
     * @param curr 当前所处位置（起点），表示 DFS 探险队目前到达了语法图中的哪一个节点（Variable）。
     * @param currentCond 累积的通行条件，表示从“起点”走到当前的 curr 节点，一路上累积必须要满足的逻辑条件。
     * @param visited 当前探索路线的脚印)，记录着探险队当前所在的这条路径上，已经走过了哪些节点。
     * @param C 全局的寻路指南/ReachCond，这是一个字典，记录了从最开始的起点到达每一个可能到达的节点，汇总后所需的通行条件。作用：如果探险队通过路线 A 到达了节点 X，又通过路线 B 到达了节点 X，那么节点 X 对应的条件就会变成 (路线A的条件) OR (路线B的条件)。这是探险队最终要交付的成果报告。
     * @param pg 完整的路网地图，整个语法系统的数据结构，包含了所有的节点和边（产生式）。
     * @param backEdge 被封锁的危险道路，就是导致最初死循环的那条“回边”。
     * @param ctx 封装了底层 BDD（二元决策图）引擎的上下文对象。
     */
    private static void computeClosureDFS(
            String curr,
            BDD currentCond,
            Set<String> visited,
            Map<String, BDD> C,
            PathGrammar pg,
            PathGrammarProduction backEdge,
            BddContext ctx
    ) {
        if (currentCond.isZero()) return;

        // BDD 等价状态合并
        BDD existing = C.get(curr);
        if (existing == null) {
            C.put(curr, currentCond.id());
        } else {
            BDD updated = existing.or(currentCond); // BDD 的优势：如果在搜索中通过不同的路径到达了同一个节点，底层的 BDD 会自动对这些条件进行 OR 操作并进行极简合并，避免了条件表达式的无限膨胀。
            C.put(curr, updated);
            existing.free();
        }

        visited.add(curr);

        for (PathGrammarProduction p : pg.productions) {
            if (p.leftVariable.equals(curr) && isInstantaneousEdge(p) && p != backEdge) {
                checkDeprecatedType(p);
                String toNode = getTargetVariableOfInstantEdge(p);

                // 仅当邻居不在当前 DFS 堆栈中时才继续深入，完美避开死循环
                if (!visited.contains(toNode)) {
                    BDD edgeCond = ctx.toBdd(getConditionOfInstantEdge(p));
                    BDD nextCond = currentCond.and(edgeCond);
                    edgeCond.free();

                    computeClosureDFS(toNode, nextCond, visited, C, pg, backEdge, ctx);
                    nextCond.free();
                }
            }
        }

        visited.remove(curr); // 回溯
    }

    // ====================================================================================
    // --- 【全新核心引擎】 AST 与 BDD 之间安全的双向翻译器 ---
    // ====================================================================================

    public static class BddContext {
        BDDFactory factory;
        Map<String, Integer> var2Id = new HashMap<>();
        Map<Integer, String> id2Var = new HashMap<>();

        public BddContext() {
            factory = JFactory.init(100000, 100000);
            if (factory.varNum() < 2000) {
                factory.setVarNum(2000);
            }
        }

        public BDD toBdd(LDL f) {
            if (isLDLTrueNode(f)) return factory.one();
            if (isLDLFalseNode(f)) return factory.zero();

            if (f.operator == LDL.Operators.ATOM) {
                String rawName = f.data;
                String cleanName = extractVarName(rawName);
                if (!var2Id.containsKey(cleanName)) {
                    int id = var2Id.size();
                    var2Id.put(cleanName, id);
                    id2Var.put(id, rawName);
                }
                return factory.ithVar(var2Id.get(cleanName));
            }

            if (f.operator == LDL.Operators.AND) {
                BDD left = toBdd(f.children.get(0));
                BDD right = toBdd(f.children.get(1));
                BDD res = left.and(right);
                left.free(); right.free();
                return res;
            }

            if (f.operator == LDL.Operators.OR) {
                BDD left = toBdd(f.children.get(0));
                BDD right = toBdd(f.children.get(1));
                BDD res = left.or(right);
                left.free(); right.free();
                return res;
            }

            if (f.operator == LDL.Operators.NOT) {
                BDD child = toBdd(f.children.get(0));
                BDD res = child.not();
                child.free();
                return res;
            }

            throw new IllegalArgumentException("Unsupported LDL operator for BDD translation: " + f.operator);
        }

        public LDL toLdl(BDD bdd) {
            if (bdd.isOne()) return LDLTrue();
            if (bdd.isZero()) return LDLFalse();

            int varId = bdd.var();
            String rawName = id2Var.get(varId);
            LDL vNode = new LDL(rawName);

            BDD high = bdd.high();
            BDD low = bdd.low();

            LDL highNode = toLdl(high);
            LDL lowNode = toLdl(low);

            high.free();
            low.free();

            if (isLDLTrueNode(highNode) && isLDLFalseNode(lowNode)) return vNode;
            if (isLDLFalseNode(highNode) && isLDLTrueNode(lowNode)) return buildRawNot(vNode);

            LDL left = buildRawAnd(vNode, highNode);
            LDL right = buildRawAnd(buildRawNot(vNode), lowNode);
            return buildRawOr(left, right);
        }

        private String extractVarName(String raw) {
            if (raw != null && raw.startsWith("'") && raw.endsWith("'") && raw.length() >= 2) {
                return raw.substring(1, raw.length() - 1);
            }
            return raw;
        }

        private boolean isLDLTrueNode(LDL f) { return f.operator == LDL.Operators.ATOM && "TRUE".equals(f.data); }
        private boolean isLDLFalseNode(LDL f) { return f.operator == LDL.Operators.ATOM && "FALSE".equals(f.data); }

        private LDL buildRawAnd(LDL a, LDL b) {
            if (isLDLFalseNode(a) || isLDLFalseNode(b)) return LDLFalse();
            if (isLDLTrueNode(a)) return b;
            if (isLDLTrueNode(b)) return a;
            return new LDL(LDL.Operators.AND, a, b);
        }

        private LDL buildRawOr(LDL a, LDL b) {
            if (isLDLTrueNode(a) || isLDLTrueNode(b)) return LDLTrue();
            if (isLDLFalseNode(a)) return b;
            if (isLDLFalseNode(b)) return a;
            return new LDL(LDL.Operators.OR, a, b);
        }

        private LDL buildRawNot(LDL a) {
            if (isLDLTrueNode(a)) return LDLFalse();
            if (isLDLFalseNode(a)) return LDLTrue();
            return new LDL(LDL.Operators.NOT, a);
        }
    }

    // ====================================================================================
    // --- 满足要求的核心 BDD 代理操作接口 (所有逻辑运算必须经过底层 BDD) ---
    // ====================================================================================

    private static LDL LDLTrue() { return new LDL("TRUE"); }
    private static LDL LDLFalse() { return new LDL("FALSE"); }

    public static boolean isLDLTrue(LDL f, BddContext ctx) {
        BDD b = ctx.toBdd(f);
        boolean res = b.isOne();
        b.free();
        return res;
    }

    public static boolean isLDLFalse(LDL f, BddContext ctx) {
        BDD b = ctx.toBdd(f);
        boolean res = b.isZero();
        b.free();
        return res;
    }

    public static LDL LDLAnd(LDL a, LDL b, BddContext ctx) {
        BDD bddA = ctx.toBdd(a);
        BDD bddB = ctx.toBdd(b);
        BDD res = bddA.and(bddB);
        LDL out = ctx.toLdl(res);
        bddA.free(); bddB.free(); res.free();
        return out;
    }

    public static LDL LDLOr(LDL a, LDL b, BddContext ctx) {
        BDD bddA = ctx.toBdd(a);
        BDD bddB = ctx.toBdd(b);
        BDD res = bddA.or(bddB);
        LDL out = ctx.toLdl(res);
        bddA.free(); bddB.free(); res.free();
        return out;
    }

    // ====================================================================================
    // --- 可达性分析与死代码清理 ---
    // ====================================================================================

    /**
     * @return true 如果删除了任何产生式；否则返回 false
     */
    private static boolean removeUnreachableProductions(PathGrammar pg) {
        if (pg.start == null) return false;

        Set<String> reachable = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        reachable.add(pg.start);
        queue.add(pg.start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            for (PathGrammarProduction p : pg.productions) {
                if (p.leftVariable.equals(curr)) {
                    String rightVar = p.getRightVariable();
                    if (rightVar != null && !reachable.contains(rightVar)) {
                        reachable.add(rightVar);
                        queue.add(rightVar);
                    }
                }
            }
        }

        // removeIf 返回 true 说明成功移除了元素
        return pg.productions.removeIf(p -> !reachable.contains(p.leftVariable));
    }

    // ====================================================================================
    // --- 构建旁路的新产生式 (引入 BDD Context 严格把关逻辑) ---
    // 根据给定的起点、通行条件和具体的出口类型，精准地生成一条或多条新的语法产生式（也就是“捷径”）。
    // ====================================================================================

    /**
     * 构建旁路的新产生式 (引入 BDD Context 严格把关逻辑)
     * 根据给定的起点、通行条件和具体的出口类型，精准地生成一条或多条新的语法产生式（也就是“捷径”）。
     * @param v (String)：旁路的起点，也就是那条被删掉的死循环回边的出发点。
     * @param bypassCond (LDL)：节点v到节点w的瞬时通行条件。只有满足这个组合条件，系统才能走这条捷径。
     * @param p (PathGrammarProduction)：环岛的真实出口（耗时产生式或终端产生式）。
     * @param ctx (BddContext)：底层的 BDD 引擎上下文，用来做严谨的逻辑数学运算。
     * @return
     */
    private static List<PathGrammarProduction> buildBypassProductions(String v, LDL bypassCond, PathGrammarProduction p, BddContext ctx) {
        List<PathGrammarProduction> res = new ArrayList<>();
        if (isLDLFalse(bypassCond, ctx)) return res; // 如果 BDD 判断这个条件在逻辑上是绝对不可能发生的（恒假），那就说明这条捷径没人能走，直接返回空列表，拒绝施工。

        LDL bypassTest = new LDL(false, LDL.Operators.TEST, bypassCond); // bypassTest是节点v到节点w的瞬时通行条件

        switch (p.type) {
            case Empty: // p: w->empty
                res.add(new PathGrammarProduction(v, bypassTest)); // 新建捷径：v -> bypassCond?
                break;
            case Test: // p: w->f2?
                LDL f2 = ((LDL) p.rightItem1).children.get(0);
                LDL bypassCond_f2 = LDLAnd(bypassCond, f2, ctx);
                if(!isLDLFalse(bypassCond_f2, ctx)) {
                    // 每次 AST 拼接，都会深入 C 底层 BDD 执行极简化简
                    LDL combinedTest = new LDL(false, LDL.Operators.TEST, bypassCond_f2);
                    res.add(new PathGrammarProduction(v, combinedTest)); // 新建捷径：v -> (bypassCond AND f2)?
                }
                break;
            case PropFormula: // p: w -> a
                LDL a = (LDL) p.rightItem1;
                if(!isLDLFalse(a, ctx)) {
                    if (bypassCond.isPropFormula()) { // bypassCond是纯命题公式（断言）
                        LDL bypassCond_a = LDLAnd(bypassCond, a, ctx);
                        if (!isLDLFalse(bypassCond_a, ctx))
                            res.add(new PathGrammarProduction(v, bypassCond_a)); // 新建捷径： v -> (bypassCond AND a)
                    } else { // bypassCond不是断言
                        String tmp = "bypass_tmp_" + (++tmpCounter);
                        res.add(new PathGrammarProduction(v, bypassTest, tmp)); // 新建捷径： v -> bypassTest tmp
                        res.add(new PathGrammarProduction(tmp, a));  // 新建捷径： tmp -> a
                    }
                }
                break;
            case PropFormula_Variable: // p: w -> a2 x2
                LDL a2 = (LDL) p.rightItem1;
                String x2 = (String) p.rightItem2;
                if(!isLDLFalse(a2, ctx)) {
                    if (bypassCond.isPropFormula()) { // bypassCond是纯命题公式（断言）
                        LDL bypassCond_a2 = LDLAnd(bypassCond, a2, ctx);
                        if(!isLDLFalse(bypassCond_a2, ctx))
                            res.add(new PathGrammarProduction(v, bypassCond_a2, x2)); // 新建捷径：v -> (bypassCond AND a2) x2
                    } else {
                        String tmp = "bypass_tmp_" + (++tmpCounter);
                        res.add(new PathGrammarProduction(v, bypassTest, tmp)); // 新建捷径： v -> bypassTest tmp
                        res.add(new PathGrammarProduction(tmp, a2, x2)); // 新建捷径： tmp -> a2 x2
                    }
                }
                break;
            case Test_PropFormula:
            case PropFormula_Test:
                throw new IllegalArgumentException("Error: Deprecated type encountered.");
            default:
                break;
        }
        return res;
    }

    // ====================================================================================
    // --- 适配原系统语法的工具方法 ---
    // ====================================================================================

    private static void checkDeprecatedType(PathGrammarProduction p) {
        if (p.type == PathGrammarProduction.Type.Test_PropFormula ||
                p.type == PathGrammarProduction.Type.PropFormula_Test) {
            throw new IllegalArgumentException("Error: Deprecated type encountered: " + p.type);
        }
    }

    private static boolean isInstantaneousEdge(PathGrammarProduction p) {
        return p.type == PathGrammarProduction.Type.Variable || p.type == PathGrammarProduction.Type.Test_Variable;
    }

    private static String getTargetVariableOfInstantEdge(PathGrammarProduction p) {
        if (p.type == PathGrammarProduction.Type.Variable) return (String) p.rightItem1;
        if (p.type == PathGrammarProduction.Type.Test_Variable) return (String) p.rightItem2;
        return null;
    }

    private static LDL getConditionOfInstantEdge(PathGrammarProduction p) {
        if (p.type == PathGrammarProduction.Type.Variable) return LDLTrue();
        if (p.type == PathGrammarProduction.Type.Test_Variable) return ((LDL) p.rightItem1).children.get(0);
        return LDLTrue();
    }

    /**
     * 判断产生式p是否是耗时(consuming)产生式v->ax、或终端(terminal)产生式v->empty, v->f?, v->a
     * @param p:
     * @return
     */
    private static boolean isLoadOrTermination(PathGrammarProduction p) {
        checkDeprecatedType(p);
        return p.type == PathGrammarProduction.Type.Empty ||
                p.type == PathGrammarProduction.Type.Test ||
                p.type == PathGrammarProduction.Type.PropFormula ||
                p.type == PathGrammarProduction.Type.PropFormula_Variable;
    }

    private static boolean referencesVariable(PathGrammarProduction p, String varName) {
        String rightVar = p.getRightVariable();
        return rightVar != null && rightVar.equals(varName);
    }

    /**
     * p:v->empty, return newLeft->empty
     * p:v->f1?, return newLeft->f1?
     * p:v->f1, return newLeft->f1
     * p:v->w: if w==oldVar then return newLeft-> newVar,
     *         else return newLeft->w
     * p:v->f1?w: if w==oldVar then return newLeft-> f1?newVar,
     *         else return newLeft->f1?w
     * p:v->f1.w: if w==oldVar then return newLeft-> f1.newVar,
     *         else return newLeft->f1.w
     *
     * @param p
     * @param oldVar
     * @param newVar
     * @param newLeft
     * @return
     */
    private static PathGrammarProduction createReplacedProduction(PathGrammarProduction p, String oldVar, String newVar, String newLeft) {
        switch (p.type) {
            case Empty:
                return new PathGrammarProduction(newLeft);
            case Test:
            case PropFormula:
                return new PathGrammarProduction(newLeft, (LDL) p.rightItem1);
            case Variable:
                String r1 = p.rightItem1.equals(oldVar) ? newVar : (String) p.rightItem1;
                return new PathGrammarProduction(newLeft, r1);
            case Test_Variable:
                String r2 = p.rightItem2.equals(oldVar) ? newVar : (String) p.rightItem2;
                return new PathGrammarProduction(newLeft, (LDL) p.rightItem1, r2);
            case PropFormula_Variable:
                String r3 = p.rightItem2.equals(oldVar) ? newVar : (String) p.rightItem2;
                return new PathGrammarProduction(newLeft, (LDL) p.rightItem1, r3);
            default:
                return null;
        }
    }

    // ====================================================================================
    // --- 图算法：SCC与回边探测 ---
    // ====================================================================================

    private static List<List<String>> findSCCs(List<String> nodes, List<PathGrammarProduction> edges) {
        Map<String, Integer> ids = new HashMap<>();
        Map<String, Integer> low = new HashMap<>();
        Set<String> onStack = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        List<List<String>> sccs = new ArrayList<>();
        int[] idCounter = {0};

        for (String node : nodes) {
            if (!ids.containsKey(node)) {
                tarjanDFS(node, edges, ids, low, onStack, stack, sccs, idCounter);
            }
        }
        return sccs;
    }

    private static void tarjanDFS(String at, List<PathGrammarProduction> edges, Map<String, Integer> ids,
                                  Map<String, Integer> low, Set<String> onStack, Deque<String> stack,
                                  List<List<String>> sccs, int[] idCounter) {
        stack.push(at);
        onStack.add(at);
        ids.put(at, idCounter[0]);
        low.put(at, idCounter[0]);
        idCounter[0]++;

        for (PathGrammarProduction e : edges) {
            if (e.leftVariable.equals(at)) {
                String to = (String) e.rightItem1;
                if (!ids.containsKey(to)) {
                    tarjanDFS(to, edges, ids, low, onStack, stack, sccs, idCounter);
                    low.put(at, Math.min(low.get(at), low.get(to)));
                } else if (onStack.contains(to)) {
                    low.put(at, Math.min(low.get(at), ids.get(to)));
                }
            }
        }

        if (ids.get(at).equals(low.get(at))) {
            List<String> scc = new ArrayList<>();
            while (true) {
                String node = stack.pop();
                onStack.remove(node);
                scc.add(node);
                if (node.equals(at)) break;
            }
            sccs.add(scc);
        }
    }

    /**
     * 找到文法pg中的一条零延迟环回边
     * @param pg
     * @return
     */
    private static PathGrammarProduction findOneBackEdge(PathGrammar pg) {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String node : pg.getVariables()) {
            if (!visited.contains(node)) {
                PathGrammarProduction backEdge = dfsForBackEdge(node, pg.productions, visited, recStack);
                if (backEdge != null) return backEdge;
            }
        }
        return null;
    }

    /**
     *
     * @param at
     * @param prods
     * @param visited
     * @param recStack
     * @return
     */
    private static PathGrammarProduction dfsForBackEdge(
            String at,
            Set<PathGrammarProduction> prods,
            Set<String> visited,
            Set<String> recStack
    ) {
        visited.add(at); // LXY: 记录在整个 DFS 遍历过程中，所有已经被访问过的节点。目的：避免重复计算和无限递归。如果遍历到一个已经在 visited 中的节点，说明该节点及其后续路径已经被完全探索过或正在探索中，DFS 不需要再从该节点重新开始搜索。
        recStack.add(at); // LXY: 记录当前深度优先搜索路径（即当前活动调用栈）上的所有节点。目的：标识当前节点的直接祖先（从起点到当前节点的路径）。

        for (PathGrammarProduction p : prods) {
            if (p.leftVariable.equals(at) && isInstantaneousEdge(p)) { // LXY: p: at -> to || at -> f? to
                String to = getTargetVariableOfInstantEdge(p);
                if (!visited.contains(to)) {
                    PathGrammarProduction res = dfsForBackEdge(to, prods, visited, recStack);
                    if (res != null) return res;
                } else if (recStack.contains(to)) {
                    return p;
                }
            }
        }
        recStack.remove(at);
        return null;
    }
}