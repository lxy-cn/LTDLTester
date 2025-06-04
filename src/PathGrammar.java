
import java.util.*;

// Path Grammar Class for a path expression
public class PathGrammar {
    LDL pathExpr;
//    Set<String> variables;
//    Set<PathGrammarTerminal> terminals;
    Set<PathGrammarProduction> productions;
    String start; // the start symbol in the set variables

    String variableNamePrefix = "";
    static int builtVariableCount = 0;

    PathGrammar(LDL pathExpr) throws CloneNotSupportedException {
        if (!pathExpr.isPathExpression()) {
            System.out.println("Error: the following formula is not path expression: " + pathExpr.getText());
            return;
        }
        this.pathExpr = pathExpr;
//        this.variables = new HashSet<>();
//        this.terminals = new HashSet<>();
        this.productions = new HashSet<>();
        boolean result = buildPathGrammarRecur(pathExpr);

//        print();

        optimization();
//        System.out.print("After optimizing, ");
//        print();

    }

    String addNewVariable() {
        String newVar = variableNamePrefix + (++builtVariableCount);
//        variables.add(newVar);
        return newVar;
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
        for (PathGrammarProduction prod1 : pg1.productions) {
            switch (prod1.type) {
                case Empty: // v->empty in P1
                    this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg2.start)); //v->S2
                    break;
                case Test: // v->LDL? in P1
                case PropFormula: // v->propLDL in P1
                    this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg2.start)); // v->LDL?.S2 | v->propLDL.S2
                    break;
                case Variable: // v->w in P1
                    this.productions.add(prod1); // v->w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem1); // eProd1 = w->empty
                    if(pg1.productions.contains(eProd1))
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg2.start)); // v->S2
                    break;
                case Test_Variable: // v->LDL?.w in P1
                case PropFormula_Variable: // v->propLDL.w in P1
                    this.productions.add(prod1); // v->LDL?.w | v->propLDL.w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem2); // w->empty
                    if(pg1.productions.contains(eProd1))
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg2.start)); // v->LDL?.S2 | v->propLDL.S2
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

        this.productions.add(new PathGrammarProduction(newVar, pg[0].start)); // v->S1
        this.productions.add(new PathGrammarProduction(newVar, pg[1].start)); // v->S2
        this.productions.addAll(pg[0].productions);
        this.productions.addAll(pg[1].productions);

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
                        this.productions.add(prod1); //v->empty
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg1.start)); // v->S1
                    } else // v==S1
                        this.productions.add(new PathGrammarProduction(pg1.start)); // S1->empty
                    break;
                case Test: // v->LDL?
                case PropFormula: // v->propLDL
                    this.productions.add(prod1); //v->LDL? | v->propLDL
                    this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg1.start)); //v->LDL?.S1 | v->propLDL.S1
                    break;
                case Variable: // v->w
                    this.productions.add(prod1); // v->w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem1); // w->empty
                    if(pg1.productions.contains(eProd1)) { // w->empty in P1
                        if (!prod1.leftVariable.equals(pg1.start)) { // v<>S1
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable)); // v->empty
                            this.productions.add(new PathGrammarProduction(prod1.leftVariable, pg1.start)); // v->S1
                        } else { // v==S1
                            this.productions.add(new PathGrammarProduction(pg1.start)); // S1->empty
                        }
                    }
                    break;
                case Test_Variable: // v->LDL?.w
                case PropFormula_Variable: // v->propLDL.w
                    this.productions.add(prod1); //  v->LDL?.w | v->propLDL.w
                    eProd1 = new PathGrammarProduction((String) prod1.rightItem2); // w->empty
                    if(pg1.productions.contains(eProd1)) { // w->empty in P1
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1)); // v->LDL? | v->propLDL
                        this.productions.add(new PathGrammarProduction(prod1.leftVariable, (LDL) prod1.rightItem1, pg1.start)); // v->LDL?.S1 | v->propLDL.S1
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

    // get productions starting from variable leftVar
    public Set<PathGrammarProduction> getProds(String leftVar) {
        Set<PathGrammarProduction> prods = new HashSet<>();
        for (PathGrammarProduction p : this.productions) {
            if (p.leftVariable.equals(leftVar)) prods.add(p);
        }
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
            Set<PathGrammarProduction> varProds = this.getProds(var);
            for (PathGrammarProduction prod : varProds) {
                s+="    "+(++j)+". "+prod.getText()+"\r\n";
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
            Set<PathGrammarProduction> varProds = this.getProds(var);
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
        s+="The Path Grammar of "+this.pathExpr.getText()+":\r\n";
        s+="  Start variable: "+this.start+"\r\n";
        String vars = "";
        for(String var : this.getVariables())
            vars += var + " ";
        s+="  Variables: "+vars+"\r\n";
        s+="  Productions:\r\n";
        //printProductions();
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

    public boolean mergeProds() {
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
                                tf = new LDL(LDL.Operators.TEST, tf);
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
                                tf = new LDL(LDL.Operators.TEST, tf);
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

    public boolean reduceProds() {
        Set<PathGrammarProduction> prods;
        PathGrammarProduction newProd;
        boolean changed=false;
        Set<PathGrammarProduction> toRemove=new HashSet<>();
        Set<PathGrammarProduction> toAdd=new HashSet<>();
        while(true) {
            for(PathGrammarProduction t : this.productions) {
                if (!t.leftVariable.equals(this.start) &&
                        (t.type == PathGrammarProduction.Type.Empty ||
                        t.type == PathGrammarProduction.Type.Test ||
                                t.type == PathGrammarProduction.Type.PropFormula)) {
                    if(getProds(t.leftVariable).size()==1){
                        // t = w->empty | w->LDL? | w->prop, and t is the unique production of w
                        // if t: w->prop and exists v->aw in P, then DON'T remove t, else remove t
                        if (t.type != PathGrammarProduction.Type.PropFormula) toRemove.add(t);
                        boolean has=false;
                        for(PathGrammarProduction tp : this.productions){
                            if(tp.type == PathGrammarProduction.Type.PropFormula_Variable &&
                                    tp.rightItem2.equals(t.leftVariable)){
                                has=true;
                                break;
                            }
                        }
                        if(!has) toRemove.add(t);

                        for(PathGrammarProduction p : this.productions){
                            switch (p.type){
                                case Variable:
                                    if(p.rightItem1.equals(t.leftVariable)){
                                        // p:v->w
                                        toRemove.add(p);
                                        if(t.type == PathGrammarProduction.Type.Empty)
                                            // rewrite each p:v->w into v->empty
                                            newProd = new PathGrammarProduction(p.leftVariable);
                                        else if (t.type == PathGrammarProduction.Type.Test)
                                            // rewrite each p:v->w into v->LDL?
                                            newProd = new PathGrammarProduction(p.leftVariable, (LDL)t.rightItem1);
                                        else // t.type==Prop
                                            // rewrite each p:v->w into v->prop
                                            newProd = new PathGrammarProduction(p.leftVariable, (LDL)t.rightItem1);
                                        toAdd.add(newProd);
                                    }
                                    break;
                                case Test_Variable:
                                    if(p.rightItem1.equals(t.leftVariable)){
                                        // p:v->LDL2?.w
                                        toRemove.add(p);
                                        if(t.type == PathGrammarProduction.Type.Empty)
                                            // rewrite each p:v->LDL2?.w into v->LDL2?
                                            newProd = new PathGrammarProduction(p.leftVariable);
                                        else if (t.type == PathGrammarProduction.Type.Test){// t.type==Test
                                            // rewrite each p:v->LDL2?.w into v->(LDL2&LDL)?
                                            LDL newTest=new LDL(LDL.Operators.TEST,
                                                    ((LDL)t.rightItem1).children.get(0),
                                                    ((LDL)p.rightItem1).children.get(0));
                                            newProd = new PathGrammarProduction(p.leftVariable, newTest, (String) p.rightItem2);
                                        }else{ // t.type==prop
                                            // rewrite each p:v->LDL2?.w into v->(LDL2&prop)?
                                            LDL newTest=new LDL(LDL.Operators.TEST,
                                                    (LDL)t.rightItem1,
                                                    ((LDL)p.rightItem1).children.get(0));
                                            newProd = new PathGrammarProduction(p.leftVariable, newTest, (String) p.rightItem2);
                                        }
                                        toAdd.add(newProd);
                                    }
                                    break;
                                case PropFormula_Variable:
                                    if(p.rightItem1.equals(t.leftVariable)){
                                        // p:v->LDL2.w
                                        // if t:w->empty, then rewrite each p:v->LDL2.w into v->LDL2
                                        if(t.type == PathGrammarProduction.Type.Empty) {
                                            toRemove.add(p);
                                            newProd = new PathGrammarProduction(p.leftVariable, (LDL) p.rightItem1);
                                            toAdd.add(newProd);
                                        }
                                    }
                                    break;

                            }
                        }
                    }
                }
            }
            if(toRemove.size()>0 || toAdd.size()>0) {
                changed=true;
                this.productions.removeAll(toRemove);
                toRemove.clear();
                this.productions.addAll(toAdd);
                toAdd.clear();
            }else
                return changed;
        }
    }

    public boolean reduceProds2() {
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
                Set<PathGrammarProduction> tps = getProds(v);
                if (tps.size()==1)
                    od1Prods.add((PathGrammarProduction) tps.toArray()[0]);
            }

            //削减产生式，可能生成新的出度为1的产生式
            PathGrammarProduction p,t;
            boolean predByPropVarProds;
            Iterator<PathGrammarProduction> itProds;
            ListIterator<PathGrammarProduction> itOd1Prods = od1Prods.listIterator();
            oneRoundChanged=false;
            while (itOd1Prods.hasNext()) {
                t = itOd1Prods.next();
                switch (t.type) {
                    case Empty: // t:w->empty
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p);
                                    newP = new PathGrammarProduction(p.leftVariable); // v->empty
                                    toAdd.add(newP);
                                    break;
                                case Test_Variable: // p:v->ldl?.w
                                case PropFormula_Variable: // p:v->ldl.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) p.rightItem1); // v->ldl? | v->ldl
                                    toAdd.add(newP);
                                    break;
                                default:
                                    break;
                            }
                        }
                        toRemove.add(t); // remove t
                        oneRoundChanged=true;
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
                        break;
                    case Test: // t:w->ldl?
                        predByPropVarProds=false;
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1); // v->ldl?
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case Test_Variable: // p:v->ldl2?w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                            ((LDL)p.rightItem1).children.get(0),
                                            ((LDL)t.rightItem1).children.get(0)));
                                    newP = new PathGrammarProduction(p.leftVariable, tf); // v->(ldl2 & ldl)?
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case PropFormula_Variable: // p:v->prop.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    predByPropVarProds=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!predByPropVarProds) {
                            toRemove.add(t); // remove t
                            oneRoundChanged=true;
                        }
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
                        break;
                    case PropFormula: // t:w->prop
                        predByPropVarProds=false;
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1); // v->prop
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case Test_Variable: // p:v->ldl?w
                                    /*if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                                    ((LDL)p.rightItem1).children.get(0), // ldl
                                                    ((LDL)t.rightItem1))); // prop
                                    newP = new PathGrammarProduction(p.leftVariable, tf); // v->(ldl & prop)?
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;*/
                                case PropFormula_Variable: // p:v->prop.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    predByPropVarProds=true; //实际在Test_Variable或PropFormula_Variable两种情况下都为真
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!predByPropVarProds) {
                            toRemove.add(t); // remove t
                            oneRoundChanged=true;
                        }
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
                        break;
                    case Variable: // t:w->w'
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable) || p==t) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (String) t.rightItem1); // v->w'
                                    toAdd.add(newP);
                                    break;
                                case Test_Variable: // p:v->ldl?.w
                                case PropFormula_Variable: // p:v->ldl.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) p.rightItem1, (String) t.rightItem1); // v->ldl?.w' | v->ldl.w'
                                    toAdd.add(newP);
                                    break;
                                default:
                                    break;
                            }
                        }
                        toRemove.add(t); // remove t
                        oneRoundChanged=true;
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
                        break;
                    case Test_Variable: // t:w->ldl?.w'
                        predByPropVarProds=false;
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1, (String) t.rightItem2); // v->ldl?.w'
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case Test_Variable: // p:v->ldl2?.w
                                    if(!p.rightItem2.equals(t.leftVariable) || p==t) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                                    ((LDL)p.rightItem1).children.get(0),
                                                    ((LDL)t.rightItem1).children.get(0))); // tf=(ldl2 & ldl)?
                                    newP = new PathGrammarProduction(p.leftVariable, tf, (String) t.rightItem2); // v->(ldl2 & ldl)?.w'
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case PropFormula_Variable: // p:v->ldl.w
                                    if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    predByPropVarProds=true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!predByPropVarProds) {
                            toRemove.add(t); // remove t
                            oneRoundChanged=true;
                        }
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
                        break;
                    case PropFormula_Variable: // t:w->prop.w'
                        predByPropVarProds=false;
                        itProds = this.productions.iterator();
                        while (itProds.hasNext()) {
                            p = itProds.next();
                            switch (p.type) {
                                case Variable: // p:v->w
                                    if(!p.rightItem1.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    newP = new PathGrammarProduction(p.leftVariable, (LDL) t.rightItem1, (String) t.rightItem2); // v->prop.w'
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;
                                case Test_Variable: // p:v->ldl?.w
                                    /*if(!p.rightItem2.equals(t.leftVariable)) continue;
                                    toRemove.add(p); // remove p
                                    LDL tf = new LDL(LDL.Operators.TEST,
                                            new LDL(LDL.Operators.AND,
                                                    ((LDL)p.rightItem1).children.get(0),
                                                    (LDL)t.rightItem1)); // tf=(ldl & prop)?
                                    newP = new PathGrammarProduction(p.leftVariable, tf, (String) t.rightItem2); // v->(ldl & prop)?.w'
                                    toAdd.add(newP);
                                    oneRoundChanged=true;
                                    break;*/
                                case PropFormula_Variable: // p:v->ldl.w
                                    if(!p.rightItem2.equals(t.leftVariable) || p==t) continue;
                                    predByPropVarProds=true; //在Test_Variable或PropFormula_Variable两种情况下都为真
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(!predByPropVarProds) {
                            toRemove.add(t); // remove t
                            oneRoundChanged=true;
                        }
                        this.productions.removeAll(toRemove);
                        this.productions.addAll(toAdd);
                        toRemove.clear();
                        toAdd.clear();
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

                                System.out.println(wp.getText() + " is modified as " + newP.getText() );

                            }

                            //(2)删除p:v->w
                            toRemove.add(p);
                            reduced=true;
                            changed=true;

                            System.out.println("The production removed: " + p.getText());
                            System.out.println("The variable removed: " + w);

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

    //如果从fromVar开始的产生式有向图使用了var，则返回true，否则返回false
    public boolean varUsed(String fromVar, String var) {
        //广度优先搜索
        Set<String> usedVars = new HashSet<>();
        Set<String> newVars=new HashSet<>();
        Set<PathGrammarProduction> prods=new HashSet<>();
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
        Set<PathGrammarProduction> curProds=new HashSet<>();
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
                    Set<PathGrammarProduction> tps = getProds(v);
                    curProds.addAll(tps);
                    outUsedProds.addAll(tps);
                }
            }
        }
    }

    public void optimization() {
        boolean res1=true,res2=true;
        while(res1 || res2) {
            res1 = mergeProds();
            /*if(res1) {
                System.out.println("Merged productions:");
                printProductions();
            }else{
                System.out.println("No any production is merged.");
            }*/

            res2 = reduceProds2();
            /*if(res2) {
                System.out.println("Reduced productions:");
                printProductions();
            }else{
                System.out.println("No any production is reduced.");
            }*/
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
