// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LTDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LTDLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LTDLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LTDLParser#ldl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdl(LTDLParser.LdlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUNARY_LTL_OPTR_LDL(LTDLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBINARY_BOOL_OPTR_LDL(LTDLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDIAMOND_LDL(LTDLParser.DIAMOND_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitATOM_LDL(LTDLParser.ATOM_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT_LDL(LTDLParser.NOT_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBINARY_LTL_OPTR_LDL(LTDLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_LDL(LTDLParser.PAREN_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBOX_LDL(LTDLParser.BOX_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPROP_PATHEXPR(LTDLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitREPETITION_PATHEXPR(LTDLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTEST_PATHEXPR(LTDLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_PATHEXPR(LTDLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTWO_OPERANDS_PATHEXPR(LTDLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT_PROP(LTDLParser.NOT_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTWO_OPERANDS_PROP(LTDLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_PROP(LTDLParser.PAREN_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitATOM_PROP(LTDLParser.ATOM_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitID_ATOM(LTDLParser.ID_ATOMContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTREXPR_ATOM(LTDLParser.STREXPR_ATOMContext ctx);
}