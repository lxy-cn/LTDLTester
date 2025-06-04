/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainLdlVisitor {
    public static String getTokenLiteralName(int tokenType) {
        return LDLParser.VOCABULARY.getLiteralName(tokenType).replace("'", "");
    }

    public  static String stringSingleQuotes2Parentheses(String s) {
        // In the string s, single quotes must occur in pairs
        return s.replaceAll("'(.*?)'", "($1)");
    }

    // 获得自定义的LDL语法树
    public static class LdlGetTreeVisitor extends LDLBaseVisitor<LDL> {
        public LDL visitLdl(LDLParser.LdlContext ctx) {
            return visit(ctx.ldlFormula());
        }

        public LDL visitLdlFormula(LDLParser.LdlFormulaContext ctx) {
            if ( ctx.getChildCount()==4 ) { // operations have 4 children
                if ( ctx.op.getType()==LDLParser.ANGLE_OPEN ) {
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
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.ldlFormula(0));
                }
                else if ( ctx.op.getType()==LDLParser.AND ) {
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.AND, l, r);
                }
                else if (ctx.op.getType()==LDLParser.OR) {
                    LDL l=visit(ctx.ldlFormula(0));
                    LDL r=visit(ctx.ldlFormula(1));
                    return new LDL(LDL.Operators.OR, l, r);
                }
                else if (ctx.op.getType()==LDLParser.BIIMPLY) {
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

        public LDL visitPathExpr(LDLParser.PathExprContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.pathExpr(0));
                }
                else if ( ctx.op.getType()==LDLParser.SEMI ) {
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
                if ( ctx.op.getType()==LDLParser.STAR ) {
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

        public LDL visitPropFormula(LDLParser.PropFormulaContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.propFormula(0));
                }
                else if ( ctx.op.getType()==LDLParser.AND ) {
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.AND, l, r);
                }
                else if ( ctx.op.getType()==LDLParser.OR ) {
                    LDL l=visit(ctx.propFormula(0));
                    LDL r=visit(ctx.propFormula(1));
                    return new LDL(LDL.Operators.OR, l, r);
                }
                else if (ctx.op.getType()==LDLParser.BIIMPLY) {
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

        public LDL visitAtomicFormula(LDLParser.AtomicFormulaContext ctx) {
            return new LDL(ctx.getText());
        }

        @Override
        public LDL visitTerminal(TerminalNode node) {
            return null;
        }
    }

    public static class LdlGetTextVisitor extends LDLBaseVisitor<String> {
        public String visitLdl(LDLParser.LdlContext ctx) {
            return visit(ctx.ldlFormula());
        }

        public String visitLdlFormula(LDLParser.LdlFormulaContext ctx) {
            if ( ctx.getChildCount()==4 ) { // operations have 4 children
                if ( ctx.op.getType()==LDLParser.ANGLE_OPEN ) {
                    String pe=visit(ctx.pathExpr()); // path expression
                    String sf=visit(ctx.ldlFormula(0)); // subformula
                    //return "<" + pe + ">" + sf;
                    return getTokenLiteralName(LDLParser.ANGLE_OPEN) + pe +
                            getTokenLiteralName(LDLParser.ANGLE_CLOSE) + sf;
                }
                else { // ctx.op.getType()==LDLParser.SQUARE_OPEN
                    String pe=visit(ctx.pathExpr()); // path expression
                    String sf=visit(ctx.ldlFormula(0)); // subformula
                    return getTokenLiteralName(LDLParser.SQUARE_OPEN) + pe +
                            getTokenLiteralName(LDLParser.SQUARE_CLOSE) + sf;
                }
            }
            else if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.ldlFormula(0));
                }
                else if ( ctx.op.getType()==LDLParser.AND ) {
                    String l=visit(ctx.ldlFormula(0));
                    String r=visit(ctx.ldlFormula(1));
                    return l + getTokenLiteralName(LDLParser.AND) + r;
                }
                else { // ctx.op.getType()==LDLParser.OR
                    String l=visit(ctx.ldlFormula(0));
                    String r=visit(ctx.ldlFormula(1));
                    return l + getTokenLiteralName(LDLParser.OR) + r;
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children: NOT subformula
                return getTokenLiteralName(LDLParser.NOT) + "(" + visit(ctx.ldlFormula(0)) + ")";
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public String visitPathExpr(LDLParser.PathExprContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.pathExpr(0));
                }
                else if ( ctx.op.getType()==LDLParser.SEMI ) {
                    String l=visit(ctx.pathExpr(0));
                    String r=visit(ctx.pathExpr(1));
                    return l + getTokenLiteralName(LDLParser.SEMI) + r;
                }
                else { // ctx.op.getType()==LDLParser.PLUS
                    String l=visit(ctx.pathExpr(0));
                    String r=visit(ctx.pathExpr(1));
                    return l + getTokenLiteralName(LDLParser.PLUS) + r;
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children
                if ( ctx.op.getType()==LDLParser.STAR ) {
                    return "(" + visit(ctx.pathExpr(0)) + ")" + getTokenLiteralName(LDLParser.STAR);
                }
                else { //ctx.op.getType()==LDLParser.QUESTION
                    return "(" + visit(ctx.ldlFormula()) + ")" + getTokenLiteralName(LDLParser.QUESTION);
                }
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public String visitPropFormula(LDLParser.PropFormulaContext ctx) {
            if ( ctx.getChildCount()==3 ) { // operations have 3 children
                if ( ctx.op.getType()==LDLParser.PAREN_OPEN ) {
                    return visit(ctx.propFormula(0));
                }
                else if ( ctx.op.getType()==LDLParser.AND ) {
                    String l=visit(ctx.propFormula(0));
                    String r=visit(ctx.propFormula(1));
                    return l + getTokenLiteralName(LDLParser.AND) + r;
                }
                else { // ctx.op.getType()==LDLParser.OR
                    String l=visit(ctx.propFormula(0));
                    String r=visit(ctx.propFormula(1));
                    return l + getTokenLiteralName(LDLParser.OR) + r;
                }
            }
            else if ( ctx.getChildCount()==2 ) { // operations have 2 children: NOT subformula
                return getTokenLiteralName(LDLParser.NOT) + "(" + visit(ctx.propFormula(0)) + ")";
            }
            else {
                return visitChildren(ctx);   // must be atomicFormula
            }
        }

        public String visitAtomicFormula(LDLParser.AtomicFormulaContext ctx) {
            return ctx.getText();
        }

        @Override
        public String visitTerminal(TerminalNode node) {
            return node.getText();
        }
    }

    public static void generateOneTester(String fmla) throws CloneNotSupportedException {
        //System.out.println(fmla);
        LDLLexer lexer = new LDLLexer(CharStreams.fromString(fmla));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LDLParser parser = new LDLParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        ParseTree tree = parser.ldl(); // parse

        LdlGetTreeVisitor ldlGetTreeVisitor = new LdlGetTreeVisitor();
        LDL ldlTree = ldlGetTreeVisitor.visit(tree);
        System.out.println("--The LDL formula to be verified: " + ldlTree.getText());
        ldlTree = ldlTree.box2diamond().reduceRedundantNotOperator();
        System.out.println("--The LDL formula without DIAMOND operators: " + ldlTree.getText());
//        ldlTree = ldlTree.reduceRedundantNotOperator();
//        System.out.println("--The LDL formula without redundant NOT operators: " + ldlTree.getText());

        Tester tester = new Tester("", ldlTree);
        String smvOutput = tester.toSMV();
        System.out.println(smvOutput);

//      System.out.println("----------------------------------------------");
    }

    public static void showUsage(){
        System.out.println("Usage: 'java -jar LDLTester.jar -ldl formula' or 'java -jar LDLTester.jar -file filename'");
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            if(args[0].equals("-ldl")){
                if(args.length<2){
                    System.out.println("Warning: an LDL formula must be inputted after '-ldl'.");
                    return;
                }
                generateOneTester(args[1]);
                return;
            }else if(args[0].equals("-file")){
                if(args.length<2){
                    System.out.println("Warning: an filename must be inputted after '-file'.");
                    return;
                }
                String inputFile = args[1];
                try {
                    // 读取所有行到List中
                    List<String> lines = Files.readAllLines(Paths.get(inputFile));

                    // 逐行处理
                    int i=1;
                    for (String line : lines) {
                        line=line.trim();
                        if(!line.startsWith("//")){
                            System.out.println("---------------------------------("+(i++)+")---------------------------------");
                            generateOneTester(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                showUsage();
            }
        }else{ // no any argument
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input an LDL formula: ");
            String input = scanner.nextLine(); // 读取整行
            generateOneTester(input);
        }
    }
}
