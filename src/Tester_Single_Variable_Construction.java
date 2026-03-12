import java.util.*;
import java.util.stream.Collectors;

/* The class for testers constructed via SINGLE-variables representation */
public class Tester_Single_Variable_Construction {
    LDL fmla; // any LDL formula
    LDL out; // the output assertion of T(fmla)
    //对于fmla的每一个不同的主时态子公式f，构造T(f)，并存储(f, T(f).out)
    StackMap<LDL, SubTesterContainer> SubTester_Containers; // 主时态子式的测试器容器集合

    String Tester_Instance_Name = ""; // for example "T1"
    int SubTester_Containers_Number = 0; // record the total number of LTL and LDL sub-testers

    //for LTL
    String LtlVariable_NamePrefix = "W";
    int LtlVariables_Number = 0; // only used for the output variables of LTL sub-testers

    static String Diamond_GrammarVariable_NamePrefix = "X";
    int Diamond_GrammarVariables_Number = 0; // used for recording the number of diamond or box testers

    static String Diamond_SubFormula_AuxiVar_NamePrefix = "N";
    int Diamond_SubFormula_AuxiVar_Number = 0;

    static String Diamond_AuxiVar_U_NamePrefix = "U";
    int Diamond_AuxiVar_U_Number = 0;

    static String Diamond_smvDefinition_finalCondition_NamePrefix = "FinalCond";
    int Diamond_smvDefinition_finalCondition_Number = 0;

    static String Diamond_smvDefinition_Path_Starting_NamePrefix = "StartCond";
    int Diamond_smvDefinition_Path_Starting_Number = 0;

    static String Diamond_smvDefinition_Path_inProgress_NamePrefix = "InProgressCond";
    int Diamond_smvDefinition_Path_inProgress_Number = 0;

    // the transition of a sub-tester
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
        Tester_Single_Variable_Construction parentTester=null;
        int ID = 0;
        LDL fmla; // this tester will be built for this LDL formula fmla
        LDL out; // the output assertion of

        // Diamond算子相关的变量
        PathGrammar diamond_pathGrammar = null;
        LDL diamond_subformula_auxiVar = null;
        LDL diamond_auxiVar_U = null;

        LDL diamond_smvDefinition_path_starting = null;
        LDL diamond_path_starting_condition = null;  // C^S

        LDL diamond_smvDefinition_finalCond = null;

        LDL diamond_smvDefinition_path_inProgress = null;
        LDL diamond_path_inProgress_condition = null; // R^S

        String diamond_pathGrammar_before_optimization = "";
        String diamond_pathGrammar_after_optimization_before_renaming = "";


        //Set<String> vars; // the new variables created only for this tester, not for the sub testers
        LDL initCond;
        List<SubTesterTransition> trans; // the whole transition relation is the conjunction of all elements in the list
        List<LDL> justices;
        LDL finalCond;

