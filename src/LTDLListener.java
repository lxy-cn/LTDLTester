// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LTDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LTDLParser}.
 */
public interface LTDLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LTDLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void enterLdl(LTDLParser.LdlContext ctx);
	/**
	 * Exit a parse tree produced by {@link LTDLParser#ldl}.
	 * @param ctx the parse tree
	 */
	void exitLdl(LTDLParser.LdlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterUNARY_LTL_OPTR_LDL(LTDLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UNARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitUNARY_LTL_OPTR_LDL(LTDLParser.UNARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBINARY_BOOL_OPTR_LDL(LTDLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BINARY_BOOL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBINARY_BOOL_OPTR_LDL(LTDLParser.BINARY_BOOL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterDIAMOND_LDL(LTDLParser.DIAMOND_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DIAMOND_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitDIAMOND_LDL(LTDLParser.DIAMOND_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterATOM_LDL(LTDLParser.ATOM_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ATOM_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitATOM_LDL(LTDLParser.ATOM_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterNOT_LDL(LTDLParser.NOT_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitNOT_LDL(LTDLParser.NOT_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBINARY_LTL_OPTR_LDL(LTDLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BINARY_LTL_OPTR_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBINARY_LTL_OPTR_LDL(LTDLParser.BINARY_LTL_OPTR_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_LDL(LTDLParser.PAREN_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_LDL(LTDLParser.PAREN_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void enterBOX_LDL(LTDLParser.BOX_LDLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BOX_LDL}
	 * labeled alternative in {@link LTDLParser#ldlFormula}.
	 * @param ctx the parse tree
	 */
	void exitBOX_LDL(LTDLParser.BOX_LDLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterPROP_PATHEXPR(LTDLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PROP_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitPROP_PATHEXPR(LTDLParser.PROP_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterREPETITION_PATHEXPR(LTDLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code REPETITION_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitREPETITION_PATHEXPR(LTDLParser.REPETITION_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterTEST_PATHEXPR(LTDLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TEST_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitTEST_PATHEXPR(LTDLParser.TEST_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_PATHEXPR(LTDLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_PATHEXPR(LTDLParser.PAREN_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void enterTWO_OPERANDS_PATHEXPR(LTDLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TWO_OPERANDS_PATHEXPR}
	 * labeled alternative in {@link LTDLParser#pathExpr}.
	 * @param ctx the parse tree
	 */
	void exitTWO_OPERANDS_PATHEXPR(LTDLParser.TWO_OPERANDS_PATHEXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterNOT_PROP(LTDLParser.NOT_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NOT_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitNOT_PROP(LTDLParser.NOT_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterTWO_OPERANDS_PROP(LTDLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TWO_OPERANDS_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitTWO_OPERANDS_PROP(LTDLParser.TWO_OPERANDS_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterPAREN_PROP(LTDLParser.PAREN_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PAREN_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitPAREN_PROP(LTDLParser.PAREN_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void enterATOM_PROP(LTDLParser.ATOM_PROPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ATOM_PROP}
	 * labeled alternative in {@link LTDLParser#propFormula}.
	 * @param ctx the parse tree
	 */
	void exitATOM_PROP(LTDLParser.ATOM_PROPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void enterID_ATOM(LTDLParser.ID_ATOMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ID_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void exitID_ATOM(LTDLParser.ID_ATOMContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void enterSTREXPR_ATOM(LTDLParser.STREXPR_ATOMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STREXPR_ATOM}
	 * labeled alternative in {@link LTDLParser#atomicFormula}.
	 * @param ctx the parse tree
	 */
	void exitSTREXPR_ATOM(LTDLParser.STREXPR_ATOMContext ctx);
}