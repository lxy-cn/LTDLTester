// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/ANTLR/testLDL/src/LDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LDLParser}.
 */
public interface LDLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LDLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void enterLdl(LDLParser.LdlContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void exitLdl(LDLParser.LdlContext ctx);
	/**
	 * Enter a parse tree produced by {@link LDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterLdlFormula(LDLParser.LdlFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitLdlFormula(LDLParser.LdlFormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link LDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterPathExpr(LDLParser.PathExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitPathExpr(LDLParser.PathExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterPropFormula(LDLParser.PropFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitPropFormula(LDLParser.PropFormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link LDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void enterAtomicFormula(LDLParser.AtomicFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void exitAtomicFormula(LDLParser.AtomicFormulaContext ctx);
}