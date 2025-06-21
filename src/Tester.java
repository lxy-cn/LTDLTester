import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Tester {
    LDL fmla; // any LDL formula
    LDL out; // the output assertion of T(fmla)
    //对于fmla的每一个不同的主时态子公式f，构造T(f)，并存储(f, T(f).out)
    StackMap<LDL, SubTesterContainer> principalTemporalSubTesters;

    String NusmvTesterInstanceName = "T1";
    int SubTesterNumber = 0; // record the total number of LTL and LDL sub-testers
    int GrammarVariableNumber = 0; // used for recording the number of diamond or box testers

    //for LTL
    String LtlVariableNamePrefix = "W";
    int LtlVariableNumber = 0; // only used for the output variables of LTL sub-testers

    public class SubTesterTransition {
        LDL trans;
        PathGrammarProduction prod;
        boolean prime=false; // prime=true denotes that this LDL formula is over prime (next) version of variables

        public SubTesterTransition(PathGrammarProduction prod,
                                   boolean prime,
                                   LDL trans){
            this.prod = prod;
            this.prime = prime;
            this.trans = trans;
        }
    }

    // only for storing data of the tester for a Principally Temporal Sub-Formula fmla
    public class SubTesterContainer {
        Tester parentTester=null;
        int ID = 0;
        LDL fmla; // this tester will be built for this LDL formula fmla
        LDL out; // the output assertion of
        PathGrammar fmla_pathGrammar = null;

        //Set<String> vars; // the new variables created only for this tester, not for the sub testers
        LDL initCond;
        List<SubTesterTransition> trans; // the whole transition relation is the conjunction of all elements in the list
        List<LDL> justices;

        public SubTesterContainer(Tester parentTester) {
            this.parentTester = parentTester;
            this.ID = 0;
            this.fmla = null;
            //this.vars = new HashSet<>();
            this.out = null;
            this.fmla_pathGrammar = null;

            this.initCond = null;
            this.trans = new LinkedList<>();
            this.justices = new LinkedList<>();
        }

        public void print() {
            System.out.println("--The sub-tester " + this.ID + " for " + this.fmla.getText() + ":");
            System.out.println("Output assertion: " + this.out.getText());
            if(initCond!=null) System.out.println("Initial condition: " + initCond.getText());

            System.out.print(this.fmla_pathGrammar.toString());

            System.out.println("Transitions:");
            SubTesterTransition t=null;
            for(int i=0; i<this.trans.size(); i+=2) {
                t = this.trans.get(i);
                System.out.println("  " + t.trans.getText() + ";");
            }
            for(int i=1; i<this.trans.size(); i+=2) {
                t = this.trans.get(i);
                System.out.println("  " + t.trans.getText() + ";");
            }

            if(this.justices.size()>0) {
                System.out.println("Justices:");
                for (LDL j : this.justices) {
                    System.out.println("  " + j.getText());
                }
            }

        }

        // 将System.out的输出重定向到String
        // 用法：
        // (1)PrintStream oldOut = System.out;
        // (2)StringWriter strWriter = systemOut2StringWriter()
        // (3)System.out输出
        // (4)stringWriter2String(strWriter, oldOut);
        public StringWriter systemOut2StringWriter(){
            // 1. 创建 StringWriter 和 PrintWriter
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);

            // 3. 重定向 System.out 到 PrintWriter
            System.setOut(new PrintStream(System.out) {
                @Override
                public void println(String x) {
                    printWriter.println(x);
                }
            });
            return stringWriter;
        }

        //将输出恢复到System.out，并且返回此前System.out输出的字符串
        public String stringWriter2String(StringWriter strWriter, PrintStream originalOut) {
            // 5. 恢复原 System.out
            System.setOut(originalOut);

            // 6. 获取捕获的字符串
            return strWriter.toString();
        }

        public String toSMV() {
            String s="";
            s+="--------- No." + this.ID + " sub-tester for " + this.fmla.getText() + " ---------\r\n";
            //s+="--Output variable: " + this.out.getText() + "\r\n";

            // 输出变量定义
            if(this.fmla_pathGrammar!=null) {
                s+="VAR ";
                String leftSpace="";
                Iterator itVars = this.fmla_pathGrammar.getVariables()
                        .stream()
                        .map(Integer::parseInt)
                        .sorted()
                        .collect(Collectors.toList())
                        .iterator();
                boolean first=true;
                while (itVars.hasNext()) {
                    int v = (int) itVars.next();
                    if(first) {
                        leftSpace="";
                        first=false;
                    }else leftSpace="    ";
                    s += leftSpace + parentTester.NusmvTesterInstanceName + "X" + v + " : boolean;\t";
                    s += parentTester.NusmvTesterInstanceName + "Y" + v + " : boolean;\r\n";
                }
            }else{
                s+="VAR " + this.out.getText() + " : boolean;\r\n";
            }

            if(initCond!=null) s+="INIT " + initCond.getText() + ";\r\n";
//            s+="\r\n";

            if(this.fmla_pathGrammar!=null) {
                String strPG = this.fmla_pathGrammar.toString();
                // 在所有行首（包括第一行）添加注释前缀prefix
                String prefix = "--";
                s += strPG.replaceAll("(?m)^", prefix);
                //s+="\r\n";
            }

            SubTesterTransition t=null;
            for(int i=0; i<this.trans.size(); i+=2) {
                t = this.trans.get(i);
                s+="TRANS " + t.trans.getText() + ";\r\n";
            }
//            s+="\r\n";
            for(int i=1; i<this.trans.size(); i+=2) {
                t = this.trans.get(i);
                s+="TRANS " + t.trans.getText() + ";\r\n";
            }
//            s+="\r\n";
            if(this.justices.size()>0) {
                for (LDL j : this.justices) {
                    s+="JUSTICE " + j.getText() + ";\r\n";
                }
            }
            return s;
        }

    }

    // REQUIRE: reduce all BOX and redundant negation operators from fmla
    public Tester(String testerName, LDL fmla) throws CloneNotSupportedException {
        this.NusmvTesterInstanceName = testerName;
        GrammarVariableNumber = 0;
        LtlVariableNumber = 0;
        SubTesterNumber = 0;
        this.fmla = fmla;
        principalTemporalSubTesters = new StackMap<>();
        this.out = buildSubTesterRecur(fmla);
        //this.print();
    }

    // return the output assertion of the tester of f
    // all sub testers of f are cached
    public LDL buildSubTesterRecur(LDL f) throws CloneNotSupportedException {
        if(f==null) return null;
        LDL f1=null,f2=null;
        switch (f.operator) {
            case ATOM: // f = atom
                return f.clone();
            case NOT: // f = ! f1
                f1 = f.children.get(0);
                LDL f1out = buildSubTesterRecur(f1); // tester of f1
                return new LDL(LDL.Operators.NOT, f1out);
            case AND: // f = f1 & f2
            case OR: // f = f1 | f2
            case IMPLY: // f = f1 -> f2
            case BIIMPLY: // f = f1 <-> f2
                f1 = buildSubTesterRecur(f.children.get(0));
                f2 = buildSubTesterRecur(f.children.get(1));
                return new LDL(f.operator, f1, f2);
            case NEXT:
            case PREV:
            case GLOBALLY:
            case FINALLY:
            case UNTIL:
            case RELEASE:
            case DIAMOND:
//            case BOX: // BOX is reduced before calling this function
                // (1) If the sub-tester of the principally temporal sub-formula f does not exist, build and cache it.
                // (2) otherwise, obtain the cached sub-tester of f.
                // (3) return the output variable of the sub-tester of f.
                SubTesterContainer T=null;
                if(!principalTemporalSubTesters.containsKey(f)){
                    switch (f.operator) {
                        case NEXT: T = buildSubTester4Next(f); break;
                        case PREV: T = buildSubTester4Prev(f); break;
                        case GLOBALLY: T = buildSubTester4Globally(f); break;
                        case FINALLY: T = buildSubTester4Finally(f); break;
                        case UNTIL: T = buildSubTester4Until(f); break;
                        case RELEASE: T = buildSubTester4Release(f); break;
                        case DIAMOND: T = buildSubTester4Diamond(f); break;
                        default:
                            break;
                    }
                    if(T==null) return null;
                    principalTemporalSubTesters.push(f, T);
                }else{
                    T = principalTemporalSubTesters.get(f);
                }
                return T.out;
            default:
                break;
        }

        return null;
    }

    public LDL ldlVarX2Y(LDL f) throws CloneNotSupportedException {
        if (f==null) return null;
        LDL f2 = f.clone();
        ldlVarX2Yrecur(f2);
        return f2;
    }

    void ldlVarX2Yrecur(LDL f) {
        if (f==null) return;
        if(f.operator == LDL.Operators.ATOM){
            f.data=f.data.replaceAll(this.NusmvTesterInstanceName +"X(?=\\d)", this.NusmvTesterInstanceName +"Y");
        }else{
            if(f.children!=null){
                for(int i=0; i<f.children.size(); i++)
                    ldlVarX2Yrecur(f.children.get(i));
            }
        }
    }

    //Build tester for f = <pathExpr>f1
    public SubTesterContainer buildSubTester4Diamond(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.DIAMOND) return null;
        LDL pathExpr = f.children.get(0);
        LDL f1 = f.children.get(1); // the tester of f1 must already be created and cached, if the operator of f is DIAMOND
        LDL f2 = null;
        if(!pathExpr.isPathExpression(true) || !f1.isLDL()) return null;

        LDL f1out = buildSubTesterRecur(f1);
        LDL f2out = null;

        PathGrammar pg = null;
        try {
            pg = new PathGrammar(pathExpr);
            pg.renameProductions(GrammarVariableNumber+1);
            GrammarVariableNumber+=pg.getVariables().size();
//            System.out.print("After renaming variables, ");
//            pg.print();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = pg;

        Tester tt = null;
        LDL trX = null;
        LDL trY = null;
        String w;
        LDL prop;

        Set<String> varsVisited = new HashSet<>();
        Queue<String> Qvars = new ArrayDeque<>();
        Qvars.add(pg.start);

        List<SubTesterTransition> XvarTrans = new ArrayList<>(); // the transitions for one X variable
        List<SubTesterTransition> YvarTrans = new ArrayList<>(); // the transitions for one Y variable

        while(!Qvars.isEmpty()) {
            LDL test; // the LDL formula to be tested
            //访问队首元素
            String v = Qvars.remove();
            varsVisited.add(v);
            //获得v的所有产生式
            List<PathGrammarProduction> prods = pg.getProds(v);
            XvarTrans.clear();
            YvarTrans.clear();
            for (PathGrammarProduction p : prods) {
                //System.out.println("    "+(++j)+". "+p.getText());
                //访问产生式p
                switch (p.type) {
                    case Empty: // p:v-->empty
                        XvarTrans.add(new SubTesterTransition(p, false, f1out)); // T(f1).out
                        YvarTrans.add(new SubTesterTransition(p, false, ldlVarX2Y(f1out))); // T(f1).out
                        break;
                    case Test: // p:v-->f2?
                        test = ((LDL) p.rightItem1); // f2?
                        f2 = test.children.get(0);
                        f2out = buildSubTesterRecur(f2);
                        trX = new LDL(LDL.Operators.AND, f2out, f1out); // T(f2).out & T(f1).out
                        trY = new LDL(LDL.Operators.AND, ldlVarX2Y(f2out), ldlVarX2Y(f1out));
                        XvarTrans.add(new SubTesterTransition(p, false, trX));
                        YvarTrans.add(new SubTesterTransition(p, false, trY));
                        break;
                    case PropFormula: // p:v-->prop
                        prop = (LDL) p.rightItem1;
                        LDL next_f1out = new LDL(f1out);
                        next_f1out.prime=true;
                        trX = new LDL(LDL.Operators.AND, prop, next_f1out); // prop & next(T(f1).out)
                        trY = new LDL(LDL.Operators.AND, prop, ldlVarX2Y(next_f1out));
                        XvarTrans.add(new SubTesterTransition(p, false, trX));
                        YvarTrans.add(new SubTesterTransition(p, false, trY));
                        break;
                    case Variable: // p:v-->w
                        w = (String) p.rightItem1;
                        XvarTrans.add(new SubTesterTransition(p, false, new LDL(this.NusmvTesterInstanceName +"X"+w))); // "X"+w
                        YvarTrans.add(new SubTesterTransition(p, false, new LDL(this.NusmvTesterInstanceName +"Y"+w))); // "Y"+w
                        break;
                    case Test_Variable: // p:v-->f2?.w
                        test = ((LDL) p.rightItem1); // test=f2?
                        f2 = test.children.get(0);
                        f2out = buildSubTesterRecur(f2);
                        w = (String) p.rightItem2;
                        trX = new LDL(LDL.Operators.AND, f2out, new LDL(this.NusmvTesterInstanceName +"X"+w)); // T(f2).out & "X"+w
                        trY = new LDL(LDL.Operators.AND, ldlVarX2Y(f2out), new LDL(this.NusmvTesterInstanceName +"Y"+w)); // T(f2).out & "Y"+w
                        XvarTrans.add(new SubTesterTransition(p, false, trX));
                        YvarTrans.add(new SubTesterTransition(p, false, trY));
                        break;
                    case PropFormula_Variable: // p:v-->prop.w
                        prop = (LDL) p.rightItem1;
                        w = (String) p.rightItem2;
                        LDL nw = new LDL(this.NusmvTesterInstanceName +"X"+w);
                        nw.prime=true; // nw=next("X"+w)
                        LDL nwY = new LDL(this.NusmvTesterInstanceName +"Y"+w);
                        nwY.prime=true; // nwY=next("Y"+w)

                        trX = new LDL(LDL.Operators.AND, prop, nw); // prop & next("X"+w)
                        trY = new LDL(LDL.Operators.AND, prop, nwY); // prop & next("Y"+w)
                        XvarTrans.add(new SubTesterTransition(p, false, trX));
                        YvarTrans.add(new SubTesterTransition(p, false, trY));
                        break;
/*
                    case Test_PropFormula: // p:v-->f2?.prop
                        test = (LDL) p.rightItem1; // test=f2?
                        f2 = test.children.get(0);
                        f2out = buildTesterRecur(f2);
                        prop = (LDL) p.rightItem2;

                        trX = new LDL(LDL.Operators.AND, f2out, prop); // trX = T(f2).out & prop
                        trY = new LDL(LDL.Operators.AND, ldlVarX2Y(f2out), prop); // trY = T(f2).out & prop
                        XvarTrans.add(new TesterTransition(p, false, trX));
                        YvarTrans.add(new TesterTransition(p, false, trY));
                        break;
                    case PropFormula_Test: // p:v-->prop.f2?
                        prop = (LDL) p.rightItem1;
                        test = (LDL) p.rightItem2; // test=f2?
                        f2 = test.children.get(0);
                        f2out = buildTesterRecur(f2);
                        LDL next_f2out = new LDL(f2out);
                        next_f2out.prime=true;

                        trX = new LDL(LDL.Operators.AND, prop, next_f2out); // trX = prop & next(T(f2).out)
                        trY = new LDL(LDL.Operators.AND, prop, ldlVarX2Y(next_f2out)); // trX = prop & next(T(f2).out)
                        XvarTrans.add(new TesterTransition(p, false, trX));
                        YvarTrans.add(new TesterTransition(p, false, trY));
                        break;
*/
                    default:
                        break;
                }

                //v的后继变量入队
                String rightVar = p.getRightVariable();
                if(rightVar!=null && !varsVisited.contains(rightVar)) {
                    Qvars.add(rightVar);
                    varsVisited.add(rightVar);
                }
            }

            // 构造变量v的迁移关系
            // v <-> the disjunction of all elements in XvarTrans
            trX = XvarTrans.get(0).trans;
            trY = YvarTrans.get(0).trans;
            for(int i=1; i<XvarTrans.size(); i++) {
                trX = new LDL(LDL.Operators.OR, trX, XvarTrans.get(i).trans);
                trY = new LDL(LDL.Operators.OR, trY, YvarTrans.get(i).trans);
            }
            trX = new LDL(LDL.Operators.BIIMPLY, new LDL(this.NusmvTesterInstanceName +"X"+v), trX);
            T.trans.add(new SubTesterTransition(null, false, trX));

            trY = new LDL(LDL.Operators.IMPLY, new LDL(this.NusmvTesterInstanceName +"Y"+v), trY);
            T.trans.add(new SubTesterTransition(null, false, trY));

        }

        // 构造公平性约束
        Set<String> vars = pg.getVariables();
        Iterator<Integer> it = vars.stream().map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList())
                .iterator();
        boolean firstTime=true;
        LDL justice1=null;
        LDL justice2=null;
        while(it.hasNext()){
            int v = it.next();
            LDL conjunct1 = new LDL(this.NusmvTesterInstanceName +"X"+v+"="+this.NusmvTesterInstanceName +"Y"+v);
            LDL conjunct2 = new LDL(LDL.Operators.NOT, new LDL(this.NusmvTesterInstanceName +"Y"+v));
            if(firstTime){
                justice1 = conjunct1;
                justice2 = conjunct2;
                firstTime=false;
            }else {
                justice1 = new LDL(LDL.Operators.AND, justice1, conjunct1);
                justice2 = new LDL(LDL.Operators.AND, justice2, conjunct2);
            }
        }
        T.justices.add(justice1);
        T.justices.add(justice2);

        T.ID = (++SubTesterNumber);
        T.out = new LDL(NusmvTesterInstanceName + "X" + pg.start);

        return T;
    }

    //Build the sub-tester for f = X f1
    public SubTesterContainer buildSubTester4Next(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.NEXT) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);
        LDL next_f1out = new LDL(f1out);
        next_f1out.prime=true;

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                next_f1out); // tr = newVar <-> next(T(f1).out)
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = Y f1
    public SubTesterContainer buildSubTester4Prev(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.PREV) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be created and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(newVar);
        next_newVar.prime = true;

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                f1out,
                next_newVar); // tr = T(f1).out <-> next(newVar)
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.NOT, newVar); // init = !newVar

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = F f1
    public SubTesterContainer buildSubTester4Finally(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.FINALLY) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(newVar);
        next_newVar.prime = true;

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                new LDL(LDL.Operators.OR, f1out, next_newVar)); // tr = newVar <-> (T(f1).out | next(newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.justices.add(new LDL(LDL.Operators.IMPLY, newVar, f1out)); // newVar -> T(f1).out

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = G f1
    public SubTesterContainer buildSubTester4Globally(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.GLOBALLY) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(newVar);
        next_newVar.prime = true;

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                new LDL(LDL.Operators.AND, f1out, next_newVar)); // tr = newVar <-> (T(f1).out & next(newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = f1 U f2
    public SubTesterContainer buildSubTester4Until(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.UNTIL) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        LDL f2 = f.children.get(1); // the tester of f2 must already be built and cached
        LDL f2out = buildSubTesterRecur(f2);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(newVar);
        next_newVar.prime = true;

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                            newVar,
                            new LDL(LDL.Operators.OR,
                                    f2out,
                                    new LDL(LDL.Operators.AND,
                                            f1out,
                                            next_newVar
                                    )
                            )
                ); // tr = newVar <-> (T(f2).out | (T(f1).out & next(newVar)))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.justices.add(new LDL(LDL.Operators.IMPLY, newVar, f2out)); // newVar -> T(f2).out

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = f1 R f2
    public SubTesterContainer buildSubTester4Release(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.RELEASE) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        LDL f2 = f.children.get(1); // the tester of f2 must already be built and cached
        LDL f2out = buildSubTesterRecur(f2);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.fmla_pathGrammar = null;

        String newVarName = this.NusmvTesterInstanceName + LtlVariableNamePrefix + (++LtlVariableNumber);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(newVar);
        next_newVar.prime = true;

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                new LDL(LDL.Operators.AND,
                        f2out,
                        new LDL(LDL.Operators.OR,
                                f1out,
                                next_newVar
                        )
                )
        ); // tr = newVar <-> (T(f2).out & (T(f1).out | next(newVar)))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.ID = (++SubTesterNumber);
        T.out = newVar;

        return T;
    }

    public void print() {
        System.out.println("--The tester " + this.NusmvTesterInstanceName + " for " + this.fmla.getText() + ":");
        System.out.println("Output assertion: " + this.out.getText());
        if(this.principalTemporalSubTesters.size()<=0){
            System.out.println("There is not any sub-tester.");
        }else{
            System.out.println("The sub-testers of " + SubTesterNumber + " principally temporal sub-formulas:");
            Iterator<LDL> it = this.principalTemporalSubTesters.getKeyIterator(true);
            while (it.hasNext()){
                LDL f = it.next();
                SubTesterContainer T = this.principalTemporalSubTesters.get(f);
                T.print();
            }
        }

    }

    //生成smv刻画的tester，并将其插入main模块尾部
    public String toSMV() {
        String s="";
        s+="--The LTL formula to be verified: LTLSPEC "+this.out.getText()+";\r\n";
        s+="--The following SMV code is the tester for the LDL formula without [] operator: "+this.fmla.getText()+"\r\n";

        if(this.principalTemporalSubTesters.size()>0){
            s+="\n--The output variables for " + SubTesterNumber + " principally temporal sub-formula(s):\r\n";
            Iterator<LDL> it = this.principalTemporalSubTesters.getKeyIterator(true);
            String smvCode="";
            int i=1;
            while (it.hasNext()){
                LDL f = it.next();
                SubTesterContainer T = this.principalTemporalSubTesters.get(f);
                s += "--  (" + (i++) + ") " + T.out.getText() + ": " + f.getText() + "\r\n";
                smvCode += "\r\n"+T.toSMV();
            }
            s += smvCode;
        }

        return s;
    }

}
