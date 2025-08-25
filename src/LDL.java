import java.util.Objects;
import java.util.Vector;

// We are still using LDL instead of LTDL to simplify the source code.
public class LDL implements Cloneable {
    Operators operator=null;
    String data=""; // "data" is used to record the atom name only when operator==ATOM
    Vector<LDL> children=null;

    boolean prime=false; // only used for representing transitions

    @Override
    public LDL clone() throws CloneNotSupportedException {
        LDL copy = (LDL) super.clone();
        if(children!=null){
            copy.children = new Vector<>();
            for(int i=0; i<children.size(); i++) {
                LDL tf = children.get(i).clone();
                copy.children.add(tf);
            }
        }
        return copy;
    }

    //对于以PathGrammarProduction类为元素的Set类，定义如何判断其中两个元素是否相同的机制
    @Override
    public int hashCode() { //hash函数定义
        return Objects.hash(operator, data, children);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        LDL inLdl = (LDL) obj;

        if (this.operator != inLdl.operator) return false;
        switch (this.operator) {
            case ATOM:
                return this.data.equals(inLdl.data);
            case AND:
            case OR:
            case BIIMPLY:
            case UNION:
                boolean res1=this.children.get(0).equals(inLdl.children.get(0)) && this.children.get(1).equals(inLdl.children.get(1));
                boolean res2=this.children.get(0).equals(inLdl.children.get(1)) && this.children.get(1).equals(inLdl.children.get(0));
                return res1 || res2;
            default:
                for (int i=0; i<this.children.size(); i++) {
                    if (!this.children.get(i).equals(inLdl.children.get(i)))
                        return false;
                }
                return true;
        }
    }

    public enum Operators {
        /*propositional logic operators*/
        ATOM,
        NOT,
        AND,
        OR,
        IMPLY,
        BIIMPLY,
        /*future LTL operators*/
        NEXT,
        UNTIL,
        FINALLY,
        GLOBALLY,
        RELEASE,
        // past LTL operators
        PREVIOUS,
        SINCE,
        PAST,
        HISTORICALLY,
        TRIGGER,
        /*LDL operators*/
        DIAMOND,
        BOX,
        /*path expression operators*/
        TEST,
        CONCAT,
        UNION,
        REPETITION
    }

/*
    // return 1 if op1>op2
    // return -1 if op1<op2
    // return 0 if op1=op2
    // return -2 if the priority undefined
    public static int operatorPriority(Operators op1, Operators op2) {

        // path expression
        if(op1==Operators.TEST && op2==Operators.TEST) return -2;
        if(op1==Operators.TEST && op2==Operators.REPETITION) return 1;
        if(op1==Operators.TEST && op2==Operators.CONCAT) return 1;
        if(op1==Operators.TEST && op2==Operators.UNION) return 1;

        if(op1==Operators.REPETITION && op2==Operators.TEST) return -2;
        if(op1==Operators.REPETITION && op2==Operators.REPETITION) return 1;
        if(op1==Operators.REPETITION && op2==Operators.CONCAT) return 1;
        if(op1==Operators.REPETITION && op2==Operators.UNION) return 1;

        if(op1==Operators.CONCAT && op2==Operators.TEST) return -1;
        if(op1==Operators.CONCAT && op2==Operators.REPETITION) return -1;
        if(op1==Operators.CONCAT && op2==Operators.CONCAT) return 1;
        if(op1==Operators.CONCAT && op2==Operators.UNION) return -1;

        if(op1==Operators.UNION && op2==Operators.TEST) return -1;
        if(op1==Operators.UNION && op2==Operators.REPETITION) return -1;
        if(op1==Operators.UNION && op2==Operators.CONCAT) return 1;
        if(op1==Operators.UNION && op2==Operators.UNION) return 1;

        //


        return 0;
    }
*/
    public boolean operatorIsBinary() {
        switch (this.operator){
            case ATOM:
                return this.data.contains("&")
                        || this.data.contains("|")
                        || this.data.contains("<->")
                        || this.data.contains("->")
//                        || this.data.contains("<")
//                        || this.data.contains(">")
//                        || this.data.contains("=")
                        ;
            case AND:
            case OR:
            case IMPLY:
            case BIIMPLY:

            case CONCAT:
            case UNION:

            case UNTIL:
            case RELEASE:

            case SINCE:
            case TRIGGER:

            case DIAMOND:
            case BOX:
                return true;
            default:
                return false;
        }
    }