        public SubTesterContainer(Tester_Single_Variable_Construction parentTester) {
            this.parentTester = parentTester;
            this.ID = 0;
            this.fmla = null;
            //this.vars = new HashSet<>();
            this.out = null;
            this.diamond_pathGrammar = null;

            this.initCond = null;
            this.trans = new LinkedList<>();
            this.justices = new LinkedList<>();
            this.finalCond = null;
        }

    }

    public String subTesterToSMV(SubTesterContainer T, boolean printPathGrammarBeforeOptimization, boolean printPathGrammarBeforeRenaming) {
        String s="";
        s+="--------- No." + T.ID + " sub-tester for " + T.fmla.getText(false) + " ---------\r\n";
        //s+="--Output variable: " + this.out.getText() + "\r\n";

        // 输出变量定义
        if(T.diamond_pathGrammar !=null) {
            s+="VAR ";
            int printedCounter = 0;
            String leftSpace="";
            int var_number_per_line = 5;
            Iterator itVars = T.diamond_pathGrammar.getVariables()
                    .stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList())
                    .iterator();
            boolean first=true;
            while (itVars.hasNext()) {
                int v = (int) itVars.next();
                String varX = Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + v;
                s += leftSpace + varX + " : boolean;";
                printedCounter++; if((printedCounter % var_number_per_line) != 0) s+="  "; else s+="\r\n    ";
            }

            s+=T.diamond_subformula_auxiVar.getText(false) + " : boolean;";
            printedCounter++; if((printedCounter % var_number_per_line) != 0) s+="  "; else s+="\r\n    ";

            s+=T.diamond_auxiVar_U.getText(false) + " : boolean;";
            printedCounter++; if((printedCounter % var_number_per_line) != 0) s+="  "; else s+="\r\n    ";

            if(printedCounter%var_number_per_line != 0) s+="\r\n";

            s+="\r\nDEFINE " + T.diamond_smvDefinition_path_starting.getText(false) + " := " + T.diamond_path_starting_condition.getText(false) + ";\r\n";
            s+="DEFINE " + T.diamond_smvDefinition_path_inProgress.getText(false) + " := " + T.diamond_path_inProgress_condition.getText(false) + ";\r\n";
            s+="DEFINE " + T.diamond_smvDefinition_finalCond.getText(false) + " := " + T.finalCond.getText(false) + ";\r\n\n";
        }else{
            s+="VAR " + T.out.getText(false) + " : boolean;\r\n\r\n";
        }

        if(T.initCond!=null) s+="INIT " + T.initCond.getText(false) + ";\r\n";
