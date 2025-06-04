// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/ANTLR/testLDL/src/LDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LDLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LDLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LDLParser#ldl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdl(LDLParser.LdlContext ctx);
	/**
	 * Visit a parse tree produced by {@link LDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdlFormula(LDLParser.LdlFormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link LDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathExpr(LDLParser.PathExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LDLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropFormula(LDLParser.PropFormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link LDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicFormula(LDLParser.AtomicFormulaContext ctx);
}