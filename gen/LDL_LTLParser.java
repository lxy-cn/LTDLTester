// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LDL_LTL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LDL_LTLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BIIMPLY=1, IMPLY=2, AND=3, OR=4, NOT=5, STAR=6, PLUS=7, SEMI=8, QUESTION=9, 
		PAREN_OPEN=10, PAREN_CLOSE=11, ANGLE_OPEN=12, ANGLE_CLOSE=13, SQUARE_OPEN=14, 
		SQUARE_CLOSE=15, NEXT=16, PREV=17, GLOBALLY=18, FINALLY=19, UNTIL=20, 
		RELEASE=21, Identifier=22, StringExpr=23, WS=24;
	public static final int
		RULE_ldl = 0, RULE_ldlFormula = 1, RULE_pathExpr = 2, RULE_propFormula = 3, 
		RULE_atomicFormula = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"ldl", "ldlFormula", "pathExpr", "propFormula", "atomicFormula"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<->'", "'->'", "'&'", "'|'", "'!'", "'*'", "'+'", "';'", "'?'", 
			"'('", "')'", "'<'", "'>'", "'['", "']'", "'X'", "'Y'", "'G'", "'F'", 
			"'U'", "'R'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BIIMPLY", "IMPLY", "AND", "OR", "NOT", "STAR", "PLUS", "SEMI", 
			"QUESTION", "PAREN_OPEN", "PAREN_CLOSE", "ANGLE_OPEN", "ANGLE_CLOSE", 
			"SQUARE_OPEN", "SQUARE_CLOSE", "NEXT", "PREV", "GLOBALLY", "FINALLY", 
			"UNTIL", "RELEASE", "Identifier", "StringExpr", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "LDL_LTL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LDL_LTLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LdlContext extends ParserRuleContext {
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LDL_LTLParser.EOF, 0); }
		public LdlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterLdl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitLdl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitLdl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdlContext ldl() throws RecognitionException {
		LdlContext _localctx = new LdlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_ldl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			ldlFormula(0);
			setState(11);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LdlFormulaContext extends ParserRuleContext {
		public LdlFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldlFormula; }
	 
		public LdlFormulaContext() { }
		public void copyFrom(LdlFormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UNARY_LTL_OPTR_LDLContext extends LdlFormulaContext {
		public Token op;
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode NEXT() { return getToken(LDL_LTLParser.NEXT, 0); }
		public TerminalNode PREV() { return getToken(LDL_LTLParser.PREV, 0); }
		public TerminalNode GLOBALLY() { return getToken(LDL_LTLParser.GLOBALLY, 0); }
		public TerminalNode FINALLY() { return getToken(LDL_LTLParser.FINALLY, 0); }
		public UNARY_LTL_OPTR_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterUNARY_LTL_OPTR_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitUNARY_LTL_OPTR_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitUNARY_LTL_OPTR_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BINARY_BOOL_OPTR_LDLContext extends LdlFormulaContext {
		public Token op;
		public List<LdlFormulaContext> ldlFormula() {
			return getRuleContexts(LdlFormulaContext.class);
		}
		public LdlFormulaContext ldlFormula(int i) {
			return getRuleContext(LdlFormulaContext.class,i);
		}
		public TerminalNode AND() { return getToken(LDL_LTLParser.AND, 0); }
		public TerminalNode OR() { return getToken(LDL_LTLParser.OR, 0); }
		public TerminalNode IMPLY() { return getToken(LDL_LTLParser.IMPLY, 0); }
		public TerminalNode BIIMPLY() { return getToken(LDL_LTLParser.BIIMPLY, 0); }
		public BINARY_BOOL_OPTR_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterBINARY_BOOL_OPTR_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitBINARY_BOOL_OPTR_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitBINARY_BOOL_OPTR_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DIAMOND_LDLContext extends LdlFormulaContext {
		public Token op;
		public PathExprContext pathExpr() {
			return getRuleContext(PathExprContext.class,0);
		}
		public TerminalNode ANGLE_CLOSE() { return getToken(LDL_LTLParser.ANGLE_CLOSE, 0); }
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode ANGLE_OPEN() { return getToken(LDL_LTLParser.ANGLE_OPEN, 0); }
		public DIAMOND_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterDIAMOND_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitDIAMOND_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitDIAMOND_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ATOM_LDLContext extends LdlFormulaContext {
		public AtomicFormulaContext atomicFormula() {
			return getRuleContext(AtomicFormulaContext.class,0);
		}
		public ATOM_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterATOM_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitATOM_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitATOM_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NOT_LDLContext extends LdlFormulaContext {
		public Token op;
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode NOT() { return getToken(LDL_LTLParser.NOT, 0); }
		public NOT_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterNOT_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitNOT_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitNOT_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BINARY_LTL_OPTR_LDLContext extends LdlFormulaContext {
		public Token op;
		public List<LdlFormulaContext> ldlFormula() {
			return getRuleContexts(LdlFormulaContext.class);
		}
		public LdlFormulaContext ldlFormula(int i) {
			return getRuleContext(LdlFormulaContext.class,i);
		}
		public TerminalNode UNTIL() { return getToken(LDL_LTLParser.UNTIL, 0); }
		public TerminalNode RELEASE() { return getToken(LDL_LTLParser.RELEASE, 0); }
		public BINARY_LTL_OPTR_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterBINARY_LTL_OPTR_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitBINARY_LTL_OPTR_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitBINARY_LTL_OPTR_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PAREN_LDLContext extends LdlFormulaContext {
		public Token op;
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDL_LTLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDL_LTLParser.PAREN_OPEN, 0); }
		public PAREN_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterPAREN_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitPAREN_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitPAREN_LDL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BOX_LDLContext extends LdlFormulaContext {
		public Token op;
		public PathExprContext pathExpr() {
			return getRuleContext(PathExprContext.class,0);
		}
		public TerminalNode SQUARE_CLOSE() { return getToken(LDL_LTLParser.SQUARE_CLOSE, 0); }
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode SQUARE_OPEN() { return getToken(LDL_LTLParser.SQUARE_OPEN, 0); }
		public BOX_LDLContext(LdlFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterBOX_LDL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitBOX_LDL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitBOX_LDL(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdlFormulaContext ldlFormula() throws RecognitionException {
		return ldlFormula(0);
	}

	private LdlFormulaContext ldlFormula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LdlFormulaContext _localctx = new LdlFormulaContext(_ctx, _parentState);
		LdlFormulaContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_ldlFormula, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAREN_OPEN:
				{
				_localctx = new PAREN_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(14);
				((PAREN_LDLContext)_localctx).op = match(PAREN_OPEN);
				setState(15);
				ldlFormula(0);
				setState(16);
				match(PAREN_CLOSE);
				}
				break;
			case Identifier:
			case StringExpr:
				{
				_localctx = new ATOM_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(18);
				atomicFormula();
				}
				break;
			case NOT:
				{
				_localctx = new NOT_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19);
				((NOT_LDLContext)_localctx).op = match(NOT);
				setState(20);
				ldlFormula(6);
				}
				break;
			case NEXT:
			case PREV:
			case GLOBALLY:
			case FINALLY:
				{
				_localctx = new UNARY_LTL_OPTR_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(21);
				((UNARY_LTL_OPTR_LDLContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 983040L) != 0)) ) {
					((UNARY_LTL_OPTR_LDLContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(22);
				ldlFormula(5);
				}
				break;
			case ANGLE_OPEN:
				{
				_localctx = new DIAMOND_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(23);
				((DIAMOND_LDLContext)_localctx).op = match(ANGLE_OPEN);
				setState(24);
				pathExpr(0);
				setState(25);
				match(ANGLE_CLOSE);
				setState(26);
				ldlFormula(3);
				}
				break;
			case SQUARE_OPEN:
				{
				_localctx = new BOX_LDLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(28);
				((BOX_LDLContext)_localctx).op = match(SQUARE_OPEN);
				setState(29);
				pathExpr(0);
				setState(30);
				match(SQUARE_CLOSE);
				setState(31);
				ldlFormula(2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(43);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(41);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new BINARY_LTL_OPTR_LDLContext(new LdlFormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_ldlFormula);
						setState(35);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(36);
						((BINARY_LTL_OPTR_LDLContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==UNTIL || _la==RELEASE) ) {
							((BINARY_LTL_OPTR_LDLContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(37);
						ldlFormula(5);
						}
						break;
					case 2:
						{
						_localctx = new BINARY_BOOL_OPTR_LDLContext(new LdlFormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_ldlFormula);
						setState(38);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(39);
						((BINARY_BOOL_OPTR_LDLContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0)) ) {
							((BINARY_BOOL_OPTR_LDLContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(40);
						ldlFormula(2);
						}
						break;
					}
					} 
				}
				setState(45);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PathExprContext extends ParserRuleContext {
		public PathExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathExpr; }
	 
		public PathExprContext() { }
		public void copyFrom(PathExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PROP_PATHEXPRContext extends PathExprContext {
		public PropFormulaContext propFormula() {
			return getRuleContext(PropFormulaContext.class,0);
		}
		public PROP_PATHEXPRContext(PathExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterPROP_PATHEXPR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitPROP_PATHEXPR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitPROP_PATHEXPR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class REPETITION_PATHEXPRContext extends PathExprContext {
		public Token op;
		public PathExprContext pathExpr() {
			return getRuleContext(PathExprContext.class,0);
		}
		public TerminalNode STAR() { return getToken(LDL_LTLParser.STAR, 0); }
		public REPETITION_PATHEXPRContext(PathExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterREPETITION_PATHEXPR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitREPETITION_PATHEXPR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitREPETITION_PATHEXPR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TEST_PATHEXPRContext extends PathExprContext {
		public Token op;
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(LDL_LTLParser.QUESTION, 0); }
		public TEST_PATHEXPRContext(PathExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterTEST_PATHEXPR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitTEST_PATHEXPR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitTEST_PATHEXPR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PAREN_PATHEXPRContext extends PathExprContext {
		public Token op;
		public PathExprContext pathExpr() {
			return getRuleContext(PathExprContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDL_LTLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDL_LTLParser.PAREN_OPEN, 0); }
		public PAREN_PATHEXPRContext(PathExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterPAREN_PATHEXPR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitPAREN_PATHEXPR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitPAREN_PATHEXPR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TWO_OPERANDS_PATHEXPRContext extends PathExprContext {
		public Token op;
		public List<PathExprContext> pathExpr() {
			return getRuleContexts(PathExprContext.class);
		}
		public PathExprContext pathExpr(int i) {
			return getRuleContext(PathExprContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(LDL_LTLParser.SEMI, 0); }
		public TerminalNode PLUS() { return getToken(LDL_LTLParser.PLUS, 0); }
		public TWO_OPERANDS_PATHEXPRContext(PathExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterTWO_OPERANDS_PATHEXPR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitTWO_OPERANDS_PATHEXPR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitTWO_OPERANDS_PATHEXPR(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathExprContext pathExpr() throws RecognitionException {
		return pathExpr(0);
	}

	private PathExprContext pathExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PathExprContext _localctx = new PathExprContext(_ctx, _parentState);
		PathExprContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_pathExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				_localctx = new PAREN_PATHEXPRContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(47);
				((PAREN_PATHEXPRContext)_localctx).op = match(PAREN_OPEN);
				setState(48);
				pathExpr(0);
				setState(49);
				match(PAREN_CLOSE);
				}
				break;
			case 2:
				{
				_localctx = new PROP_PATHEXPRContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(51);
				propFormula(0);
				}
				break;
			case 3:
				{
				_localctx = new TEST_PATHEXPRContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(52);
				ldlFormula(0);
				setState(53);
				((TEST_PATHEXPRContext)_localctx).op = match(QUESTION);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(64);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(62);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new TWO_OPERANDS_PATHEXPRContext(new PathExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_pathExpr);
						setState(57);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(58);
						((TWO_OPERANDS_PATHEXPRContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==SEMI) ) {
							((TWO_OPERANDS_PATHEXPRContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(59);
						pathExpr(2);
						}
						break;
					case 2:
						{
						_localctx = new REPETITION_PATHEXPRContext(new PathExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_pathExpr);
						setState(60);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(61);
						((REPETITION_PATHEXPRContext)_localctx).op = match(STAR);
						}
						break;
					}
					} 
				}
				setState(66);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropFormulaContext extends ParserRuleContext {
		public PropFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propFormula; }
	 
		public PropFormulaContext() { }
		public void copyFrom(PropFormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NOT_PROPContext extends PropFormulaContext {
		public Token op;
		public PropFormulaContext propFormula() {
			return getRuleContext(PropFormulaContext.class,0);
		}
		public TerminalNode NOT() { return getToken(LDL_LTLParser.NOT, 0); }
		public NOT_PROPContext(PropFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterNOT_PROP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitNOT_PROP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitNOT_PROP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TWO_OPERANDS_PROPContext extends PropFormulaContext {
		public Token op;
		public List<PropFormulaContext> propFormula() {
			return getRuleContexts(PropFormulaContext.class);
		}
		public PropFormulaContext propFormula(int i) {
			return getRuleContext(PropFormulaContext.class,i);
		}
		public TerminalNode AND() { return getToken(LDL_LTLParser.AND, 0); }
		public TerminalNode OR() { return getToken(LDL_LTLParser.OR, 0); }
		public TerminalNode IMPLY() { return getToken(LDL_LTLParser.IMPLY, 0); }
		public TerminalNode BIIMPLY() { return getToken(LDL_LTLParser.BIIMPLY, 0); }
		public TWO_OPERANDS_PROPContext(PropFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterTWO_OPERANDS_PROP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitTWO_OPERANDS_PROP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitTWO_OPERANDS_PROP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PAREN_PROPContext extends PropFormulaContext {
		public Token op;
		public PropFormulaContext propFormula() {
			return getRuleContext(PropFormulaContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDL_LTLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDL_LTLParser.PAREN_OPEN, 0); }
		public PAREN_PROPContext(PropFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterPAREN_PROP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitPAREN_PROP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitPAREN_PROP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ATOM_PROPContext extends PropFormulaContext {
		public AtomicFormulaContext atomicFormula() {
			return getRuleContext(AtomicFormulaContext.class,0);
		}
		public ATOM_PROPContext(PropFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterATOM_PROP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitATOM_PROP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitATOM_PROP(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropFormulaContext propFormula() throws RecognitionException {
		return propFormula(0);
	}

	private PropFormulaContext propFormula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PropFormulaContext _localctx = new PropFormulaContext(_ctx, _parentState);
		PropFormulaContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_propFormula, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAREN_OPEN:
				{
				_localctx = new PAREN_PROPContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(68);
				((PAREN_PROPContext)_localctx).op = match(PAREN_OPEN);
				setState(69);
				propFormula(0);
				setState(70);
				match(PAREN_CLOSE);
				}
				break;
			case Identifier:
			case StringExpr:
				{
				_localctx = new ATOM_PROPContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(72);
				atomicFormula();
				}
				break;
			case NOT:
				{
				_localctx = new NOT_PROPContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(73);
				((NOT_PROPContext)_localctx).op = match(NOT);
				setState(74);
				propFormula(2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(82);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TWO_OPERANDS_PROPContext(new PropFormulaContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_propFormula);
					setState(77);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(78);
					((TWO_OPERANDS_PROPContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0)) ) {
						((TWO_OPERANDS_PROPContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(79);
					propFormula(2);
					}
					} 
				}
				setState(84);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomicFormulaContext extends ParserRuleContext {
		public AtomicFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicFormula; }
	 
		public AtomicFormulaContext() { }
		public void copyFrom(AtomicFormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STREXPR_ATOMContext extends AtomicFormulaContext {
		public TerminalNode StringExpr() { return getToken(LDL_LTLParser.StringExpr, 0); }
		public STREXPR_ATOMContext(AtomicFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterSTREXPR_ATOM(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitSTREXPR_ATOM(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitSTREXPR_ATOM(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ID_ATOMContext extends AtomicFormulaContext {
		public TerminalNode Identifier() { return getToken(LDL_LTLParser.Identifier, 0); }
		public ID_ATOMContext(AtomicFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).enterID_ATOM(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDL_LTLListener ) ((LDL_LTLListener)listener).exitID_ATOM(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDL_LTLVisitor ) return ((LDL_LTLVisitor<? extends T>)visitor).visitID_ATOM(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicFormulaContext atomicFormula() throws RecognitionException {
		AtomicFormulaContext _localctx = new AtomicFormulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atomicFormula);
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				_localctx = new ID_ATOMContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				match(Identifier);
				}
				break;
			case StringExpr:
				_localctx = new STREXPR_ATOMContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(86);
				match(StringExpr);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return ldlFormula_sempred((LdlFormulaContext)_localctx, predIndex);
		case 2:
			return pathExpr_sempred((PathExprContext)_localctx, predIndex);
		case 3:
			return propFormula_sempred((PropFormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean ldlFormula_sempred(LdlFormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean pathExpr_sempred(PathExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean propFormula_sempred(PropFormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0018Z\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\"\b"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001*\b\u0001\n\u0001\f\u0001-\t\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u00028\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0005\u0002?\b\u0002\n\u0002\f\u0002B\t"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003L\b\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0005\u0003Q\b\u0003\n\u0003\f\u0003T\t\u0003\u0001"+
		"\u0004\u0001\u0004\u0003\u0004X\b\u0004\u0001\u0004\u0000\u0003\u0002"+
		"\u0004\u0006\u0005\u0000\u0002\u0004\u0006\b\u0000\u0004\u0001\u0000\u0010"+
		"\u0013\u0001\u0000\u0014\u0015\u0001\u0000\u0001\u0004\u0001\u0000\u0007"+
		"\bc\u0000\n\u0001\u0000\u0000\u0000\u0002!\u0001\u0000\u0000\u0000\u0004"+
		"7\u0001\u0000\u0000\u0000\u0006K\u0001\u0000\u0000\u0000\bW\u0001\u0000"+
		"\u0000\u0000\n\u000b\u0003\u0002\u0001\u0000\u000b\f\u0005\u0000\u0000"+
		"\u0001\f\u0001\u0001\u0000\u0000\u0000\r\u000e\u0006\u0001\uffff\uffff"+
		"\u0000\u000e\u000f\u0005\n\u0000\u0000\u000f\u0010\u0003\u0002\u0001\u0000"+
		"\u0010\u0011\u0005\u000b\u0000\u0000\u0011\"\u0001\u0000\u0000\u0000\u0012"+
		"\"\u0003\b\u0004\u0000\u0013\u0014\u0005\u0005\u0000\u0000\u0014\"\u0003"+
		"\u0002\u0001\u0006\u0015\u0016\u0007\u0000\u0000\u0000\u0016\"\u0003\u0002"+
		"\u0001\u0005\u0017\u0018\u0005\f\u0000\u0000\u0018\u0019\u0003\u0004\u0002"+
		"\u0000\u0019\u001a\u0005\r\u0000\u0000\u001a\u001b\u0003\u0002\u0001\u0003"+
		"\u001b\"\u0001\u0000\u0000\u0000\u001c\u001d\u0005\u000e\u0000\u0000\u001d"+
		"\u001e\u0003\u0004\u0002\u0000\u001e\u001f\u0005\u000f\u0000\u0000\u001f"+
		" \u0003\u0002\u0001\u0002 \"\u0001\u0000\u0000\u0000!\r\u0001\u0000\u0000"+
		"\u0000!\u0012\u0001\u0000\u0000\u0000!\u0013\u0001\u0000\u0000\u0000!"+
		"\u0015\u0001\u0000\u0000\u0000!\u0017\u0001\u0000\u0000\u0000!\u001c\u0001"+
		"\u0000\u0000\u0000\"+\u0001\u0000\u0000\u0000#$\n\u0004\u0000\u0000$%"+
		"\u0007\u0001\u0000\u0000%*\u0003\u0002\u0001\u0005&\'\n\u0001\u0000\u0000"+
		"\'(\u0007\u0002\u0000\u0000(*\u0003\u0002\u0001\u0002)#\u0001\u0000\u0000"+
		"\u0000)&\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000"+
		"\u0000\u0000+,\u0001\u0000\u0000\u0000,\u0003\u0001\u0000\u0000\u0000"+
		"-+\u0001\u0000\u0000\u0000./\u0006\u0002\uffff\uffff\u0000/0\u0005\n\u0000"+
		"\u000001\u0003\u0004\u0002\u000012\u0005\u000b\u0000\u000028\u0001\u0000"+
		"\u0000\u000038\u0003\u0006\u0003\u000045\u0003\u0002\u0001\u000056\u0005"+
		"\t\u0000\u000068\u0001\u0000\u0000\u00007.\u0001\u0000\u0000\u000073\u0001"+
		"\u0000\u0000\u000074\u0001\u0000\u0000\u00008@\u0001\u0000\u0000\u0000"+
		"9:\n\u0001\u0000\u0000:;\u0007\u0003\u0000\u0000;?\u0003\u0004\u0002\u0002"+
		"<=\n\u0003\u0000\u0000=?\u0005\u0006\u0000\u0000>9\u0001\u0000\u0000\u0000"+
		"><\u0001\u0000\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000"+
		"\u0000@A\u0001\u0000\u0000\u0000A\u0005\u0001\u0000\u0000\u0000B@\u0001"+
		"\u0000\u0000\u0000CD\u0006\u0003\uffff\uffff\u0000DE\u0005\n\u0000\u0000"+
		"EF\u0003\u0006\u0003\u0000FG\u0005\u000b\u0000\u0000GL\u0001\u0000\u0000"+
		"\u0000HL\u0003\b\u0004\u0000IJ\u0005\u0005\u0000\u0000JL\u0003\u0006\u0003"+
		"\u0002KC\u0001\u0000\u0000\u0000KH\u0001\u0000\u0000\u0000KI\u0001\u0000"+
		"\u0000\u0000LR\u0001\u0000\u0000\u0000MN\n\u0001\u0000\u0000NO\u0007\u0002"+
		"\u0000\u0000OQ\u0003\u0006\u0003\u0002PM\u0001\u0000\u0000\u0000QT\u0001"+
		"\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000"+
		"S\u0007\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000UX\u0005\u0016"+
		"\u0000\u0000VX\u0005\u0017\u0000\u0000WU\u0001\u0000\u0000\u0000WV\u0001"+
		"\u0000\u0000\u0000X\t\u0001\u0000\u0000\u0000\t!)+7>@KRW";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}