
import java.util.*;
import java.util.stream.Collectors;

// Path Grammar Class for a path expression
public class PathGrammar {
    static int builtVariableCount = 0;
    LDL pathExpr;
//    Set<String> variables;
//    Set<PathGrammarTerminal> terminals;
    Set<PathGrammarProduction> productions;
    String start; // the start symbol in the set variables
    String variableNamePrefix = "";

//    String str_pathGrammar_before_optimization = "";


    PathGrammar(LDL pathExpr) throws CloneNotSupportedException {
        if (!pathExpr.isPathExpression(true)) {
            System.out.println("Error: the following formula is not path expression: " + pathExpr.getText(true));
            return;
        }
        this.pathExpr = pathExpr;
//        this.variables = new HashSet<>();
//        this.terminals = new HashSet<>();
        this.productions = new HashSet<>();
        buildPathGrammarRecur(pathExpr);

//        str_pathGrammar_before_optimization = this.toString();
//        optimization();

    }

    String addNewVariable() {
        return variableNamePrefix + (++builtVariableCount);
    }

    // The formula f must be a legal path expression
    boolean buildPathGrammarRecur(LDL f) throws CloneNotSupportedException {
        String newVar;
        if(f.isPropFormula()) {
            newVar = addNewVariable();
            start = newVar;
            productions.add(new PathGrammarProduction(newVar, f));
            return true;
        }
        switch (f.operator) {
            case TEST: {
                newVar = addNewVariable();
                start = newVar;
                productions.add(new PathGrammarProduction(newVar, f));
                return true;
            }
            case REPETITION:
                buildPathGrammar4REPETITION(f);
                return true;
            case CONCAT:
                buildPathGrammar4CONCAT(f);
                return true;
            case UNION:
                buildPathGrammar4UNION(f);
                return true;
            default:
                return false;
        }
    }

    // The main operator of LDL formula f must be CONCAT
    void buildPathGrammar4CONCAT(LDL f) throws CloneNotSupportedException {
        if (f.operator != LDL.Operators.CONCAT) return;
        PathGrammar pg1, pg2;
        pg1 = new PathGrammar(f.children.get(0));
        pg2 = new PathGrammar(f.children.get(1));
        this.start = pg1.start;
//        this.variables.addAll(pg1.variables);
//        this.variables.addAll(pg2.variables);
    //    this.terminals.addAll(pg1.terminals);
    //    this.terminals.addAll(pg2.terminals);

        PathGrammarProduction eProd1;
        for (PathGrammarProduction p1 : pg1.productions) {
            switch (p1.type) {
                case Empty: // v->empty in P1
                    this.productions.add(new PathGrammarProduction(p1.leftVariable, pg2.start)); //v->S2
                    break;
                case Test: // v->LDL? in P1
                case PropFormula: // v->propLDL in P1
                    this.productions.add(new PathGrammarProduction(p1.leftVariable, (LDL) p1.rightItem1, pg2.start)); // v->LDL?.S2 | v->propLDL.S2
                    break;
                case Variable: // v->w in P1
                    this.productions.add(p1); // v->w
                    eProd1 = new PathGrammarProduction((String) p1.rightItem1); // eProd1 = w->empty
                    if(pg1.productions.contains(eProd1))
                        this.productions.add(new PathGrammarProduction(p1.leftVariable, pg2.start)); // v->S2
                    break;
                case Test_Variable: // v->LDL?.w in P1
                case PropFormula_Variable: // v->propLDL.w in P1
                    this.productions.add(p1); // v->LDL?.w | v->propLDL.w
                    eProd1 = new PathGrammarProduction((String) p1.rightItem2); // w->empty
                    if(pg1.productions.contains(eProd1))
                        this.productions.add(new PathGrammarProduction(p1.leftVariable, (LDL) p1.rightItem1, pg2.start)); // v->LDL?.S2 | v->propLDL.S2
                    break;
                case Test_PropFormula:
                    break;
                case PropFormula_Test:
                    break;
                default:

            }
        }
        this.productions.addAll(pg2.productions);
    }

