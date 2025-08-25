import java.util.Objects;

public class PathGrammarProduction {
    public enum Type implements Comparable<Type> {
        Illegal(0), // Illegal type
        Empty(1), // v->empty
        Test(2), // v->LDL?
        PropFormula(3), // v->propLDL
        Variable(4), // v->w
        Test_Variable(5), // v->LDL?.w
        PropFormula_Variable(6), // v->propLDL.w
        Test_PropFormula(7), // v->LDL?.propLDL
        PropFormula_Test(8); // v->propLDL.LDL?

        private final int value;
        Type(int val) {
            this.value = val;
        }

        public int getValue() {
            return this.value;
        }

    }

    Type type;
    String leftVariable;
    Object rightItem1;
    Object rightItem2;

    // v->empty
    PathGrammarProduction(String leftVar) {
        type = Type.Empty;
        leftVariable = leftVar;
        rightItem1 = null;
        rightItem2 = null;
    }


    // v->LDL?  |  v->propLDL
    PathGrammarProduction(String leftVar, LDL f) {
        if(f.operator == LDL.Operators.TEST)
            type = Type.Test;
        else
            type = Type.PropFormula;
        leftVariable = leftVar;
        rightItem1 = f;
        rightItem2 = null;
    }

    // v->w
    PathGrammarProduction(String leftVar, String rightVar) {
        type = Type.Variable;
        leftVariable = leftVar;
        rightItem1 = rightVar;
        rightItem2 = null;
    }

    // v->LDL?.w  |  v->propLDL.w
    PathGrammarProduction(String leftVar, LDL f, String rightVar) {
        if(f.operator == LDL.Operators.TEST )
            type = Type.Test_Variable;
        else
            type = Type.PropFormula_Variable;
        leftVariable = leftVar;
        rightItem1 = f;
        rightItem2 = rightVar;
    }

    // v->LDL?.propLDL  |  v->propLDL.LDL?
    PathGrammarProduction(String leftVar, LDL f1, LDL f2) {
        if(f1.operator == LDL.Operators.TEST && f2.isPropFormula())
            type = Type.Test_PropFormula;
        else if(f1.isPropFormula() && f2.operator == LDL.Operators.TEST)
            type = Type.PropFormula_Test;
        else {
            type = Type.Illegal;
            leftVariable = null;
            rightItem1 = null;
            rightItem2 = null;
            return;
        }

        leftVariable = leftVar;
        rightItem1 = f1;
        rightItem2 = f2;
    }

    public String getText() {
        String s = this.leftVariable + " --> ";
        switch (this.type) {
            case Empty:
                s += "ε";
                break;
            case Test:
            case PropFormula:
                s += LDL.getTextWithBracketIfNeed((LDL)this.rightItem1, true);
                break;
            case Variable:
                s += this.rightItem1;
                break;
            case Test_Variable:
            case PropFormula_Variable:
                s += LDL.getTextWithBracketIfNeed(((LDL)this.rightItem1), true) + " " + this.rightItem2;
                break;
            case Test_PropFormula:
            case PropFormula_Test:
                s += "(" + ((LDL)this.rightItem1).getText(true) + ").(" + ((LDL)this.rightItem2).getText(true) + ")";
                break;
        }
        return s;
    }

    public String getRightVariable() {
        switch (this.type) {
            case Empty:
            case Test:
            case PropFormula:
            case Test_PropFormula:
            case PropFormula_Test:
                return null;
            case Variable:
                return (String) this.rightItem1;
            case Test_Variable:
            case PropFormula_Variable:
                return (String) this.rightItem2;
            default:
                return null;
        }
    }

    //对于以PathGrammarProduction类为元素的Set类，定义如何判断其中两个元素是否相同的机制
    @Override
    public int hashCode() { //hash函数定义
        return Objects.hash(type, leftVariable, rightItem1, rightItem2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        PathGrammarProduction inProd = (PathGrammarProduction) obj;

        if (this.type != inProd.type) return false;
        if ((this.leftVariable!=null && inProd.leftVariable==null) || (this.leftVariable==null && inProd.leftVariable!=null)) return false;
        if (!this.leftVariable.equals(inProd.leftVariable)) return false;
        switch (this.type) {
            case Empty:
                break;
            case Test:
            case PropFormula:
                if(!this.rightItem1.equals(inProd.rightItem1)) return false;
                break;
            case Variable:
                if(!this.rightItem1.equals(inProd.rightItem1)) return false;
                break;
            case Test_Variable:
            case PropFormula_Variable:
                if(!this.rightItem1.equals(inProd.rightItem1)) return false;
                if(!this.rightItem2.equals(inProd.rightItem2)) return false;
                break;
            case Test_PropFormula:
            case PropFormula_Test:
                if(!this.rightItem1.equals(inProd.rightItem1)) return false;
                if(!this.rightItem2.equals(inProd.rightItem2)) return false;
                break;
            case Illegal:
                break;
            default: // this.type==Illegal
                return false;
        }
        return true;
    }
}