    public boolean isPropFormula() {
        switch (operator) {
            case ATOM:
                return true;
            case NOT:
                return children.get(0).isPropFormula();
            case AND:
            case OR:
            case IMPLY:
            case BIIMPLY:
                return children.get(0).isPropFormula() && children.get(1).isPropFormula();
            default:
                return false;
        }
    }

    public boolean isPathExpression( boolean allow_LTL_in_Test) {
        if(this.isPropFormula()) return true;
        switch (operator) {
            case TEST:
                if(allow_LTL_in_Test)
                    return children.get(0).isLDL_LTL();
                else
                    return children.get(0).isLDL();
            case REPETITION:
                return children.get(0).isPathExpression(allow_LTL_in_Test);
            case CONCAT:
            case UNION:
                return children.get(0).isPathExpression(allow_LTL_in_Test)
                        && children.get(1).isPathExpression(allow_LTL_in_Test);
            default:
                return false;
        }
    }

    public boolean isTestOnly() {
        switch (operator) {
            case TEST:
                return true;
            case REPETITION:
                return children.get(0).isTestOnly();
            case CONCAT:
            case UNION:
                return children.get(0).isTestOnly() && children.get(1).isTestOnly();
            default:
                return false;
        }
    }

    public boolean isLDL() {
        switch (operator) {
            case ATOM:
                return true;
            case NOT:
                return children.get(0).isLDL();
            case AND:
            case OR:
            case IMPLY:
            case BIIMPLY:
                return children.get(0).isLDL() && children.get(1).isLDL();
            case DIAMOND:
            case BOX:
                return children.get(0).isPathExpression(false) && children.get(1).isLDL();
            default:
                return false;
        }
    }

    public boolean isLTL() {
        switch (operator) {
            case ATOM:
                return true;
            case NOT:

            case NEXT:
            case FINALLY:
            case GLOBALLY:

            case PREVIOUS:
            case PAST:
            case HISTORICALLY:
                return children.get(0).isLTL();

            case AND:
            case OR:
            case IMPLY:
            case BIIMPLY:

            case UNTIL:
            case RELEASE:

            case SINCE:
            case TRIGGER:
                return children.get(0).isLTL() && children.get(1).isLTL();
            default:
                return false;
        }
    }

    public boolean isLDL_LTL() {
        switch (operator) {
            case ATOM:
                return true;
            case NOT:

            case NEXT:
            case FINALLY:
            case GLOBALLY:

            case PREVIOUS:
            case PAST:
            case HISTORICALLY:
                return children.get(0).isLDL_LTL();


            case AND:
            case OR:
            case IMPLY:
            case BIIMPLY:

            case UNTIL:
            case RELEASE:

            case SINCE:
            case TRIGGER:
                return children.get(0).isLDL_LTL() && children.get(1).isLDL_LTL();
            case DIAMOND:
            case BOX:
                return children.get(0).isPathExpression(true) && children.get(1).isLDL_LTL();
            default:
                return false;
        }
    }

    // 如果公式f的主算子是二目运算符&、|、->、<->、';'、+，则用圆括号将f的文本括起来，否则没有圆括号
    public static String getTextWithBracketIfNeed(LDL f, boolean sortChildren) {
        String ftext = f.getText(sortChildren).trim();
        if(f.operatorIsBinary()) // && !ftext.matches("\\(.*\\)"))
            return "(" + ftext + ")";
        else
            return ftext;
    }

    public LDL(boolean prime, String atomicData) {
        this.prime=prime;
        this.operator= Operators.ATOM;
        this.data=atomicData;
        this.children=new Vector<>();
    }
    public LDL(String atomicData) { this(false, atomicData); }

    public LDL(boolean prime, Operators op, LDL child0) {
        this.prime=prime;
        this.operator=op;
        this.data="";
        this.children=new Vector<>();
        this.children.add(child0);
    }
    public LDL(Operators op, LDL child0) { this(false, op, child0); }

    public LDL(boolean prime, Operators op, LDL child0, LDL child1) {
        this.prime=prime;
        this.operator=op;
        this.data="";
        this.children=new Vector<>();
        this.children.add(child0);
        this.children.add(child1);
    }
    public LDL(Operators op, LDL child0, LDL child1) { this(false, op, child0, child1); }