    // The main operator of LDL formula f must be UNION
    void buildPathGrammar4UNION(LDL f) {
        if (f.operator != LDL.Operators.UNION) return;

        PathGrammar[] pg = new PathGrammar[2];
        try {
            pg[0] = new PathGrammar(f.children.get(0));
            //pg[0].print();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            pg[1] = new PathGrammar(f.children.get(1));
            //pg[1].print();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        String newVar = addNewVariable(); // v
        this.start = newVar;

        this.productions.addAll(pg[0].productions);
        this.productions.addAll(pg[1].productions);

        this.productions.add(new PathGrammarProduction(newVar, pg[0].start)); // v->S1
        this.productions.add(new PathGrammarProduction(newVar, pg[1].start)); // v->S2


    }

    // The main operator of LDL formula f must be UNION
    void buildPathGrammar4UNION_old(LDL f) {
        if (f.operator != LDL.Operators.UNION) return;

        PathGrammar[] pg = new PathGrammar[2];
        try {
            pg[0] = new PathGrammar(f.children.get(0));
            System.out.println(pg[0]);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            pg[1] = new PathGrammar(f.children.get(1));
            System.out.println(pg[1]);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        String newVar = addNewVariable(); // v
        this.start = newVar;

//        this.variables.add(newVar);
//        this.variables.addAll(pg[0].variables);
//        this.variables.addAll(pg[1].variables);
    //    this.terminals.addAll(pg[1].terminals);
    //    this.terminals.addAll(pg[1].terminals);

        PathGrammarProduction eProd;
        for(int i=0; i<2; i++) {
            for (PathGrammarProduction prod : pg[i].productions) {
                if (prod.leftVariable.equals(pg[i].start)) { // v==Si
                    switch (prod.type) {
                        case Empty: // Si->empty in Pi
                            this.productions.add(new PathGrammarProduction(newVar)); // v->empty
                            break;
                        case Test: // Si->LDL? in Pi
                        case PropFormula: // Si->propLDL
                            this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1)); // v->LDL? | v->propLDL
                            break;
                        case Variable: // Si->w in Pi
                            this.productions.add(new PathGrammarProduction(newVar, (String) prod.rightItem1)); // v->w
                            eProd = new PathGrammarProduction((String) prod.rightItem1); //w->empty
                            if (pg[i].productions.contains(eProd))
                                this.productions.add(new PathGrammarProduction(newVar)); // v->empty
                            break;
                        case Test_Variable: // Si->LDL?.w in Pi
                            if (!prod.leftVariable.equals(prod.rightItem2)) // Si<>w
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1, (String) prod.rightItem2)); // v->LDL?.w
                            else // Si==w
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1, newVar)); // v->LDL?.v
                            eProd = new PathGrammarProduction((String) prod.rightItem2); // w->empty
                            if (pg[i].productions.contains(eProd))
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1)); // v->LDL?
                            break;
                        case PropFormula_Variable: // Si->propLDL.w in Pi
                            if (!prod.leftVariable.equals(prod.rightItem2)) // Si<>w
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1, (String) prod.rightItem2)); // v->LDL?.w
                            else // Si==w
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1, newVar)); // v->LDL?.v
                            eProd = new PathGrammarProduction((String) prod.rightItem2); // w->empty
                            if (pg[i].productions.contains(eProd))
                                this.productions.add(new PathGrammarProduction(newVar, (LDL) prod.rightItem1)); // v->propLDL
                            break;
                        case Illegal:
                            break;
                        default:
                            //case Test_PropFormula:
                            //case PropFormula_Test:
                            //  this.productions.add(prod);
                    }
                } else { // v<>>Si
                    this.productions.add(prod);
                }
            }
        }
    }

    // The main operator of LDL formula f must be REPETITION
    void buildPathGrammar4REPETITION(LDL f) {
        if (f.operator != LDL.Operators.REPETITION) return;
        PathGrammar pg1;
        try {
            pg1 = new PathGrammar(f.children.get(0));
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        this.start = pg1.start;
//        this.variables.addAll(pg1.variables);
//        this.terminals.addAll(pg1.terminals);

        this.productions.add(new PathGrammarProduction(pg1.start)); // S1->empty

        PathGrammarProduction eProd1;
        for (PathGrammarProduction prod1 : pg1.productions) {
            switch (prod1.type) {
                case Empty: // v->empty in P1
                    if(!prod1.leftVariable.equals(pg1.start)) { // v<>S1
                        this.productions.add(prod1); //add v->empty
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg1.start)); // add v->S1
                    } else // v==S1
                        this.productions.add(new PathGrammarProduction(pg1.start)); // add S1->empty
                    break;
                case Test: // v->LDL?
                case PropFormula: // v->propLDL
                    this.productions.add(prod1); //add v->LDL? | v->propLDL
                    this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg1.start)); //add v->LDL?.S1 | v->propLDL.S1
                    break;
                case Variable: // v->w
                    this.productions.add(prod1); // add v->w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem1); // w->empty
                    if(pg1.productions.contains(eProd1)) { // w->empty in P1
                        if (!prod1.leftVariable.equals(pg1.start)) { // v<>S1
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable)); // add v->empty
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg1.start)); // add v->S1
                        } else { // v==S1
                            this.productions.add(new PathGrammarProduction(pg1.start)); // add S1->empty
                        }
                    }
                    break;
                case Test_Variable: // v->LDL?.w
                    this.productions.add(prod1); // add v->LDL?.w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem2); // w->empty
                    if(pg1.productions.contains(eProd1)) { // w->empty in P1
                        if (!prod1.leftVariable.equals(pg1.start)) { // v<>S1
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1)); // add v->LDL?
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg1.start)); // add v->LDL?.S1
                        } else { // v==S1
                            this.productions.add(new PathGrammarProduction(pg1.start, (LDL) prod1.rightItem1)); // add S1->LDL?
                        }
                    }
                    break;
                case PropFormula_Variable: // v->propLDL.w
                    this.productions.add(prod1); //  add v->propLDL.w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem2); // w->empty
                    if(pg1.productions.contains(eProd1)) { // w->empty in P1
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1)); // add v->propLDL
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg1.start)); // add v->propLDL.S1
                    }
                    break;
                case Test_PropFormula:
                    break;
                case PropFormula_Test:
                    break;
                default:

            }
        }
    }

    public Set<String> getVariables() {
        Set<String> vars = new HashSet<>();
        for (PathGrammarProduction prod : this.productions) {
            vars.add(prod.leftVariable);
            switch (prod.type) {
                case Empty:
                case Test:
                case PropFormula:
                case PropFormula_Test:
                    break;
                case Variable:
                    vars.add((String) prod.rightItem1);
                    break;
                case Test_Variable:
                case PropFormula_Variable:
                    vars.add((String) prod.rightItem2);
                    break;
                case Test_PropFormula:
                    break;
            }
        }
        return vars;
    }

