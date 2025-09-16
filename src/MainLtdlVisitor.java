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

public class MainLtdlVisitor {
    static int OutputLevel = 0; // 输出显示级别

    public static String getTokenLiteralName(int tokenType) {
        return LTDLParser.VOCABULARY.getLiteralName(tokenType).replace("'", "");
    }

    public  static String stringSingleQuotes2Parentheses(String s) {
        // In the string s, single quotes must occur in pairs
        return s.replaceAll("'(.*?)'", "($1)");
    }

    // 获得自定义的LDL语法树
    public static class Ldl_ltlGetTreeVisitor extends LTDLBaseVisitor<LDL> {
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitLdl(LTDLParser.LdlContext ctx) {
            return visit(ctx.ldlFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitUNARY_LTL_OPTR_LDL(LTDLParser.UNARY_LTL_OPTR_LDLContext ctx) {
            LDL c0 = null;
            c0 = visit(ctx.ldlFormula());
            if(c0 == null) return null;
            if (ctx.op.getType() == LTDLParser.NEXT)
                return new LDL(LDL.Operators.NEXT, c0);
            else if (ctx.op.getType() == LTDLParser.PREVIOUS)
                return new LDL(LDL.Operators.PREVIOUS, c0);
            else if (ctx.op.getType() == LTDLParser.FINALLY)
                return new LDL(LDL.Operators.FINALLY, c0);
            else if (ctx.op.getType() == LTDLParser.PAST)
                return new LDL(LDL.Operators.PAST, c0);
            else if (ctx.op.getType() == LTDLParser.GLOBALLY)
                return new LDL(LDL.Operators.GLOBALLY, c0);
            else if (ctx.op.getType() == LTDLParser.HISTORICALLY)
                return new LDL(LDL.Operators.HISTORICALLY, c0);
            else
                return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitBINARY_BOOL_OPTR_LDL(LTDLParser.BINARY_BOOL_OPTR_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.ldlFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LTDLParser.AND)
                return new LDL(LDL.Operators.AND, c0, c1);
            else if (ctx.op.getType() == LTDLParser.OR)
                return new LDL(LDL.Operators.OR, c0, c1);
            else if (ctx.op.getType() == LTDLParser.IMPLY)
                return new LDL(LDL.Operators.IMPLY, c0, c1);
            else if (ctx.op.getType() == LTDLParser.BIIMPLY)
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
        @Override public LDL visitDIAMOND_LDL(LTDLParser.DIAMOND_LDLContext ctx) {
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
        @Override public LDL visitATOM_LDL(LTDLParser.ATOM_LDLContext ctx) {
            return visit(ctx.atomicFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitNOT_LDL(LTDLParser.NOT_LDLContext ctx) {
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
        @Override public LDL visitBINARY_LTL_OPTR_LDL(LTDLParser.BINARY_LTL_OPTR_LDLContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.ldlFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.ldlFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LTDLParser.UNTIL)
                return new LDL(LDL.Operators.UNTIL, c0, c1);
            else if (ctx.op.getType() == LTDLParser.SINCE)
                return new LDL(LDL.Operators.SINCE, c0, c1);
            else if (ctx.op.getType() == LTDLParser.RELEASE)
                return new LDL(LDL.Operators.RELEASE, c0, c1);
            else if (ctx.op.getType() == LTDLParser.TRIGGER)
                return new LDL(LDL.Operators.TRIGGER, c0, c1);
            else
                return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitPAREN_LDL(LTDLParser.PAREN_LDLContext ctx) {
            return visit(ctx.ldlFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitBOX_LDL(LTDLParser.BOX_LDLContext ctx) {
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
        @Override public LDL visitPROP_PATHEXPR(LTDLParser.PROP_PATHEXPRContext ctx) {
            return visit(ctx.propFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitREPETITION_PATHEXPR(LTDLParser.REPETITION_PATHEXPRContext ctx) {
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
        @Override public LDL visitTEST_PATHEXPR(LTDLParser.TEST_PATHEXPRContext ctx) {
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
        @Override public LDL visitPAREN_PATHEXPR(LTDLParser.PAREN_PATHEXPRContext ctx) {
            return visit(ctx.pathExpr());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitTWO_OPERANDS_PATHEXPR(LTDLParser.TWO_OPERANDS_PATHEXPRContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.pathExpr(0));
            if (c0 == null) return null;
            c1 = visit(ctx.pathExpr(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LTDLParser.SEMI)
                return new LDL(LDL.Operators.CONCAT, c0, c1);
            else if (ctx.op.getType() == LTDLParser.PLUS)
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
        @Override public LDL visitNOT_PROP(LTDLParser.NOT_PROPContext ctx) {
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
        @Override public LDL visitTWO_OPERANDS_PROP(LTDLParser.TWO_OPERANDS_PROPContext ctx) {
            LDL c0=null, c1=null;
            c0 = visit(ctx.propFormula(0));
            if (c0 == null) return null;
            c1 = visit(ctx.propFormula(1));
            if (c1 == null) return null;
            if (ctx.op.getType() == LTDLParser.AND)
                return new LDL(LDL.Operators.AND, c0, c1);
            else if (ctx.op.getType() == LTDLParser.OR)
                return new LDL(LDL.Operators.OR, c0, c1);
            else if (ctx.op.getType() == LTDLParser.IMPLY)
                return new LDL(LDL.Operators.IMPLY, c0, c1);
            else if (ctx.op.getType() == LTDLParser.BIIMPLY)
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
        @Override public LDL visitPAREN_PROP(LTDLParser.PAREN_PROPContext ctx) {
            return visit(ctx.propFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitATOM_PROP(LTDLParser.ATOM_PROPContext ctx) {
            return visit(ctx.atomicFormula());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitID_ATOM(LTDLParser.ID_ATOMContext ctx) {
            return new LDL(ctx.Identifier().getText());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public LDL visitSTREXPR_ATOM(LTDLParser.STREXPR_ATOMContext ctx) {
            return new LDL(ctx.StringExpr().getText());
        }
    }

    public static void oneFormulaTesterConstruction(String fmla) throws CloneNotSupportedException {
        //System.out.println(fmla);
        LTDLLexer lexer = new LTDLLexer(CharStreams.fromString(fmla));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LTDLParser parser = new LTDLParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        ParseTree tree = parser.ldl(); // parse

        Ldl_ltlGetTreeVisitor ldlGetTreeVisitor = new Ldl_ltlGetTreeVisitor();
        LDL ldlTree = ldlGetTreeVisitor.visit(tree);
        System.out.println("--The original LTDL formula: " + ldlTree.getText(false));
        ldlTree = ldlTree.box2diamond().reduceRedundantNotOperator();  // eliminate box and redundant not operators

        Tester tester = new Tester("", ldlTree);
        String smvOutput = tester.toSMV();
        System.out.println(smvOutput);
    }

    public static void showUsage(){
        System.out.println("Usage: 'java -jar LTDLTester.jar -ldl formula' or 'java -jar LTDLTester.jar -file filename'");
    }

    public static void showVersion(){
        System.out.println("*** LTDLTester 1.2.0 is a temporal tester generator for Linear Temporal Dynamic Logic (LTDL), which is a combination ofLinear Dynamic Logic (LDL) and Future and Past Linear Temporal Logic (LTL). The generated tester is written in SMV language that is used by NuSMV and nuXmv.");
        System.out.println("*** Copyright (c) 2025, Xiangyu Luo at Huaqiao University");
        System.out.println("*** For more information please contact us via luoxy@hqu.edu.cn");
    }

    public static void main(String[] args) throws Exception {
        showVersion();
        if (args.length > 0) {
            if(args[0].equals("-ldl")){
                if(args.length<2){
                    System.out.println("Warning: an LTDL formula must be inputted after '-ldl'.");
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
            System.out.print("Input an LTDL formula: ");
            String input = scanner.nextLine(); // 读取整行
            generateOneTester(input);*/
            showUsage();
        }
    }
}