    public LDL(Operators op, LDL child0, LDL child1, LDL child2) {
        this.prime=false;
        this.operator=op;
        this.data="";
        this.children=new Vector<>();
        this.children.add(child0);
        this.children.add(child1);
        this.children.add(child2);
    }

    //new reference of an LDL with prime setup
    public LDL(boolean prime, LDL f) {
        this.prime = prime;
        this.operator = f.operator;
        this.children = f.children;
        this.data = f.data;
    }

    //new reference of an LDL
    public LDL(LDL f) {
        this(f.prime, f);
    }

    public String getText(boolean sortChildren) {
        String res = "";
        String t="",c0="",c1="";
        switch (operator) {
            case ATOM:
                res = data.replaceAll("^'|'$", ""); // 将两端的单引号去掉（如果有），^'表示首字符为'，'$表示尾字符为'
                break;
            case AND:
            case OR:
            case BIIMPLY:
            case UNION:
                switch (operator){
                    case AND: t=" & "; break;
                    case OR: t=" | "; break;
                    case BIIMPLY: t=" <-> "; break;
                    case UNION: t=" + "; break;
                }
                c0 = getTextWithBracketIfNeed(children.get(0), sortChildren);
                c1 = getTextWithBracketIfNeed(children.get(1), sortChildren);
                if(c0.compareTo(c1)>0 && sortChildren){
                    res=c1+t+c0;
                }else{ // c0<=c1
                    res=c0+t+c1;
                }
                break;
            case IMPLY:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " -> " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case NOT:
                res = "!" + getTextWithBracketIfNeed(children.get(0), sortChildren);
                break;
            case NEXT:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "X" + t;
                else
                    res = "X " + t;
                break;
            case PREVIOUS:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "Y" + t;
                else
                    res = "Y " + t;
                break;
            case FINALLY:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "F" + t;
                else
                    res = "F " + t;
                break;
            case PAST:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "P" + t;
                else
                    res = "P " + t;
                break;
            case GLOBALLY:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "G" + t;
                else
                    res = "G " + t;
                break;
            case HISTORICALLY:
                t = getTextWithBracketIfNeed(children.get(0), sortChildren);
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "H" + t;
                else
                    res = "H " + t;
                break;
            case UNTIL:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " U " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case SINCE:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " S " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case RELEASE:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " R " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case TRIGGER:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " T " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case DIAMOND:
                res = "<" + children.get(0).getText(sortChildren) + ">" + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case BOX:
                res = "[" + children.get(0).getText(sortChildren) + "]" + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case TEST:
                if (children.get(0).operator!=Operators.ATOM)
                    res = "("+children.get(0).getText(sortChildren)+")?";
                else
                    res = children.get(0).getText(sortChildren)+"?";
                break;
            case CONCAT:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + " ; " + getTextWithBracketIfNeed(children.get(1), sortChildren);
                break;
            case REPETITION:
                res = getTextWithBracketIfNeed(children.get(0), sortChildren) + "*";
                break;
            default:
                break;
        }

        if(this.prime) res = "next(" + res + ")";

        return res;
    }

/*
    public String getText() {
        String res = "";
        String t="";
        switch (operator) {
            case ATOM:
                res = data.replaceAll("^'|'$", ""); // 将两端的单引号去掉（如果有），^'表示首字符为'，'$表示尾字符为'
                break;
            case AND:
                res = getTextWithBracketIfNeed(children.get(0)) + " & " + getTextWithBracketIfNeed(children.get(1));
                break;
            case OR:
                res = getTextWithBracketIfNeed(children.get(0)) + " | " + getTextWithBracketIfNeed(children.get(1));
                break;
            case IMPLY:
                res = getTextWithBracketIfNeed(children.get(0)) + " -> " + getTextWithBracketIfNeed(children.get(1));
                break;
            case BIIMPLY:
                res = getTextWithBracketIfNeed(children.get(0)) + " <-> " + getTextWithBracketIfNeed(children.get(1));
                break;
            case NOT:
                res = "!" + getTextWithBracketIfNeed(children.get(0));
                break;
            case NEXT:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "X" + t;
                else
                    res = "X " + t;
                break;
            case PREVIOUS:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "Y" + t;
                else
                    res = "Y " + t;
                break;
            case FINALLY:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "F" + t;
                else
                    res = "F " + t;
                break;
            case PAST:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "P" + t;
                else
                    res = "P " + t;
                break;
            case GLOBALLY:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "G" + t;
                else
                    res = "G " + t;
                break;
            case HISTORICALLY:
                t = getTextWithBracketIfNeed(children.get(0));
                if(t.length()>0 && t.substring(0,1).equals("("))
                    res = "H" + t;
                else
                    res = "H " + t;
                break;
            case UNTIL:
                res = getTextWithBracketIfNeed(children.get(0)) + " U " + getTextWithBracketIfNeed(children.get(1));
                break;
            case SINCE:
                res = getTextWithBracketIfNeed(children.get(0)) + " S " + getTextWithBracketIfNeed(children.get(1));
                break;
            case RELEASE:
                res = getTextWithBracketIfNeed(children.get(0)) + " R " + getTextWithBracketIfNeed(children.get(1));
                break;
            case TRIGGER:
                res = getTextWithBracketIfNeed(children.get(0)) + " T " + getTextWithBracketIfNeed(children.get(1));
                break;
            case DIAMOND:
                res = "<" + children.get(0).getText() + ">" + getTextWithBracketIfNeed(children.get(1));
                break;
            case BOX:
                res = "[" + children.get(0).getText() + "]" + getTextWithBracketIfNeed(children.get(1));
                break;
            case TEST:
                if (children.get(0).operator!=Operators.ATOM)
                    res = "("+children.get(0).getText()+")?";
                else
                    res = children.get(0).getText()+"?";
                break;
            case UNION:
                res = getTextWithBracketIfNeed(children.get(0)) + " + " + getTextWithBracketIfNeed(children.get(1));
                break;
            case CONCAT:
                res = getTextWithBracketIfNeed(children.get(0)) + " ; " + getTextWithBracketIfNeed(children.get(1));
                break;
            case REPETITION:
                res = getTextWithBracketIfNeed(children.get(0)) + "*";
                break;
            default:
                break;
        }

        if(this.prime) res = "next(" + res + ")";

        return res;
    }
*/