/*
    // get productions starting from variable leftVar
    public Set<PathGrammarProduction> getProds(String leftVar) {
        Set<PathGrammarProduction> prods = new HashSet<>();
        for (PathGrammarProduction p : this.productions) {
            if (p.leftVariable.equals(leftVar)) prods.add(p);
        }
        return prods;
    }
*/
// get productions that use variable rightVar
public List<PathGrammarProduction> getProdsUsingVar(String rightVar) {
    List<PathGrammarProduction> prods = new LinkedList<>();
    for (PathGrammarProduction p : this.productions) {
        switch (p.type){
            case Variable:
                if(p.rightItem1.equals(rightVar)) prods.add(p);
                break;
            case Test_Variable:
            case PropFormula_Variable:
                if(p.rightItem2.equals(rightVar)) prods.add(p);
                break;
            default:
                break;
        }
    }
    return prods;
}

    // get productions starting from variable leftVar
    public List<PathGrammarProduction> getProds(String leftVar) {
        List<PathGrammarProduction> prods = new LinkedList<>();
        for (PathGrammarProduction p : this.productions) {
            if (p.leftVariable.equals(leftVar)) prods.add(p);
        }

        // prods按照leftVariable, type, rightItem1, rightItem2排序
        Collections.sort(prods, new Comparator<PathGrammarProduction>() {
            @Override
            public int compare(PathGrammarProduction p1, PathGrammarProduction p2) {
                int res1 = p1.leftVariable.compareTo(p2.leftVariable);
                if (res1!=0) return res1;
                int res2 = p1.type.compareTo(p2.type);
                if (res2!=0) return res2;
                switch (p1.type){
                    case Empty:
                        break;
                    case Test:
                    case PropFormula:
                        return ((LDL)p1.rightItem1).getText(true).compareTo(((LDL)p2.rightItem1).getText(true));
                    case Variable:
                        return ((String)p1.rightItem1).compareTo(((String)p2.rightItem1));
                    case Test_Variable:
                    case PropFormula_Variable:
                        int res3 = ((LDL)p1.rightItem1).getText(true).compareTo(((LDL)p2.rightItem1).getText(true));
                        if(res3!=0) return res3;
                        return ((String)p1.rightItem2).compareTo(((String)p2.rightItem2));
                    case Test_PropFormula:
                        break;
                    case PropFormula_Test:
                        break;
                }
                return res2;
            }
        });
        return prods;
    }


    // get productions
    public Set<PathGrammarProduction> getProds(String leftVar, PathGrammarProduction.Type type) {
        Set<PathGrammarProduction> prods = new HashSet<>();
        for (PathGrammarProduction p : this.productions) {
            if (p.leftVariable.equals(leftVar) && p.type == type)
                prods.add(p);
        }
        return prods;
    }


    // get productions of the form
    public Set<PathGrammarProduction> getTwoVarsProds(PathGrammarProduction.Type type, String leftVar, String rightVar) {
        Set<PathGrammarProduction> prods = new HashSet<>();
        for (PathGrammarProduction p : this.productions) {
            if (p.leftVariable.equals(leftVar) && p.type == type) {
                switch (type) {
                    case Variable:
                        if(p.rightItem1.equals(rightVar)) prods.add(p);
                        break;
                    case Test_Variable:
                    case PropFormula_Variable:
                        if(p.rightItem2.equals(rightVar)) prods.add(p);
                        break;
                    default:
                }
            }
        }
        return prods;
    }

    public String productionsToString() {
        String s="";
        Set<String> varsVisited = new HashSet<>();
        Queue<String> Qvars = new ArrayDeque<>();

        Qvars.add(this.start);
        int j=0;
        while(!Qvars.isEmpty()) {
            //访问队首元素
            String var = Qvars.remove();
            varsVisited.add(var);
            //访问var的所有产生式
            List<PathGrammarProduction> varProds = this.getProds(var);
            // varProds按照leftVariable, type, rightItem1, rightItem2排序
            List<PathGrammarProduction> prods = new ArrayList<>(varProds);
            Collections.sort(prods, new Comparator<PathGrammarProduction>() {
                @Override
                public int compare(PathGrammarProduction p1, PathGrammarProduction p2) {
                    int res1 = p1.leftVariable.compareTo(p2.leftVariable);
                    if (res1!=0) return res1;
                    int res2 = p1.type.compareTo(p2.type);
                    if (res2!=0) return res2;
                    switch (p1.type){
                        case Empty:
                            break;
                        case Test:
                        case PropFormula:
                            return ((LDL)p1.rightItem1).getText(true).compareTo(((LDL)p2.rightItem1).getText(true));
                        case Variable:
                            return ((String)p1.rightItem1).compareTo(((String)p2.rightItem1));
                        case Test_Variable:
                        case PropFormula_Variable:
                            int res3 = ((LDL)p1.rightItem1).getText(true).compareTo(((LDL)p2.rightItem1).getText(true));
                            if(res3!=0) return res3;
                            return ((String)p1.rightItem2).compareTo(((String)p2.rightItem2));
                        case Test_PropFormula:
                            break;
                        case PropFormula_Test:
                            break;
                    }
                    return res2;
                }
            });

            for (PathGrammarProduction prod : prods) {
                s+="    ("+(++j)+") "+prod.getText()+"\r\n";
                //var的后继变量入队
                String rightVar = prod.getRightVariable();
                if(rightVar!=null && !varsVisited.contains(rightVar)) {
                    Qvars.add(rightVar);
                    varsVisited.add(rightVar);
                }
            }
        }
        return s;
    }

    /*public void printProductions() {
        Set<String> varsVisited = new HashSet<>();
        Queue<String> Qvars = new ArrayDeque<>();

        Qvars.add(this.start);
        int j=0;
        while(!Qvars.isEmpty()) {
            //访问队首元素
            String var = Qvars.remove();
            varsVisited.add(var);
            //访问var的所有产生式
            Set<PathGrammarProduction> varProds = this.getProds(var);
            for (PathGrammarProduction prod : varProds) {
                System.out.println("    "+(++j)+". "+prod.getText());
                //var的后继变量入队
                String rightVar = prod.getRightVariable();
                if(rightVar!=null && !varsVisited.contains(rightVar)) {
                    Qvars.add(rightVar);
                    varsVisited.add(rightVar);
                }
            }
        }
    }*/

    // Input: the number of current grammar variables
    public void renameProductions(int firstVariableNumber) {
        Set<String> varsVisited = new HashSet<>();
        Queue<String> Qvars = new ArrayDeque<>();
        int i=firstVariableNumber;
        Map<String, Integer> map = new HashMap<>();
        Qvars.add(this.start);
        //为所有变量赋予新编号（开始符号编号为1）
        while(!Qvars.isEmpty()) {
            //访问队首元素
            String var = Qvars.remove();
            map.put(var, i++);
            varsVisited.add(var);
            //访问var的所有产生式
            List<PathGrammarProduction> varProds = this.getProds(var);
            for (PathGrammarProduction prod : varProds) {
                //var的后继变量入队
                String rightVar = prod.getRightVariable();
                if(rightVar!=null && !varsVisited.contains(rightVar)) {
                    Qvars.add(rightVar);
                    varsVisited.add(rightVar);
                }
            }
        }

        //按照map编号修改所有产生式中的变量名
        this.start = variableNamePrefix + map.get(this.start);
        for(PathGrammarProduction p : this.productions) {
            p.leftVariable = variableNamePrefix + map.get(p.leftVariable);
            switch (p.type) {
                case Variable:
                    p.rightItem1 = variableNamePrefix + map.get(p.rightItem1);
                    break;
                case Test_Variable:
                case PropFormula_Variable:
                    p.rightItem2 = variableNamePrefix + map.get(p.rightItem2);
                    break;
            }
        }
    }

    public String toString() {
        String s="";
        s+="The Path Grammar of "+this.pathExpr.getText(true)+":\r\n";
        s+="  Start variable: "+this.start+"\r\n";

        s+="  Variables: "+this.getVariables().stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList())
                .toString()+"\r\n";

        s+="  Productions:\r\n";

        s+=productionsToString();
        return s;
    }

    /*public void print() {
        System.out.println("The Path Grammar of "+this.pathExpr.getText()+":");
        System.out.println("  Start variable: "+this.start);
        String vars = "";
        for(String var : this.getVariables())
            vars += var + " ";
        System.out.println("  Variables: "+vars);
        System.out.println("  Productions:");
        printProductions();
    }*/

    // get all productions to variable var
    public Set<PathGrammarProduction> getVarProdsTo(String var) {
        Set<PathGrammarProduction> prods = new HashSet<>();
        for (PathGrammarProduction prod : this.productions) {
            if(prod.type == PathGrammarProduction.Type.Variable && var.equals(prod.rightItem1))
                prods.add(prod);
        }
        return prods;
    }

    public boolean mergeProductions() {
        // prods按照leftVariable(v), type, rightItem2(w)排序
        List<PathGrammarProduction> prods = new ArrayList<>(this.productions);
        Collections.sort(prods, new Comparator<PathGrammarProduction>() {
            @Override
            public int compare(PathGrammarProduction p1, PathGrammarProduction p2) {
                int res1 = p1.leftVariable.compareTo(p2.leftVariable);
                if (res1!=0) return res1;
                int res2 = p1.type.compareTo(p2.type);
                if (res2!=0) return res2;
                if (p1.rightItem2!=null && p2.rightItem2!=null &&
                        (p1.type == PathGrammarProduction.Type.Test_Variable || p1.type == PathGrammarProduction.Type.PropFormula_Variable))
                    return p1.rightItem2.toString().compareTo(p2.rightItem2.toString());
                else
                    return 0;
            }
        });

        /*System.out.println("Productions sorted by leftVar, Type, rightItem2:");
        for(int i=0; i<prods.size(); i++) {
            System.out.println(prods.get(i).getText() +
                    "  " + prods.get(i).type.name() +
                    "  " + prods.get(i).type.getValue()
            );
        }*/

        //合并test, prop, test_variable, prop_variable产生式
        String curVar="";
        PathGrammarProduction.Type curType=null;
        String curRightItem2="";
        int startIdx=0;
        int endIdx=0;
        int i=0;
        boolean changed=false;
        while(i<prods.size()) {
            //走到可合并的第i个产生式
            boolean found=false;
            while (i < prods.size() && !found) {
                switch (prods.get(i).type) {
                    case Test:
                    case PropFormula:
                    case Test_Variable:
                    case PropFormula_Variable:
                        found=true;
                        break;
                    default:
                        i++;
                }
            }
            //从第i个产生式开始合并
            if(found) {
                curVar=prods.get(i).leftVariable;
                curType=prods.get(i).type;
                curRightItem2=(String)prods.get(i).rightItem2;
                startIdx=i;
                i++;
                switch (curType){
                    case Test:
                    case PropFormula:
                        while(i<prods.size() && prods.get(i).leftVariable.equals(curVar) &&
                                prods.get(i).type==curType) i++;
                        endIdx=i-1;
                        if (endIdx-startIdx>0) {
                            if(curType == PathGrammarProduction.Type.Test) {
                                //需要将v->LDL1?,...,v->LDLm?合并为v->(LDL1 | ... | LDLm)?
                                LDL tf=((LDL)prods.get(startIdx).rightItem1).children.get(0);
                                for (int j = startIdx+1; j <= endIdx; j++) {
                                    tf = new LDL(LDL.Operators.OR, tf, ((LDL)prods.get(j).rightItem1).children.get(0));
                                }
                                tf = new LDL(false, LDL.Operators.TEST, tf);
                                PathGrammarProduction newProd = new PathGrammarProduction(curVar, tf);
                                for(int j=startIdx; j<=endIdx; j++) this.productions.remove(prods.get(j));
                                this.productions.add(newProd);
                            }else{ // curType==PropFormula
                                //需要将v->LDL1,...,v->LDLm合并为v->(LDL1 | ... | LDLm)
                                LDL bf=(LDL)prods.get(startIdx).rightItem1;
                                for (int j = startIdx+1; j <= endIdx; j++) {
                                    bf = new LDL(LDL.Operators.OR, bf, (LDL)prods.get(j).rightItem1);
                                }
                                PathGrammarProduction newProd = new PathGrammarProduction(curVar, bf);
                                for(int j=startIdx; j<=endIdx; j++) this.productions.remove(prods.get(j));
                                this.productions.add(newProd);
                            }
                            changed=true;
                        }
                        break;
                    case Test_Variable:
                    case PropFormula_Variable:
                        while(i<prods.size() && prods.get(i).leftVariable.equals(curVar) &&
                                prods.get(i).type==curType && prods.get(i).rightItem2.equals(curRightItem2)) i++;
                        endIdx=i-1;
                        if (endIdx-startIdx>0) {
                            if(curType == PathGrammarProduction.Type.Test) {
                                //需要将v->LDL1?w,...,v->LDLm?w合并为v->(LDL1 | ... | LDLm)?w
                                LDL tf=((LDL)prods.get(startIdx).rightItem1).children.get(0);
                                for (int j = startIdx+1; j <= endIdx; j++) {
                                    tf = new LDL(LDL.Operators.OR, tf, ((LDL)prods.get(j).rightItem1).children.get(0));
                                }
                                tf = new LDL(false, LDL.Operators.TEST, tf);
                                PathGrammarProduction newProd = new PathGrammarProduction(curVar, tf, curRightItem2);
                                for(int j=startIdx; j<=endIdx; j++) this.productions.remove(prods.get(j));
                                this.productions.add(newProd);
                            }else{ // curType==PropFormula
                                //需要将v->LDL1.w,...,v->LDLm.w合并为v->(LDL1 | ... | LDLm).w
                                LDL bf=(LDL)prods.get(startIdx).rightItem1;
                                for (int j = startIdx+1; j <= endIdx; j++) {
                                    bf = new LDL(LDL.Operators.OR, bf, (LDL)prods.get(j).rightItem1);
                                }
                                PathGrammarProduction newProd = new PathGrammarProduction(curVar, bf, curRightItem2);
                                for(int j=startIdx; j<=endIdx; j++) this.productions.remove(prods.get(j));
                                this.productions.add(newProd);
                            }
                            changed=true;
                        }
                        break;
                    default:

                }

            }
        }
        return changed;
    }

    // reduce productions
    // NOTE: do NOT reduce to the following forms: v-->f?.a or v-->a.f?
    public boolean reduceProductions() {
        ////////////////////////////////////////////////////////////////////////////////////////
        // (1) 删除满足以下条件的所有变量及其产生式：该变量只有一个产生式，并且该变量被其他产生式使用。该变量被删除前需修改使用它的产生式以消除该变量的引用。
        ////////////////////////////////////////////////////////////////////////////////////////
        List<PathGrammarProduction> od1Prods = new ArrayList<>(); // 出度为1的产生式
        Set<PathGrammarProduction> toRemove = new HashSet<>();
        Set<PathGrammarProduction> toAdd = new HashSet<>();
        PathGrammarProduction newP;
        boolean changed=false;
        boolean oneRoundChanged=false;
        do {
            //将所有出度为1的产生式存入od1Prods，起始产生式除外
            od1Prods.clear();
            Set<String> vars = getVariables();
            for(String v : vars) {
                if(v.equals(start)) continue;
                List<PathGrammarProduction> tps = getProds(v);
                if (tps.size()==1)
                    od1Prods.add((PathGrammarProduction) tps.toArray()[0]);
            }

            //削减产生式，可能生成新的出度为1的产生式
            PathGrammarProduction p,t;
            boolean reserveThisOd1Prod;
            Iterator<PathGrammarProduction> itProds;
            ListIterator<PathGrammarProduction> itOd1Prods = od1Prods.listIterator();
            oneRoundChanged=false;
            boolean pChanged=false;
            while (itOd1Prods.hasNext()) {
                t = itOd1Prods.next();
                switch (t.type) {
                    case Empty: // t:w-->empty
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p);
                                    newP = new PathGrammarProduction(p.leftVariable); // v-->empty
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v-->f1?.w
                                case PropFormula_Variable: // p:v-->f1.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) p.rightItem1); // v-->f1? | v-->f1
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(pChanged) {
                            toRemove.add(t); // remove t
                            oneRoundChanged = true;
                            this.productions.removeAll(toRemove);
                            this.productions.addAll(toAdd);
                            toRemove.clear();
                            toAdd.clear();
                        }
                        break;
                    case Test: // t:w-->f2?
                        reserveThisOd1Prod=false;
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1); // v-->f2?
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v-->f1?w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(false, LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                                    ((LDL)p.rightItem1).children.get(0),
                                                    ((LDL)t.rightItem1).children.get(0)));
                                    newP = new PathGrammarProduction(p.leftVariable, tf); // v-->(f1 & f2)?
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case PropFormula_Variable: // p:v-->a.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    reserveThisOd1Prod=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!reserveThisOd1Prod) {
                            if(pChanged){ // all uses of w are reduced
                                toRemove.add(t); // remove t
                                oneRoundChanged=true;
                                this.productions.removeAll(toRemove);
                                this.productions.addAll(toAdd);
                                toRemove.clear();
                                toAdd.clear();
                            }
                        }else{ // at least one use of w is preserved (v-->a w)
                            if(pChanged){
                                toRemove.clear();
                                toAdd.clear();
                                break;
                            }
                        }
                        break;
                    case PropFormula: // t:w-->b
                        reserveThisOd1Prod=false;
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1); // v-->b
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v-->f1?w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    LDL f1 = ((LDL)p.rightItem1).children.get(0);
                                    LDL b = (LDL)t.rightItem1;
                                    if(f1.isPropFormula()) { // f1 is an assertion
                                        toRemove.add(p); // remove p
                                        LDL rightItem1 = new LDL(LDL.Operators.AND, f1, b); // rightItem1 = f1 & b
                                        newP = new PathGrammarProduction(p.leftVariable, rightItem1); // v-->(f1 & b)
                                        toAdd.add(newP);
                                        pChanged = true;
                                    }else { // f1 is NOT an assertion
                                        reserveThisOd1Prod=true;
                                    }
                                    break;
                                case PropFormula_Variable: // p:v-->a w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    reserveThisOd1Prod=true; // t:w-->b has to be preserved
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!reserveThisOd1Prod) {
                            if(pChanged){ // all uses of w are reduced
                                toRemove.add(t); // remove t
                                oneRoundChanged=true;
                                this.productions.removeAll(toRemove);
                                this.productions.addAll(toAdd);
                                toRemove.clear();
                                toAdd.clear();
                            }
                        }else{ // at least one use of w is preserved (v-->f1?w  or  v-->a w)
                            if(pChanged){
                                toRemove.clear();
                                toAdd.clear();
                                break;
                            }
                        }
                        break;
                    case Variable: // t:w->w'
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable) || p==t) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (String) t.rightItem1); // v-->w'
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v-->f1?.w
                                case PropFormula_Variable: // p:v-->f1.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) p.rightItem1, (String) t.rightItem1); // v-->f1?.w' | v-->f1.w'
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(pChanged) {
                            toRemove.add(t); // remove t
                            oneRoundChanged = true;
                            this.productions.removeAll(toRemove);
                            this.productions.addAll(toAdd);
                            toRemove.clear();
                            toAdd.clear();
                        }
                        break;
                    case Test_Variable: // t:w-->f2?.w'
                        reserveThisOd1Prod=false;
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1, (String) t.rightItem2); // v-->f2?.w'
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v-->f1?.w
                                    if(!p.rightItem2.equals(t.leftVariable) || p==t) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(false, LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                                    ((LDL)p.rightItem1).children.get(0),
                                                    ((LDL)t.rightItem1).children.get(0))); // tf=(f1 & f2)?
                                    newP = new PathGrammarProduction(p.leftVariable, tf, (String) t.rightItem2); // v-->(f1 & f2)?.w'
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case PropFormula_Variable: // p:v->ldl.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    reserveThisOd1Prod=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!reserveThisOd1Prod) {
                            if(pChanged){ // all uses of w are reduced
                                toRemove.add(t); // remove t
                                oneRoundChanged=true;
                                this.productions.removeAll(toRemove);
                                this.productions.addAll(toAdd);
                                toRemove.clear();
                                toAdd.clear();
                            }
                        }else{ // at least one use of w is preserved (v-->a w)
                            if(pChanged){
                                toRemove.clear();
                                toAdd.clear();
                                break;
                            }
                        }
                        break;
                    case PropFormula_Variable: // t:w-->b.w'
                        reserveThisOd1Prod=false;
                        itProds = this.productions.iterator();
                        pChanged=false;
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v-->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1, (String) t.rightItem2); // v-->b.w'
                                    toAdd.add(newP);
                                    pChanged=true;
                                    break;
                                case Test_Variable: // p:v->f1?.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    LDL f1 = ((LDL)p.rightItem1).children.get(0);
                                    LDL b = (LDL)t.rightItem1;
                                    if(f1.isPropFormula()) { // f1 is an assertion
                                        toRemove.add(p); // remove p
                                        LDL rightItem1 = new LDL(LDL.Operators.AND, f1, b); // rightItem1 = f1 & b
                                        newP = new PathGrammarProduction(p.leftVariable, rightItem1, (String)t.rightItem2); // v-->(f1 & b).w'
                                        toAdd.add(newP);
                                        pChanged = true;
                                    }else { // f1 is NOT an assertion
                                        reserveThisOd1Prod=true;
                                    }
                                    break;
                                case PropFormula_Variable: // p:v-->a.w
                                    if(!p.rightItem2.equals(t.leftVariable) || p==t) continue;
                                    reserveThisOd1Prod=true; //在Test_Variable或PropFormula_Variable两种情况下都为真
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!reserveThisOd1Prod) {
                            if(pChanged){ // all uses of w are reduced
                                toRemove.add(t); // remove t
                                oneRoundChanged=true;
                                this.productions.removeAll(toRemove);
                                this.productions.addAll(toAdd);
                                toRemove.clear();
                                toAdd.clear();
                            }
                        }else{ // at least one use of w is preserved (v-->a w)
                            if(pChanged){
                                toRemove.clear();
                                toAdd.clear();
                                break;
                            }
                        }
                        break;
                    case Test_PropFormula: // t:w-->f2?.b
                        break;
                    case PropFormula_Test: // t:w-->b.f2?
                        break;
                    default:
                        break;
                }
            }
            if (oneRoundChanged){
                /*this.productions.removeAll(toRemove);
                this.productions.addAll(toAdd);
                toRemove.clear();
                toAdd.clear();*/
                changed=true;
            }

        } while (oneRoundChanged);

        ////////////////////////////////////////////////////////////////////////////////////////
        // (2) 删除满足以下条件的所有变量及其产生式：该变量只有一个产生式，并且该变量被其他产生式使用。该变量被删除前需修改使用它的产生式以消除该变量的引用。
        ////////////////////////////////////////////////////////////////////////////////////////
        boolean reduced=false;
        List<String> vars = new LinkedList<>(getVariables());
        int i=0;
        String w;
        do{
            toRemove.clear();
            toAdd.clear();
            reduced=false;
            while(i<vars.size()){
                w = vars.get(i);
                i++;
                Set<PathGrammarProduction> prodsUsedW = prodsUsingVar(this.productions, w); // prodsUsedW是直接使用变量w的所有产生式集合
                if(prodsUsedW.size()==1){
                    PathGrammarProduction p = (PathGrammarProduction) prodsUsedW.toArray()[0];
                    if(p.type == PathGrammarProduction.Type.Variable) {
                        // p:v->w 是使用变量w的唯一一个产生式
                        Set<PathGrammarProduction> usedProdsFromW=new HashSet<>();
                        Set<String> usedVarsFromW=new HashSet<>();
                        getUsedProdsAndVars(w, p.leftVariable, usedProdsFromW, usedVarsFromW);
                        if(!usedVarsFromW.contains(w)) {
                            //如果从w开始的产生式有向图中所使用的变量集合不包含w（特别注意，从w开始最多只能前向走到v！）
                            //(1)将所有w产生式的w替换为v
                            for(PathGrammarProduction wp : getProds(w)) {
                                //wp.leftVariable = p.leftVariable;
                                String str_old_wp = wp.getText();
                                wp.leftVariable = p.leftVariable;
/*
                                newP=null;
                                switch (wp.type) {
                                    case Empty:
                                        newP = new PathGrammarProduction(p.leftVariable);
                                        break;
                                    case Test:
                                    case PropFormula:
                                        newP = new PathGrammarProduction(p.leftVariable, (LDL) wp.rightItem1);
                                        break;
                                    case Variable:
                                        newP = new PathGrammarProduction(p.leftVariable, (String) wp.rightItem1);
                                        break;
                                    case Test_Variable:
                                    case PropFormula_Variable:
                                        newP = new PathGrammarProduction(p.leftVariable, (LDL) wp.rightItem1, (String) wp.rightItem2);
                                        break;
                                }
                                toRemove.add(wp);
                                toAdd.add(newP);

                                if (MainLdl_ltlVisitor.OutputLevel >0)
                                    System.out.println(wp.getText() + " is modified as " + newP.getText() );
*/
                                if (MainLtdlVisitor.OutputLevel >0)
                                    System.out.println(str_old_wp + " is modified as " + wp.getText());
                            }

                            //(2)删除p:v->w
                            toRemove.add(p);
                            reduced=true;
                            changed=true;

                            if (MainLtdlVisitor.OutputLevel >0) {
                                System.out.println("The production removed: " + p.getText());
                                System.out.println("The variable removed: " + w);
                            }

                            //注意：必须尽可能快地更新集合，否则可能导致集合错误。另外，修改元素也应该采用先删除后添加元素的方式实现
                            this.productions.removeAll(toRemove); toRemove.clear();
                            this.productions.addAll(toAdd); toAdd.clear();

                            //更新var set
                            vars = new LinkedList<>(getVariables());
                            i=0;

                        }
                    }
                }
            }

        }while(reduced);

        return changed;
    }




    // 联合两个产生式（不考虑对其他产生式的影响）
    // 成功联合的条件：
    //  (1) 左产生式v->tw满足v不等于w，右产生式为w->t'或w->t'w'，其中t,t'为终结符（empty, tests, assertions），w不等于w'
    //  (2) v->tt'或v->tt'w'可被转化为合法产生式v->t"或v->t"w，其中终结符t"的语义与tt'等价
    // 输出：合并成功则返回合法产生式v->t"或v->t"w，否则返回null
    PathGrammarProduction unite2productions(PathGrammarProduction lProd, PathGrammarProduction rProd) {
        PathGrammarProduction newP = null;
        if(lProd==null || rProd==null) return null;
        if(lProd.type!=PathGrammarProduction.Type.Variable &&
                lProd.type!=PathGrammarProduction.Type.Test_Variable &&
                lProd.type!=PathGrammarProduction.Type.PropFormula_Variable) return null;
        String lProd_rightVariable=lProd.getRightVariable();
        if(lProd_rightVariable==null || lProd_rightVariable.equals(lProd.leftVariable)) return null;
        //现在leftProd满足条件(1)
        if(!rProd.leftVariable.equals(lProd_rightVariable)) return null;
        String rProd_rightVariable=rProd.getRightVariable();
        if(rProd_rightVariable!=null && rProd_rightVariable.equals(rProd.leftVariable)) return null;
        //现在rightProd符合条件(1)
        //合成t"=tt'
        switch (lProd.type) {
            case Variable:
                switch (rProd.type) {
                    case Empty:
                        return new PathGrammarProduction(lProd.leftVariable);
                    case Test:
                    case PropFormula:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)rProd.rightItem1);
                    case Variable:
                        return new PathGrammarProduction(lProd.leftVariable, (String)rProd.rightItem1);
                    case Test_Variable:
                    case PropFormula_Variable:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)rProd.rightItem1, (String)rProd.rightItem2);
                    default:
                        break;
                }
                break;
            case Test_Variable:
                switch (rProd.type) {
                    case Empty:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)lProd.rightItem1);
                    case Test:
                        LDL ltf=((LDL)lProd.rightItem1).children.get(0);
                        LDL rtf=((LDL)rProd.rightItem1).children.get(0);
                        LDL lrt=new LDL(LDL.Operators.TEST,
                                        new LDL(LDL.Operators.AND, ltf, rtf)); // lrt = (ltf & rtf)?
                        return new PathGrammarProduction(lProd.leftVariable, lrt);
                    case PropFormula:
                        ltf = ((LDL) lProd.rightItem1).children.get(0);
                        if(ltf.isPropFormula()){
                            lrt=new LDL(LDL.Operators.AND, ltf, (LDL)rProd.rightItem1); // lrt = ltf & rt
                            return new PathGrammarProduction(lProd.leftVariable, lrt);
                        }else return null;
                    case Variable:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)lProd.rightItem1, (String)rProd.rightItem1);
                    case Test_Variable:
                        ltf=((LDL)lProd.rightItem1).children.get(0);
                        rtf=((LDL)rProd.rightItem1).children.get(0);
                        lrt=new LDL(LDL.Operators.TEST,
                                new LDL(LDL.Operators.AND, ltf, rtf)); // lrt = (ltf & rtf)?
                        return new PathGrammarProduction(lProd.leftVariable, lrt, (String)rProd.rightItem2);
                    case PropFormula_Variable:
                        ltf = ((LDL) lProd.rightItem1).children.get(0);
                        if(ltf.isPropFormula()){
                            lrt=new LDL(LDL.Operators.AND, ltf, (LDL)rProd.rightItem1); // lrt = ltf & rt
                            return new PathGrammarProduction(lProd.leftVariable, lrt, (String)rProd.rightItem2);
                        }else return null;
                    default:
                        break;
                }
                break;
            case PropFormula_Variable:
                switch (rProd.type) {
                    case Empty:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)lProd.rightItem1);
                    case Test:
                    case PropFormula:
                        return null;
                    case Variable:
                        return new PathGrammarProduction(lProd.leftVariable, (LDL)lProd.rightItem1, (String)rProd.rightItem1);
                    case Test_Variable:
                    case PropFormula_Variable:
                        return null;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return null;
    }

    // 后向约减产生式：
    // （1）对任一左变量出度为1的右产生式w->t2w2（终结符t2和变量w2都可能为空），如果所有使用w的左产生式v->t1w（终结符t1可以为空）都可以和右产生式联合，则将所有左产生式修改为v->t3w2，其中t3与t1t2语义等价，并删除右产生式。
    // （2）如果发生产生式约减则返回true，否则返回false
    public boolean BackwardReduceProductions() {
        List<PathGrammarProduction> lvod1Prods = new ArrayList<>(); // 左变量出度为1的产生式
        boolean pChanged=false; // pChanged=true表示有产生式发生改变
        boolean oneRoundChanged=false; // oneRoundChanged=true表示在一轮大循环中有产生式发生改变
        do {
            oneRoundChanged=false;
            //(1) 将所有出度为1的产生式存入lvod1Prods，起始产生式除外
            lvod1Prods.clear();
            Set<String> vars = getVariables();
            for(String v : vars) {
                if(v.equals(start)) continue;
                List<PathGrammarProduction> tps = getProds(v);
                if (tps.size()==1)
                    lvod1Prods.add((PathGrammarProduction) tps.toArray()[0]);
            }

            //(2) 对任一左变量出度为1的右产生式w->t2w2（终结符t2和变量w2都可能为空），如果所有使用w的左产生式v->t1w（终结符t1可以为空）都可以和右产生式联合，则将所有左产生式修改为v->t3w2，其中t3与t1t2语义等价，并删除右产生式。
            Iterator<PathGrammarProduction> itProds;
            ListIterator<PathGrammarProduction> itLvod1Prods = lvod1Prods.listIterator();
            while (itLvod1Prods.hasNext()) {
                PathGrammarProduction rp = itLvod1Prods.next(); // rp是将被联合的右产生式
                String w = rp.leftVariable; // rp的左变量

                // 获得所有使用w的左产生式并放到lProds
                List<PathGrammarProduction> lProds = new ArrayList<>();
                itProds = this.productions.iterator();
                while (itProds.hasNext()) {
                    PathGrammarProduction lp = itProds.next(); // lp is the left production to be merged
                    String lp_rv = lp.getRightVariable();
                    if(lp!=rp && lp_rv!=null && lp_rv.equals(w)) lProds.add(lp);
                }
                if(lProds.size()<=0) continue;

                // 将lProds与rp联合得到产生式集合unitedProds
                List<PathGrammarProduction> unitedProds = new ArrayList<>();
                for(PathGrammarProduction lp : lProds){
                    PathGrammarProduction newP = unite2productions(lp, rp);
                    if(newP!=null) unitedProds.add(newP);
                }

                if(unitedProds.size()==lProds.size()){ // 所有使用w的产生式都可以和rp联合
                    this.productions.removeAll(lProds);
                    this.productions.addAll(unitedProds);
                    this.productions.remove(rp);
                    oneRoundChanged=true;
                    pChanged=true;
                }

            }
        } while (oneRoundChanged);

        return pChanged;
    }

    // 前向约减产生式：
    // （1）对任一右变量入度为1的左产生式v->t1w（终结符t1可能为空，w不空），如果所有定义w的右产生式w->t2w2（终结符t2和变量w2都可为空）都可以和左产生式联合，则将所有右产生式修改为v->t3w2，其中t3与t1t2语义等价，并删除左产生式。
    // （2）如果发生产生式约减则返回true，否则返回false
    public boolean ForwardReduceProductions() {
        List<PathGrammarProduction> rvod1Prods = new ArrayList<>(); // 右变量入度为1的产生式
        boolean pChanged=false; // pChanged=true表示有产生式发生改变
        boolean oneRoundChanged=false; // oneRoundChanged=true表示在一轮大循环中有产生式发生改变
        do {
            oneRoundChanged=false;
            //(1) 将所有入度为1的产生式存入rvod1Prods
            rvod1Prods.clear();
            Set<String> vars = getVariables();
            for(String v : vars) {
                List<PathGrammarProduction> tps = getProdsUsingVar(v);
                if (tps.size()==1)
                    rvod1Prods.add((PathGrammarProduction) tps.toArray()[0]);
            }

            //(2) 对任一右变量入度为1的左产生式v->t1w（终结符t1可能为空，w不空），如果所有定义w的右产生式w->t2w2（终结符t2和变量w2都可为空）都可以和左产生式联合，则将所有右产生式修改为v->t3w2，其中t3与t1t2语义等价，并删除左产生式。
            Iterator<PathGrammarProduction> itProds;
            ListIterator<PathGrammarProduction> itRvod1Prods = rvod1Prods.listIterator();
            while (itRvod1Prods.hasNext()) {
                PathGrammarProduction lp = itRvod1Prods.next(); // lp是将被联合的左产生式
                String w = lp.getRightVariable(); //lp的右变量

                // 获得所有定义w的右产生式并放到rProds
                List<PathGrammarProduction> rProds = new ArrayList<>();
                itProds = this.productions.iterator();
                while (itProds.hasNext()) {
                    PathGrammarProduction rp = itProds.next(); // rp is the right production to be merged
                    if(rp!=lp && rp.leftVariable.equals(w)) rProds.add(rp);
                }
                if(rProds.size()<=0) continue;

                // 将lp与rProds与联合得到产生式集合unitedProds
                List<PathGrammarProduction> unitedProds = new ArrayList<>();
                for(PathGrammarProduction rp : rProds){
                    PathGrammarProduction newP = unite2productions(lp, rp);
                    if(newP!=null) unitedProds.add(newP);
                }

                if(unitedProds.size()==rProds.size()){ // 所有使用w的产生式都可以和rp联合
                    this.productions.removeAll(rProds);
                    this.productions.addAll(unitedProds);
                    this.productions.remove(lp);
                    oneRoundChanged=true;
                    pChanged=true;
                }

            }
        } while (oneRoundChanged);

        return pChanged;
    }

    //如果从fromVar开始的产生式有向图使用了var，则返回true，否则返回false
    public boolean varUsed(String fromVar, String var) {
        //广度优先搜索
        Set<String> usedVars = new HashSet<>();
        Set<String> newVars=new HashSet<>();
        List<PathGrammarProduction> prods=new LinkedList<>();
        boolean firstTime=true;
        while(true) {
            if(firstTime){
                prods = getProds(fromVar);
                firstTime=false;
            }

            newVars.clear();
            for (PathGrammarProduction p : prods) {
                switch (p.type) {
                    case Variable:
                        if(p.rightItem1.equals(var))
                            return true;
                        else if (!usedVars.contains(p.rightItem1)) {
                            usedVars.add((String) p.rightItem1);
                            newVars.add((String) p.rightItem1);
                        }
                        break;
                    case Test_Variable:
                    case PropFormula_Variable:
                        if(p.rightItem2.equals(var))
                            return true;
                        else if (!usedVars.contains(p.rightItem2)) {
                            usedVars.add((String) p.rightItem2);
                            newVars.add((String) p.rightItem2);
                        }
                        break;
                }
            }
            if(newVars.isEmpty()) return false;
            // newVars is NOT empty
            prods.clear();
            for(String v : newVars){
                prods.addAll(getProds(v));
            }
        }
    }

    //返回从fromVar开始的产生式有向图所经历的所有变量，不包括起点fromVar，向前最多走到toVar
    public void getUsedProdsAndVars(String fromVar, String toVar, Set<PathGrammarProduction> outUsedProds, Set<String> outUsedVars) {
        //广度优先搜索
        //outUsedVars = new HashSet<>();
        Set<String> newVars=new HashSet<>();
        //outUsedProds=new HashSet<>();
        List<PathGrammarProduction> curProds=new LinkedList<>();
        boolean firstTime=true;
        while(true) {
            if(firstTime){
                curProds = getProds(fromVar);
                outUsedProds.addAll(curProds);
                firstTime=false;
            }

            newVars.clear();
            for (PathGrammarProduction p : curProds) {
                switch (p.type) {
                    case Variable:
                        if (!outUsedVars.contains(p.rightItem1)) {
                            outUsedVars.add((String) p.rightItem1);
                            newVars.add((String) p.rightItem1);
                        }
                        break;
                    case Test_Variable:
                    case PropFormula_Variable:
                        if (!outUsedVars.contains(p.rightItem2)) {
                            outUsedVars.add((String) p.rightItem2);
                            newVars.add((String) p.rightItem2);
                        }
                        break;
                }
            }
            if(newVars.isEmpty()) return;
            // newVars is NOT empty
            curProds.clear();
            for(String v : newVars){
                if(!v.equals(fromVar) && !v.equals(toVar)) {
                    List<PathGrammarProduction> tps = getProds(v);
                    curProds.addAll(tps);
                    outUsedProds.addAll(tps);
                }
            }
        }
    }

    public void optimization() {
        boolean res1=true,res2=true;
        while(res1 || res2) {
            res1 = mergeProductions();
            res2 = reduceProductions();
        }

    }

    public void new_optimization() {
        boolean res1=true,res2=true,res3=true,res4=true;
        while(res1 || res2 || res3 || res4) {
            res1 = mergeProductions();
            res2 = BackwardReduceProductions();
            res3 = mergeProductions();
            res4 = ForwardReduceProductions();
        }
    }

    public Set<PathGrammarProduction> prodsUsingVar(Set<PathGrammarProduction> prods, String var) {
        Set<PathGrammarProduction> tps = new HashSet<>();
        for(PathGrammarProduction p : prods){
            switch (p.type){
                case Variable:
                    if(p.rightItem1.equals(var)) tps.add(p);
                    break;
                case Test_Variable:
                case PropFormula_Variable:
                    if(p.rightItem2.equals(var)) tps.add(p);
                    break;
                default:
                    break;
            }
        }
        return tps;
    }



}
