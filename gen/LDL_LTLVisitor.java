// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LDL_LTL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LDL_LTLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LDL_LTLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LDL_LTLParser#ldl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdl(LDL_LTLParser.LdlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUNARY_LTL_OPTR_LDL(LDL_LTLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBINARY_BOOL_OPTR_LDL(LDL_LTLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDIAMOND_LDL(LDL_LTLParser.DIAMOND_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitATOM_LDL(LDL_LTLParser.ATOM_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT_LDL(LDL_LTLParser.NOT_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBINARY_LTL_OPTR_LDL(LDL_LTLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_LDL(LDL_LTLParser.PAREN_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBOX_LDL(LDL_LTLParser.BOX_LDLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPROP_PATHEXPR(LDL_LTLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitREPETITION_PATHEXPR(LDL_LTLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTEST_PATHEXPR(LDL_LTLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_PATHEXPR(LDL_LTLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTWO_OPERANDS_PATHEXPR(LDL_LTLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNOT_PROP(LDL_LTLParser.NOT_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTWO_OPERANDS_PROP(LDL_LTLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPAREN_PROP(LDL_LTLParser.PAREN_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitATOM_PROP(LDL_LTLParser.ATOM_PROPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitID_ATOM(LDL_LTLParser.ID_ATOMContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTREXPR_ATOM(LDL_LTLParser.STREXPR_ATOMContext ctx);
}