    public LDL box2diamond() throws CloneNotSupportedException {
        if(this.operator== Operators.ATOM){
            return this.clone();
        }
        else if(this.operator== Operators.BOX){  // this=[pe]sf
            LDL pe = this.children.get(0); // path expression
            LDL not_sf = new LDL(Operators.NOT, this.children.get(1));  // !subformula
            LDL f_diamond = new LDL(Operators.DIAMOND, pe, not_sf);
            LDL not_f_diamond = new LDL(Operators.NOT, f_diamond); // !<pe>!sf
            return not_f_diamond;
        }
        else{
            int child_count = this.children.size();
            if(child_count<=0 || child_count>2){
                System.out.println("Error in box2diamond(): "+ child_count + " children in the LDL formula " + this.getText(true));
                return null;
            }
            if(child_count==1){
                LDL sf = this.children.get(0).box2diamond();
                return new LDL(this.operator, sf);
            }else{ // child_count==2
                LDL sf0 = this.children.get(0).box2diamond();
                LDL sf1 = this.children.get(1).box2diamond();
                return new LDL(this.operator, sf0, sf1);
            }
        }
    }

    // 就地删除冗余否定词
    public LDL reduceRedundantNotOperator() {
        int child_count=this.children.size();
        LDL sf0,sf1;
        if(this.operator== Operators.NOT){ //this=!sf
            sf0 = this.children.get(0);
            if(sf0.operator== Operators.NOT){ //this=!!sf
                return sf0.children.get(0).reduceRedundantNotOperator();
            }
        }
        //this=/=!sf
        switch (child_count){
            case 1:
                sf0 = this.children.get(0).reduceRedundantNotOperator();
                this.children.set(0, sf0);
                break;
            case 2:
                sf0 = this.children.get(0).reduceRedundantNotOperator();
                sf1 = this.children.get(1).reduceRedundantNotOperator();
                this.children.set(0, sf0);
                this.children.set(1, sf1);
                break;
            default: //child_count<=0 || child_count>2
                // System.out.println("Error in reduceRedundantNotOperator(): "+ child_count + " children in the LDL formula " + this.getText());
        }
        return this;
    }


}


