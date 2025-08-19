/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainLdl_ltlVisitor {
    static int OutputLevel = 0; // 输出显示级别

    public static String getTokenLiteralName(int tokenType) {
        return LDL_LTLParser.VOCABULARY.getLiteralName(tokenType).replace("'", "");
    }

    public  static String stringSingleQuotes2Parentheses(String s) {
        // In the string s, single quotes must occur in pairs
        return s.replaceAll("'(.*?)'", "($1)");
    }

    // 获得自定义的LDL语法树
    public static class Ldl_ltlGetTreeVisitor extends LDL_LTLBaseVisitor<LDL> {
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitLdl(LDL_LTLParser.LdlContext ctx) {
            return visit(ctx.ldlFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitUNARY_LTL_OPTR_LDL(LDL_LTLParser.UNARY_LTL_OPTR_LDLContext ctx) {
            LDL c0 = null;
            c0 = visit(ctx.ldlFormula());
            if(c0 == null) return null;
            if (ctx.op.getType() == LDL_LTLParser.NEXT)
                return new LDL(LDL.Operators.NEXT, c0);
            else if (ctx.op.getType() == LDL_LTLParser.PREV)
                return new LDL(LDL.Operators.PREV, c0);
            else if (ctx.op.getType() == LDL_LTLParser.GLOBALLY)
                return new LDL(LDL.Operators.GLOBALLY, c0);
            else if (ctx.op.getType() == LDL_LTLParser.FINALLY)
                return new LDL(LDL.Operators.FINALLY, c0);
            else
                return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitBINARY_BOOL_OPTR_LDL(LDL_LTLParser.BINARY_BOOL_OPTR_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.ldlFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LDL_LTLParser.AND)
                return new LDL(LDL.Operators.AND, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.OR)
                return new LDL(LDL.Operators.OR, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.IMPLY)
                return new LDL(LDL.Operators.IMPLY, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.BIIMPLY)
                return new LDL(LDL.Operators.BIIMPLY, c0, c1);
            else
                return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitDIAMOND_LDL(LDL_LTLParser.DIAMOND_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.pathExpr());
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula());
            if (c1 == null) return null;
            return new LDL(LDL.Operators.DIAMOND, c0, c1);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitATOM_LDL(LDL_LTLParser.ATOM_LDLContext ctx) {
            return visit(ctx.atomicFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitNOT_LDL(LDL_LTLParser.NOT_LDLContext ctx) {
            LDL c0=null;
            c0 = visit(ctx.ldlFormula());
            if (c0 == null) return null;
            return new LDL(LDL.Operators.NOT, c0);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitBINARY_LTL_OPTR_LDL(LDL_LTLParser.BINARY_LTL_OPTR_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.ldlFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LDL_LTLParser.UNTIL)
                return new LDL(LDL.Operators.UNTIL, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.RELEASE)
                return new LDL(LDL.Operators.RELEASE, c0, c1);
            else
                return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitPAREN_LDL(LDL_LTLParser.PAREN_LDLContext ctx) {
            return visit(ctx.ldlFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitBOX_LDL(LDL_LTLParser.BOX_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.pathExpr());
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula());
            if (c1 == null) return null;
            return new LDL(LDL.Operators.BOX, c0, c1);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitPROP_PATHEXPR(LDL_LTLParser.PROP_PATHEXPRContext ctx) {
            return visit(ctx.propFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitREPETITION_PATHEXPR(LDL_LTLParser.REPETITION_PATHEXPRContext ctx) {
            LDL c0=null;
            c0 = visit(ctx.pathExpr());
            if (c0 == null) return null;
            return new LDL(LDL.Operators.REPETITION, c0);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitTEST_PATHEXPR(LDL_LTLParser.TEST_PATHEXPRContext ctx) {
            LDL c0=null;
            c0 = visit(ctx.ldlFormula());
            if (c0 == null) return null;
            return new LDL(LDL.Operators.TEST, c0);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitPAREN_PATHEXPR(LDL_LTLParser.PAREN_PATHEXPRContext ctx) {
            return visit(ctx.pathExpr());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitTWO_OPERANDS_PATHEXPR(LDL_LTLParser.TWO_OPERANDS_PATHEXPRContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.pathExpr(0));
            if (c0 == null) return null;
            c1 = visit(ctx.pathExpr(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LDL_LTLParser.SEMI)
                return new LDL(LDL.Operators.CONCAT, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.PLUS)
                return new LDL(LDL.Operators.UNION, c0, c1);
            else
                return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitNOT_PROP(LDL_LTLParser.NOT_PROPContext ctx) {
            LDL c0=null;
            c0 = visit(ctx.propFormula());
            if (c0 == null) return null;
            return new LDL(LDL.Operators.NOT, c0);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitTWO_OPERANDS_PROP(LDL_LTLParser.TWO_OPERANDS_PROPContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.propFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.propFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LDL_LTLParser.AND)
                return new LDL(LDL.Operators.AND, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.OR)
                return new LDL(LDL.Operators.OR, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.IMPLY)
                return new LDL(LDL.Operators.IMPLY, c0, c1);
            else if (ctx.op.getType() == LDL_LTLParser.BIIMPLY)
                return new LDL(LDL.Operators.BIIMPLY, c0, c1);
            else
                return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitPAREN_PROP(LDL_LTLParser.PAREN_PROPContext ctx) {
            return visit(ctx.propFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitATOM_PROP(LDL_LTLParser.ATOM_PROPContext ctx) {
            return visit(ctx.atomicFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitID_ATOM(LDL_LTLParser.ID_ATOMContext ctx) {
            return new LDL(ctx.Identifier().getText());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitSTREXPR_ATOM(LDL_LTLParser.STREXPR_ATOMContext ctx) {
            return new LDL(ctx.StringExpr().getText());
        }
    }

/*
    public static class Ldl_ltlGetTreeVisitor extends LDL_LTLBaseVisitor<LDL> {
        public LDL visitLdl(LDL_LTLParser.LdlContext ctx) {
            return visit(ctx.ldlFormula());
        }

        public LDL visitLdlFormula(LDL_LTLParser.LdlFormulaContext ctx) {
            if ( ctx.getChildCount()==4 ) { // operations have 4 children
                if ( ctx. .op.getType()==LDL_LTLParser.ANGLE_OPEN ) {
                    LDL pe=visit(ctx.pathExpr()); // path expression
                    LDL sf=visit(ctx.ldlFormula(0)); // subformula
                    //return "<" + pe + ">" + sf;
                    return new LDL(LDL.Operators.DIAMOND, pe, sf);
                }
                else { // ctx.op.getType()==LDLParser.SQUARE_OPEN
                    LDL pe=visit(ctx.pathExpr()); // path expression
                    LDL sf=visit(ctx.ldlFormula(0)); // subformula
                    //return "[" + pe + "]" + sf;
                    return new LDL(LDL.Operators.BOX, pe, sf);
                }
            }
            else if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDL_LTLParser.PAREN_OPEN ) {
                    return visit(ctx.ldlFormula(0));
                }
                else if ( ctx.op.getType()==LDL_LTLParser.AND ) {
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.AND, l, r);
                }
                else if (ctx.op.getType()==LDL_LTLParser.OR) {
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.OR, l, r);
                }
                else if (ctx.op.getType()==LDL_LTLParser.BIIMPLY) {
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.BIIMPLY, l, r);
                }
                else { // ctx.op.getType()==LDLParser.IMPLY
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.IMPLY, l, r);
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children: NOT subformula
                LDL sf=visit(ctx.ldlFormula(0));
                return new LDL(LDL.Operators.NOT, sf);
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public LDL visitPathExpr(LDL_LTLParser.PathExprContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDL_LTLParser.PAREN_OPEN ) {
                    return visit(ctx.pathExpr(0));
                }
                else if ( ctx.op.getType()==LDL_LTLParser.SEMI ) {
                    LDL l=visit(ctx.pathExpr(0));
                    LDL r=visit(ctx.pathExpr(1));
                    return new LDL(LDL.Operators.CONCAT, l, r);
                }
                else { // ctx.op.getType()==LDLParser.PLUS
                    LDL l=visit(ctx.pathExpr(0));
                    LDL r=visit(ctx.pathExpr(1));
                    return new LDL(LDL.Operators.UNION, l, r);
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children
                if ( ctx.op.getType()==LDL_LTLParser.STAR ) {
                    LDL sf=visit(ctx.pathExpr(0));
                    return new LDL(LDL.Operators.REPETITION, sf);
                }
                else { //ctx.op.getType()==LDLParser.QUESTION
                    LDL sf=visit(ctx.ldlFormula());
                    return new LDL(LDL.Operators.TEST, sf);
                }
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public LDL visitPropFormula(LDL_LTLParser.PropFormulaContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDL_LTLParser.PAREN_OPEN ) {
                    return visit(ctx.propFormula(0));
                }
                else if ( ctx.op.getType()==LDL_LTLParser.AND ) {
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.AND, l, r);
                }
                else if ( ctx.op.getType()==LDL_LTLParser.OR ) {
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.OR, l, r);
                }
                else if (ctx.op.getType()==LDL_LTLParser.BIIMPLY) {
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.BIIMPLY, l, r);
                }
                else { // ctx.op.getType()==LDLParser.IMPLY
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.IMPLY, l, r);
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children: NOT subformula
                LDL sf=visit(ctx.propFormula(0));
                return new LDL(LDL.Operators.NOT, sf);
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public LDL visitAtomicFormula(LDL_LTLParser.AtomicFormulaContext ctx) {
            return new LDL(ctx.getText());
        }

        @Override
        public LDL visitTerminal(TerminalNode node) {
            return null;
        }
    }
*/

    public static void oneFormulaTesterConstruction(String fmla) throws CloneNotSupportedException {
        //System.out.println(fmla);
        LDL_LTLLexer lexer = new LDL_LTLLexer(CharStreams.fromString(fmla));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LDL_LTLParser parser = new LDL_LTLParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        ParseTree tree = parser.ldl(); // parse

        Ldl_ltlGetTreeVisitor ldlGetTreeVisitor = new Ldl_ltlGetTreeVisitor();
        LDL ldlTree = ldlGetTreeVisitor.visit(tree);
        System.out.println("--The original LDL formula: " + ldlTree.getText());
        ldlTree = ldlTree.box2diamond().reduceRedundantNotOperator();

        Tester tester = new Tester("", ldlTree);
        String smvOutput = tester.toSMV();
        System.out.println(smvOutput);
    }

    public static void showUsage(){
        System.out.println("Usage: 'java -jar LDLTester.jar -ldl formula' or 'java -jar LDLTester.jar -file filename'");
    }

    public static void showVersion(){
        System.out.println("*** LDLTester 1.1.0, a SMV temporal tester generator for LDL+LTL");
        System.out.println("*** Copyright (c) 2025, Xiangyu Luo at Huaqiao University");
        System.out.println("*** For more information please contact us via luoxy@hqu.edu.cn");
    }

    public static void main(String[] args) throws Exception {
        showVersion();
        if (args.length > 0) {
            if(args[0].equals("-ldl")){
                if(args.length<2){
                    System.out.println("Warning: an LDL formula must be inputted after '-ldl'.");
                    return;
                }
                oneFormulaTesterConstruction(args[1]);
                return;
            }else if(args[0].equals("-file")){
                if(args.length<2){
                    System.out.println("Warning: a file name must be inputted after '-file'.");
                    return;
                }
                String inputFile = args[1];
                try {
                    // 读取所有行到List中
                    byte[] bytes = Files.readAllBytes(Paths.get(inputFile));
                    String content = new String(bytes); // 默认UTF-8
                    content = content.replaceAll("/\\*[\\s\\S]*?\\*/", ""); // 删除所有/* */界定的子串
                    String[] lines = content.split("\\r?\\n|\\r"); // 兼容 \n, \r\n, \r
                    //System.out.println(content);

                    // 逐行处理
                    int i=1;
                    int l=0; //行号
                    String f="";
                    while(l<lines.length){
                        String line = lines[l++];
                        line=line.trim();
                        if(line.startsWith("--")) continue;
                        line = line.replaceAll("--.*", ""); //删除以"--"开始的后续子串
                        line = line.replaceAll("[\\t\\n\\r]", ""); // 删除所有 \t、\n、\r
                        line = line.trim();
                        if(line.equals("")) continue;
                        if(line.endsWith("#")) {
                            f += line.substring(0,line.length()-1).trim();
                            System.out.println("--------------------------------- No." +(i++)+ " formula ---------------------------------");
                            oneFormulaTesterConstruction(f);
                            f = "";
                        }else{
                            f += line;
                        }
                    }
                    //没有用'#'结尾
                    f = f.trim();
                    if(!f.equals("")) {
                        System.out.println("--------------------------------- No." +(i++)+ " formula ---------------------------------");
                        oneFormulaTesterConstruction(f);
                        f = "";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                showUsage();
            }
        }else{ // no any argument
            /*Scanner scanner = new Scanner(System.in);
            System.out.print("Input an LDL formula: ");
            String input = scanner.nextLine(); // 读取整行
            generateOneTester(input);*/
            showUsage();
        }
    }
}