//            s+="\r\n";

        // print non-optimized grammar
        if(!T.diamond_pathGrammar_before_optimization.equals("") && printPathGrammarBeforeOptimization){
            s+="\r\n--Before optimizing and renaming:\r\n";
            s+=T.diamond_pathGrammar_before_optimization.replaceAll("(?m)^", "--");
        }

        // print optimized but non-renamed grammar
        if(!T.diamond_pathGrammar_after_optimization_before_renaming.equals("") && printPathGrammarBeforeRenaming){
            s+="\r\n--After optimized and before renaming:\r\n";
            s+=T.diamond_pathGrammar_after_optimization_before_renaming.replaceAll("(?m)^", "--");
        }

        // print optimized and renamed grammar
        if(T.diamond_pathGrammar !=null) {
            String strPG = T.diamond_pathGrammar.toString();
            s+="\r\n--After optimized and renamed:\r\n";
            // 在所有行首（包括第一行）添加注释前缀prefix
            String prefix = "--";
            s += strPG.replaceAll("(?m)^", prefix);
            s+="\r\n";
        }

        // print the SMV model
        SubTesterTransition t=null;
        for(int i=0; i<T.trans.size(); i++) {
            t = T.trans.get(i);
            s+="TRANS " + t.trans.getText(false) + ";\r\n";
        }

        s+="\r\n";
        if(T.justices.size()>0) {
            for (LDL j : T.justices) {
                s+="JUSTICE " + j.getText(false) + ";\r\n";
            }
        }
        return s;
    }

    // REQUIRE: reduce all BOX and redundant negation operators from fmla
    public Tester_Single_Variable_Construction(String testerName, LDL fmla) throws CloneNotSupportedException {
        this.Tester_Instance_Name = testerName;
        Diamond_GrammarVariables_Number = 0;
        LtlVariables_Number = 0;
        SubTester_Containers_Number = 0;
        this.fmla = fmla;
        SubTester_Containers = new StackMap<>();
        this.out = buildSubTesterRecur(fmla);
        //this.print();
    }

    // return the output assertion of the tester of f
    // all sub testers of f are cached
    LDL buildSubTesterRecur(LDL f) throws CloneNotSupportedException {
        if(f==null) return null;
        LDL f1out=null,f2out=null;
        switch (f.operator) {
            case ATOM: // f = atom
                return f.clone();
            case NOT: // f = ! f1
                f1out = buildSubTesterRecur(f.children.get(0)); // tester of f1
                return new LDL(LDL.Operators.NOT, f1out);
            case AND: // f = f1 & f2
            case OR: // f = f1 | f2
            case IMPLY: // f = f1 -> f2
            case BIIMPLY: // f = f1 <-> f2
                f1out = buildSubTesterRecur(f.children.get(0)); // T(f1)
                f2out = buildSubTesterRecur(f.children.get(1)); // T(f2)
                return new LDL(f.operator, f1out, f2out);
            case NEXT:
            case PREVIOUS:
            case UNTIL:
            case SINCE:
            case RELEASE:
            case TRIGGER:
            case FINALLY:
            case PAST:
            case GLOBALLY:
            case HISTORICALLY:
            case DIAMOND:
//            case BOX: // BOX is reduced before calling this function
                // (1) If the sub-tester of the principally temporal sub-formula f does not exist, build and cache it.
                // (2) otherwise, obtain the cached sub-tester of f.
                // (3) return the output variable of the sub-tester of f.
                SubTesterContainer T=null;
                if(!SubTester_Containers.containsKey(f)){
                    switch (f.operator) {
                        case NEXT: T = buildSubTester4Next(f); break;
                        case PREVIOUS: T = buildSubTester4Previous(f); break;
                        case UNTIL: T = buildSubTester4Until(f); break;
                        case SINCE: T = buildSubTester4Since(f); break;
                        case RELEASE: T = buildSubTester4Release(f); break;
                        case TRIGGER: T = buildSubTester4Trigger(f); break;
                        case FINALLY: T = buildSubTester4Finally(f); break;
                        case PAST: T = buildSubTester4Past(f); break;
                        case GLOBALLY: T = buildSubTester4Globally(f); break;
                        case HISTORICALLY: T = buildSubTester4Historically(f); break;

                        case DIAMOND: T = buildSubTester4Diamond(f); break;
                        default:
                            break;
                    }
                    if(T==null) return null;
                    SubTester_Containers.push(f, T);
                }else{
                    T = SubTester_Containers.get(f);
                }
                return T.out;
            default:
                break;
        }

        return null;
    }

    LDL ldlVarX2Y(LDL f) throws CloneNotSupportedException {
        if (f==null) return null;
        LDL f2 = f.clone();
        ldlVarX2Yrecur(f2);
        return f2;
    }

    void ldlVarX2Yrecur(LDL f) {
        if (f==null) return;
        if(f.operator == LDL.Operators.ATOM){
            f.data=f.data.replaceAll(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + "(?=\\d)", this.Tester_Instance_Name +"Y");
        }else{
            if(f.children!=null){
                for(int i=0; i<f.children.size(); i++)
                    ldlVarX2Yrecur(f.children.get(i));
            }
        }
    }

    // Build tester for f = <pathExpr> f1
    // Building method 2: restrict feasible paths to be FINITE by using SINGLE-variables representation
    public SubTesterContainer buildSubTester4Diamond(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.DIAMOND) return null;
        LDL pathExpr = f.children.get(0);
        LDL f1 = f.children.get(1); // the tester of f1 must already be created and cached, if the operator of f is DIAMOND
        LDL f2 = null;
        if(!pathExpr.isPathExpression(true) || !f1.isLDL_LTL()) return null;

        LDL f1out = buildSubTesterRecur(f1);
        LDL next_f1out = new LDL(f1out); next_f1out.prime=true;

        LDL f2out = null;

        // Tester T for the formula f will be returned
        Tester_Single_Variable_Construction.SubTesterContainer T = new Tester_Single_Variable_Construction.SubTesterContainer(this);
        T.fmla = f;

        Diamond_SubFormula_AuxiVar_Number++;
        String diamond_subformula_auxiVar_name = Tester_Instance_Name + Diamond_SubFormula_AuxiVar_NamePrefix + Diamond_SubFormula_AuxiVar_Number;
        LDL diamond_subformula_auxiVar = new LDL(diamond_subformula_auxiVar_name);
        T.diamond_subformula_auxiVar = diamond_subformula_auxiVar;

        Diamond_AuxiVar_U_Number++;
        String diamond_auxiVar_U_Name = Tester_Instance_Name + Diamond_AuxiVar_U_NamePrefix + Diamond_AuxiVar_U_Number;
        LDL diamond_auxiVar_U = new LDL(diamond_auxiVar_U_Name);
        T.diamond_auxiVar_U = diamond_auxiVar_U;

        Diamond_smvDefinition_finalCondition_Number++;
        String diamond_smvDefinition_finalCondition_name = Tester_Instance_Name + Diamond_smvDefinition_finalCondition_NamePrefix + Diamond_smvDefinition_finalCondition_Number;
        T.diamond_smvDefinition_finalCond = new LDL(diamond_smvDefinition_finalCondition_name);

        Diamond_smvDefinition_Path_Starting_Number++;
        String diamond_smvDefinition_path_starting_name = Tester_Instance_Name + Diamond_smvDefinition_Path_Starting_NamePrefix + Diamond_smvDefinition_Path_Starting_Number;
        T.diamond_smvDefinition_path_starting = new LDL(diamond_smvDefinition_path_starting_name);

        Diamond_smvDefinition_Path_inProgress_Number++;
        String diamond_smvDefinition_path_inProgress_name = Tester_Instance_Name + Diamond_smvDefinition_Path_inProgress_NamePrefix + Diamond_smvDefinition_Path_inProgress_Number;
        T.diamond_smvDefinition_path_inProgress = new LDL(diamond_smvDefinition_path_inProgress_name);

        PathGrammar pg = null;
        int GrammarVariableStartNumber = 0;
        try {
            pg = new PathGrammar(pathExpr); // pg is the path grammar before optimization
            T.diamond_pathGrammar_before_optimization = pg.toString();
            pg.new_optimization();
            T.diamond_pathGrammar_after_optimization_before_renaming = pg.toString();

            GrammarVariableStartNumber = Diamond_GrammarVariables_Number +1;
            pg.renameProductions(GrammarVariableStartNumber);
            Diamond_GrammarVariables_Number +=pg.getVariables().size();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        T.diamond_pathGrammar = pg; // the path grammar after optimization

        LDL disjunct = null; // 一个析取项
        String w;
        LDL prop;

        Set<String> varsVisited = new HashSet<>();
        Queue<String> Qvars = new ArrayDeque<>(); // 广度优先访问的变量队列
        Qvars.add(pg.start);

        // R_disjuncts is the list of the disjuncts in the transition relation Ri for one variable
        List<Tester_Single_Variable_Construction.SubTesterTransition> R_disjuncts = new ArrayList<>();
        // F_disjuncts is the list of the disjuncts in the final condition Fi for one variable
        List<Tester_Single_Variable_Construction.SubTesterTransition> F_disjuncts = new ArrayList<>();

        // 广度优先访问文法变量
        while(!Qvars.isEmpty()) {
            LDL test; // the LDL formula to be tested
            //访问队首元素
            String v = Qvars.remove();
            varsVisited.add(v);
            //prods:获得v的所有产生式
            List<PathGrammarProduction> prods = pg.getProds(v);
            R_disjuncts.clear(); // R_disjuncts is the list of the disjuncts in the transition relation Ri for the variable v
            F_disjuncts.clear(); // F_disjuncts is the list of the disjuncts in the final condition Fi for the variable v
            for (PathGrammarProduction p : prods) {
                //System.out.println("    "+(++j)+". "+p.getText());
                //访问产生式p
                switch (p.type) {
                    case Empty: // p:v-->empty
                        F_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, f1out)); // T(f1).out
                        break;
                    case Test: // p:v-->f2?
                        test = ((LDL) p.rightItem1); // f2?
                        f2 = test.children.get(0);
                        f2out = buildSubTesterRecur(f2);
                        disjunct = new LDL(LDL.Operators.AND, f2out, f1out); // T(f2).out & T(f1).out
                        F_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, disjunct));
                        break;
                    case PropFormula: // p:v-->prop
                        prop = (LDL) p.rightItem1;

                        disjunct = new LDL(LDL.Operators.AND, prop, diamond_subformula_auxiVar); // prop & diamond_subformula_auxiVar
                        F_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, disjunct));
                        break;
                    case Variable: // p:v-->w
                        w = (String) p.rightItem1;
                        R_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, new LDL(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + w))); // "X"+w
                        break;
                    case Test_Variable: // p:v-->f2?.w
                        test = ((LDL) p.rightItem1); // test=f2?
                        f2 = test.children.get(0);
                        f2out = buildSubTesterRecur(f2);
                        w = (String) p.rightItem2;
                        disjunct = new LDL(LDL.Operators.AND, f2out, new LDL(this.Tester_Instance_Name +Diamond_GrammarVariable_NamePrefix+w)); // T(f2).out & "X"+w
                        R_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, disjunct));
                        break;
                    case PropFormula_Variable: // p:v-->prop.w
                        prop = (LDL) p.rightItem1;
                        w = (String) p.rightItem2;
                        LDL nw = new LDL(true, this.Tester_Instance_Name +Diamond_GrammarVariable_NamePrefix+w); // nw=next("X"+w)

                        disjunct = new LDL(LDL.Operators.AND, prop, nw); // prop & next("X"+w)
                        R_disjuncts.add(new Tester_Single_Variable_Construction.SubTesterTransition(p, false, disjunct));
                        break;

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

            //-------------------------------------
            // 构造变量v（编号为i）的迁移关系Ri
            LDL Ri;
            if(R_disjuncts.size()>0) {
                // v <-> the disjunction of all elements in R_disjuncts
                disjunct = R_disjuncts.get(0).trans;
                for (int i = 1; i < R_disjuncts.size(); i++) {
                    disjunct = new LDL(LDL.Operators.OR, disjunct, R_disjuncts.get(i).trans);
                }
                Ri = new LDL(LDL.Operators.BIIMPLY, new LDL(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + v), disjunct);
            }else{
                // v <-> false, i.e., !v
                Ri = new LDL(LDL.Operators.NOT, new LDL(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + v));
            }

            // 构造变量v（编号为i）的终止条件Fi
            LDL Fi;
            if(F_disjuncts.size()>0) {
                // v <-> the disjunction of all elements in F_disjuncts
                disjunct = F_disjuncts.get(0).trans;
                for (int i = 1; i < F_disjuncts.size(); i++) {
                    disjunct = new LDL(LDL.Operators.OR, disjunct, F_disjuncts.get(i).trans);
                }
                Fi = new LDL(LDL.Operators.BIIMPLY, new LDL(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + v), disjunct);
            }else{
                // v <-> false, i.e., !v
                Fi = new LDL(LDL.Operators.NOT, new LDL(this.Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + v));
            }

            // 构造终止条件F = F1 & ... & Fn
            if(T.finalCond==null){
                T.finalCond = Fi;
            }else{
                T.finalCond = new LDL(LDL.Operators.AND, T.finalCond, Fi);
            }

            // 加入迁移关系(Ri | Fi)
            T.trans.add(new Tester_Single_Variable_Construction.SubTesterTransition(null, false, new LDL(LDL.Operators.OR, Ri, Fi)));
        }

        // 加入迁移关系 x_f1 <-> next(f1out)
        T.trans.add(new Tester_Single_Variable_Construction.SubTesterTransition(null, false,
                new LDL(LDL.Operators.BIIMPLY, diamond_subformula_auxiVar, next_f1out) ));

        // 现在，T.trans是R^G

        // 构造C^S = ((S & !F) -> u) & ((S & F) -> !u)
        String start_variable_name = Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + pg.start;
        LDL startVar = new LDL(start_variable_name);
        // Cs1 = (S & !F) -> u
        LDL Cs1 = new LDL(LDL.Operators.IMPLY,
                new LDL(LDL.Operators.AND, startVar, new LDL(LDL.Operators.NOT, T.diamond_smvDefinition_finalCond)),
                diamond_auxiVar_U);
        // Cs2 = (S & F) -> !u
        LDL Cs2 = new LDL(LDL.Operators.IMPLY,
                new LDL(LDL.Operators.AND, startVar, T.diamond_smvDefinition_finalCond),
                new LDL(LDL.Operators.NOT, diamond_auxiVar_U));
        // 构造C^S = Cs1 & Cs2
        LDL Cs = new LDL(LDL.Operators.AND, Cs1, Cs2);

        T.trans.add(new Tester_Single_Variable_Construction.SubTesterTransition(null, false, T.diamond_smvDefinition_path_starting));

        T.diamond_path_starting_condition = Cs;

        if(T.initCond==null){
            T.initCond = T.diamond_smvDefinition_path_starting;
        }else{
            T.initCond = new LDL(LDL.Operators.AND, T.initCond, T.diamond_smvDefinition_path_starting);
        }


        // 构造R^S = (u & !F & (v1 | ... | vn)) -> (u' & (v1' | ... | vn'))
        LDL allVarsDisjunction=null;
        LDL oneVar = null;
        for(int i=0; i<pg.getVariables().size(); i++){
            oneVar = new LDL(Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + (GrammarVariableStartNumber + i));
            if(allVarsDisjunction==null){
                allVarsDisjunction = oneVar;
            }else{
                allVarsDisjunction = new LDL(LDL.Operators.OR, allVarsDisjunction, oneVar);
            }
        }
        LDL next_allVarsDisjunction=null;
        next_allVarsDisjunction = new LDL(allVarsDisjunction);
        next_allVarsDisjunction.prime = true;
