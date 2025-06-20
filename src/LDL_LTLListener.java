// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LDL_LTL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LDL_LTLParser}.
 */
public interface LDL_LTLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LDL_LTLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void enterLdl(LDL_LTLParser.LdlContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDL_LTLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void exitLdl(LDL_LTLParser.LdlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterUNARY_LTL_OPTR_LDL(LDL_LTLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitUNARY_LTL_OPTR_LDL(LDL_LTLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBINARY_BOOL_OPTR_LDL(LDL_LTLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBINARY_BOOL_OPTR_LDL(LDL_LTLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterDIAMOND_LDL(LDL_LTLParser.DIAMOND_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitDIAMOND_LDL(LDL_LTLParser.DIAMOND_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterATOM_LDL(LDL_LTLParser.ATOM_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitATOM_LDL(LDL_LTLParser.ATOM_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterNOT_LDL(LDL_LTLParser.NOT_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitNOT_LDL(LDL_LTLParser.NOT_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBINARY_LTL_OPTR_LDL(LDL_LTLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBINARY_LTL_OPTR_LDL(LDL_LTLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_LDL(LDL_LTLParser.PAREN_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_LDL(LDL_LTLParser.PAREN_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBOX_LDL(LDL_LTLParser.BOX_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LDL_LTLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBOX_LDL(LDL_LTLParser.BOX_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterPROP_PATHEXPR(LDL_LTLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitPROP_PATHEXPR(LDL_LTLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterREPETITION_PATHEXPR(LDL_LTLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitREPETITION_PATHEXPR(LDL_LTLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterTEST_PATHEXPR(LDL_LTLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitTEST_PATHEXPR(LDL_LTLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_PATHEXPR(LDL_LTLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_PATHEXPR(LDL_LTLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterTWO_OPERANDS_PATHEXPR(LDL_LTLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LDL_LTLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitTWO_OPERANDS_PATHEXPR(LDL_LTLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterNOT_PROP(LDL_LTLParser.NOT_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitNOT_PROP(LDL_LTLParser.NOT_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterTWO_OPERANDS_PROP(LDL_LTLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitTWO_OPERANDS_PROP(LDL_LTLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_PROP(LDL_LTLParser.PAREN_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_PROP(LDL_LTLParser.PAREN_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterATOM_PROP(LDL_LTLParser.ATOM_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LDL_LTLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitATOM_PROP(LDL_LTLParser.ATOM_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void enterID_ATOM(LDL_LTLParser.ID_ATOMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void exitID_ATOM(LDL_LTLParser.ID_ATOMContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void enterSTREXPR_ATOM(LDL_LTLParser.STREXPR_ATOMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LDL_LTLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void exitSTREXPR_ATOM(LDL_LTLParser.STREXPR_ATOMContext ctx);
}