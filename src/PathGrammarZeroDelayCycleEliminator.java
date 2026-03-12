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
                String rep = scc.contains(pg.start) ? pg.start : scc.get(0);

                for (String node : scc) {
                    if (!node.equals(rep)) {
                        for (PathGrammarProduction p : pg.productions) {
                            boolean isModified = false;
                            String newLeft = p.leftVariable;

                            if (newLeft.equals(node)) {
                                newLeft = rep;
                                isModified = true;
                            }

                            if (isModified || referencesVariable(p, node)) {
                                toRemove.add(p);
                                PathGrammarProduction newP = createReplacedProduction(p, node, rep, newLeft);
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

    private static void bypassAndRemoveBackEdge(PathGrammarProduction backEdge, PathGrammar pg, BddContext ctx) {
        String v = backEdge.leftVariable;
        String u = getTargetVariableOfInstantEdge(backEdge);
        LDL psiBackAst = getConditionOfInstantEdge(backEdge);
        BDD psiBackBdd = ctx.toBdd(psiBackAst);

        // 使用 BDD 存储累计到达的条件
        Map<String, BDD> C = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // DFS结合 BDD 寻找简单路径，计算可达闭包
        BDD startCond = ctx.factory.one();
        computeClosureDFS(u, startCond, visited, C, pg, backEdge, ctx);
        startCond.free();

        List<PathGrammarProduction> newBypassProductions = new ArrayList<>();

        for (String w : pg.getVariables()) {
            BDD C_uw = C.get(w);
            if (C_uw == null || C_uw.isZero()) continue;

            // BDD 级的 And 操作
            BDD bypassCondBdd = psiBackBdd.and(C_uw);
            // 将绝对化简后的 BDD 还原为 LDL AST
            LDL bypassCondAst = ctx.toLdl(bypassCondBdd);

            for (PathGrammarProduction p : pg.productions) {
                if (p.leftVariable.equals(w) && isLoadOrTermination(p)) {
                    List<PathGrammarProduction> bypassProds = buildBypassProductions(v, bypassCondAst, p, ctx);
                    newBypassProductions.addAll(bypassProds);
                }
            }
            bypassCondBdd.free();
        }

        pg.productions.addAll(newBypassProductions);
        pg.productions.remove(backEdge);

        removeUnreachableProductions(pg);

        // 安全释放 BDD 内存
        psiBackBdd.free();
        for (BDD bdd : C.values()) {
            bdd.free();
        }
    }

    private static void computeClosureDFS(String curr, BDD currentCond, Set<String> visited,
                                          Map<String, BDD> C, PathGrammar pg, PathGrammarProduction backEdge, BddContext ctx) {
        if (currentCond.isZero()) return;

        // BDD 等价状态合并
        BDD existing = C.get(curr);
        if (existing == null) {
            C.put(curr, currentCond.id());
        } else {
            BDD updated = existing.or(currentCond);
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
    // ====================================================================================

    private static List<PathGrammarProduction> buildBypassProductions(String v, LDL bypassCond, PathGrammarProduction p, BddContext ctx) {
        List<PathGrammarProduction> res = new ArrayList<>();
        if (isLDLFalse(bypassCond, ctx)) return res; // 交由底层的 BDD 做权威验证

        LDL bypassTest = new LDL(false, LDL.Operators.TEST, bypassCond);

        switch (p.type) {
            case Empty:
                res.add(new PathGrammarProduction(v, bypassTest));
                break;
            case Test:
                LDL f2 = ((LDL) p.rightItem1).children.get(0);
                // 每次 AST 拼接，都会深入 C 底层 BDD 执行极简化简
                LDL combinedTest = new LDL(false, LDL.Operators.TEST, LDLAnd(bypassCond, f2, ctx));
                res.add(new PathGrammarProduction(v, combinedTest));
                break;
            case PropFormula:
                LDL a = (LDL) p.rightItem1;
                if (bypassCond.isPropFormula()) {
                    res.add(new PathGrammarProduction(v, LDLAnd(bypassCond, a, ctx)));
                } else {
                    String tmp = "bypass_tmp_" + (++tmpCounter);
                    res.add(new PathGrammarProduction(v, bypassTest, tmp));
                    res.add(new PathGrammarProduction(tmp, a));
                }
                break;
            case PropFormula_Variable:
                LDL a2 = (LDL) p.rightItem1;
                String x2 = (String) p.rightItem2;
                if (bypassCond.isPropFormula()) {
                    res.add(new PathGrammarProduction(v, LDLAnd(bypassCond, a2, ctx), x2));
                } else {
                    String tmp = "bypass_tmp_" + (++tmpCounter);
                    res.add(new PathGrammarProduction(v, bypassTest, tmp));
                    res.add(new PathGrammarProduction(tmp, a2, x2));
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

    private static PathGrammarProduction dfsForBackEdge(String at, Set<PathGrammarProduction> prods,
                                                        Set<String> visited, Set<String> recStack) {
        visited.add(at);
        recStack.add(at);

        for (PathGrammarProduction p : prods) {
            if (p.leftVariable.equals(at) && isInstantaneousEdge(p)) {
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