/*
        LDL next_oneVar = null;
        for(int i=0; i<pg.getVariables().size(); i++){
            next_oneVar = new LDL(Tester_Instance_Name + "X" + (GrammarVariableStartNumber + i));
            next_oneVar.prime=true;
            if(next_allVarsDisjunction==null){
                next_allVarsDisjunction = next_oneVar;
            }else{
                next_allVarsDisjunction = new LDL(LDL.Operators.OR, next_allVarsDisjunction, next_oneVar);
            }
        }
*/
        LDL next_auxiVar_u = new LDL(diamond_auxiVar_U); next_auxiVar_u.prime=true;
        LDL Rs = new LDL(LDL.Operators.IMPLY,
                new LDL(LDL.Operators.AND,
                        new LDL(LDL.Operators.AND, diamond_auxiVar_U, new LDL(LDL.Operators.NOT, T.diamond_smvDefinition_finalCond)),
                        allVarsDisjunction),
                new LDL(LDL.Operators.AND, next_auxiVar_u, next_allVarsDisjunction)
        );
        T.trans.add(new Tester_Single_Variable_Construction.SubTesterTransition(null, false, T.diamond_smvDefinition_path_inProgress));

        T.diamond_path_inProgress_condition = Rs;

        // 构造公平性约束J = (u & (v1 | ... | vn)) -> F
        LDL justice = new LDL(LDL.Operators.IMPLY,
                new LDL(LDL.Operators.AND, diamond_auxiVar_U, allVarsDisjunction),
                T.diamond_smvDefinition_finalCond);
        T.justices.add(justice);

        T.ID = (++SubTester_Containers_Number);
        T.out = new LDL(Tester_Instance_Name + Diamond_GrammarVariable_NamePrefix + pg.start);

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
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                next_f1out); // tr = newVar <-> next(T(f1).out)
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = Y f1 (Yesterday, Previous)
    public SubTesterContainer buildSubTester4Previous(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.PREVIOUS) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be created and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                f1out,
                next_newVar); // tr = next(newVar) <-> T(f1).out
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.NOT, newVar); // init = !newVar

        T.ID = (++SubTester_Containers_Number);
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
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                new LDL(LDL.Operators.OR, f1out, next_newVar)); // tr = newVar <-> (T(f1).out | next(newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.justices.add(new LDL(LDL.Operators.IMPLY, newVar, f1out)); // newVar -> T(f1).out

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = P f1
    public SubTesterContainer buildSubTester4Past(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.FINALLY) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);
        LDL next_f1out = new LDL(true, f1out);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                next_newVar,
                new LDL(LDL.Operators.OR, next_f1out, newVar)); // tr = next(newVar) <-> (next(T(f1).out) | newVar)
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.BIIMPLY, newVar, f1out);  // newVar <-> T(f1).out

        T.ID = (++SubTester_Containers_Number);
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
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                newVar,
                new LDL(LDL.Operators.AND, f1out, next_newVar)); // tr = newVar <-> (T(f1).out & next(newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = H f1
    public SubTesterContainer buildSubTester4Historically(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.GLOBALLY) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);
        LDL next_f1out = new LDL(true, f1out);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                next_newVar,
                new LDL(LDL.Operators.AND, next_f1out, newVar)); // tr = next(newVar) <-> (next(T(f1).out) & newVar)
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.BIIMPLY, newVar, f1out);  // newVar <-> T(f1).out

        T.ID = (++SubTester_Containers_Number);
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
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);

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

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = f1 S f2
    public SubTesterContainer buildSubTester4Since(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.SINCE) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        LDL f2 = f.children.get(1); // the tester of f2 must already be built and cached
        LDL f2out = buildSubTesterRecur(f2);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);
        LDL next_f1out = new LDL(true, f1out);
        LDL next_f2out = new LDL(true, f2out);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                next_newVar,
                new LDL(LDL.Operators.OR,
                        next_f2out,
                        new LDL(LDL.Operators.AND,
                                next_f1out,
                                newVar
                        )
                )
        ); // tr = next(newVar) <-> (next(T(f2).out) | (next(T(f1).out) & newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.BIIMPLY, newVar, f2out);  // newVar <-> T(f2).out

        T.ID = (++SubTester_Containers_Number);
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
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);

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

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }

    //Build the sub-tester for f = f1 T f2
    public SubTesterContainer buildSubTester4Trigger(LDL f) throws CloneNotSupportedException {
        if(f.operator != LDL.Operators.RELEASE) return null;

        LDL f1 = f.children.get(0); // the tester of f1 must already be built and cached
        LDL f1out = buildSubTesterRecur(f1);

        LDL f2 = f.children.get(1); // the tester of f2 must already be built and cached
        LDL f2out = buildSubTesterRecur(f2);

        SubTesterContainer T = new SubTesterContainer(this); // Tester T will be returned
        T.fmla = f;
        T.diamond_pathGrammar = null;

        String newVarName = this.Tester_Instance_Name + LtlVariable_NamePrefix + (++LtlVariables_Number);
        LDL newVar = new LDL(newVarName);
        LDL next_newVar = new LDL(true, newVar);
        LDL next_f1out = new LDL(true, f1out);
        LDL next_f2out = new LDL(true, f2out);

        LDL tr = new LDL(LDL.Operators.BIIMPLY,
                next_newVar,
                new LDL(LDL.Operators.AND,
                        next_f2out,
                        new LDL(LDL.Operators.OR,
                                next_f1out,
                                newVar
                        )
                )
        ); // tr = next(newVar) <-> (next(T(f2).out) & (next(T(f1).out) | newVar))
        T.trans.add(new SubTesterTransition(null, false, tr));

        T.initCond = new LDL(LDL.Operators.BIIMPLY, newVar, f2out);  // newVar <-> T(f2).out

        T.ID = (++SubTester_Containers_Number);
        T.out = newVar;

        return T;
    }


    //生成smv刻画的tester，并将其插入main模块尾部
    public String toSMV() {
        String s="";
        s+="--The LTL formula to be verified: LTLSPEC "+this.out.getText(true)+";\r\n";
        s+="--The following SMV code is the tester for the LTDL formula without [] operator: "+this.fmla.getText(true)+"\r\n";

        if(this.SubTester_Containers.size()>0){
            s+="\n--The output variables for " + SubTester_Containers_Number + " principally temporal sub-formula(s):\r\n";
            Iterator<LDL> it = this.SubTester_Containers.getKeyIterator(true);
            String smvCode="";
            int i=1;
            while (it.hasNext()){
                LDL f = it.next();
                SubTesterContainer T = this.SubTester_Containers.get(f);
                s += "--  (" + (i++) + ") " + T.out.getText(true) + ": " + f.getText(true) + "\r\n";
                smvCode += "\r\n"+subTesterToSMV(T, false, false);
            }
            s += smvCode;
        }

        return s;
    }

}
