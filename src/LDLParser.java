// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/ANTLR/testLDL/src/LDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LDLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Identifier=1, StringExpr=2, BIIMPLY=3, IMPLY=4, AND=5, OR=6, NOT=7, STAR=8, 
		PLUS=9, SEMI=10, QUESTION=11, PAREN_OPEN=12, PAREN_CLOSE=13, ANGLE_OPEN=14, 
		ANGLE_CLOSE=15, SQUARE_OPEN=16, SQUARE_CLOSE=17, WS=18;
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
			null, null, null, "'<->'", "'->'", "'&'", "'|'", "'!'", "'*'", "'+'", 
			"';'", "'?'", "'('", "')'", "'<'", "'>'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Identifier", "StringExpr", "BIIMPLY", "IMPLY", "AND", "OR", "NOT", 
			"STAR", "PLUS", "SEMI", "QUESTION", "PAREN_OPEN", "PAREN_CLOSE", "ANGLE_OPEN", 
			"ANGLE_CLOSE", "SQUARE_OPEN", "SQUARE_CLOSE", "WS"
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
	public String getGrammarFileName() { return "LDL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LDLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LdlContext extends ParserRuleContext {
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LDLParser.EOF, 0); }
		public LdlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).enterLdl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).exitLdl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDLVisitor ) return ((LDLVisitor<? extends T>)visitor).visitLdl(this);
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
		public Token op;
		public List<LdlFormulaContext> ldlFormula() {
			return getRuleContexts(LdlFormulaContext.class);
		}
		public LdlFormulaContext ldlFormula(int i) {
			return getRuleContext(LdlFormulaContext.class,i);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDLParser.PAREN_OPEN, 0); }
		public TerminalNode NOT() { return getToken(LDLParser.NOT, 0); }
		public PathExprContext pathExpr() {
			return getRuleContext(PathExprContext.class,0);
		}
		public TerminalNode ANGLE_CLOSE() { return getToken(LDLParser.ANGLE_CLOSE, 0); }
		public TerminalNode ANGLE_OPEN() { return getToken(LDLParser.ANGLE_OPEN, 0); }
		public TerminalNode SQUARE_CLOSE() { return getToken(LDLParser.SQUARE_CLOSE, 0); }
		public TerminalNode SQUARE_OPEN() { return getToken(LDLParser.SQUARE_OPEN, 0); }
		public AtomicFormulaContext atomicFormula() {
			return getRuleContext(AtomicFormulaContext.class,0);
		}
		public TerminalNode AND() { return getToken(LDLParser.AND, 0); }
		public TerminalNode OR() { return getToken(LDLParser.OR, 0); }
		public TerminalNode IMPLY() { return getToken(LDLParser.IMPLY, 0); }
		public TerminalNode BIIMPLY() { return getToken(LDLParser.BIIMPLY, 0); }
		public LdlFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldlFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).enterLdlFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).exitLdlFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDLVisitor ) return ((LDLVisitor<? extends T>)visitor).visitLdlFormula(this);
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
			setState(31);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAREN_OPEN:
				{
				setState(14);
				((LdlFormulaContext)_localctx).op = match(PAREN_OPEN);
				setState(15);
				ldlFormula(0);
				setState(16);
				match(PAREN_CLOSE);
				}
				break;
			case NOT:
				{
				setState(18);
				((LdlFormulaContext)_localctx).op = match(NOT);
				setState(19);
				ldlFormula(6);
				}
				break;
			case ANGLE_OPEN:
				{
				setState(20);
				((LdlFormulaContext)_localctx).op = match(ANGLE_OPEN);
				setState(21);
				pathExpr(0);
				setState(22);
				match(ANGLE_CLOSE);
				setState(23);
				ldlFormula(3);
				}
				break;
			case SQUARE_OPEN:
				{
				setState(25);
				((LdlFormulaContext)_localctx).op = match(SQUARE_OPEN);
				setState(26);
				pathExpr(0);
				setState(27);
				match(SQUARE_CLOSE);
				setState(28);
				ldlFormula(2);
				}
				break;
			case Identifier:
			case StringExpr:
				{
				setState(30);
				atomicFormula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(41);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(39);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new LdlFormulaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_ldlFormula);
						setState(33);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(34);
						((LdlFormulaContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((LdlFormulaContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(35);
						ldlFormula(6);
						}
						break;
					case 2:
						{
						_localctx = new LdlFormulaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_ldlFormula);
						setState(36);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(37);
						((LdlFormulaContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==BIIMPLY || _la==IMPLY) ) {
							((LdlFormulaContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(38);
						ldlFormula(5);
						}
						break;
					}
					} 
				}
				setState(43);
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
		public Token op;
		public List<PathExprContext> pathExpr() {
			return getRuleContexts(PathExprContext.class);
		}
		public PathExprContext pathExpr(int i) {
			return getRuleContext(PathExprContext.class,i);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDLParser.PAREN_OPEN, 0); }
		public LdlFormulaContext ldlFormula() {
			return getRuleContext(LdlFormulaContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(LDLParser.QUESTION, 0); }
		public PropFormulaContext propFormula() {
			return getRuleContext(PropFormulaContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(LDLParser.SEMI, 0); }
		public TerminalNode PLUS() { return getToken(LDLParser.PLUS, 0); }
		public TerminalNode STAR() { return getToken(LDLParser.STAR, 0); }
		public PathExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).enterPathExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).exitPathExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDLVisitor ) return ((LDLVisitor<? extends T>)visitor).visitPathExpr(this);
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
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(45);
				((PathExprContext)_localctx).op = match(PAREN_OPEN);
				setState(46);
				pathExpr(0);
				setState(47);
				match(PAREN_CLOSE);
				}
				break;
			case 2:
				{
				setState(49);
				ldlFormula(0);
				setState(50);
				((PathExprContext)_localctx).op = match(QUESTION);
				}
				break;
			case 3:
				{
				setState(52);
				propFormula(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(62);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(60);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new PathExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_pathExpr);
						setState(55);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(56);
						((PathExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==SEMI) ) {
							((PathExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(57);
						pathExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new PathExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_pathExpr);
						setState(58);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(59);
						((PathExprContext)_localctx).op = match(STAR);
						}
						break;
					}
					} 
				}
				setState(64);
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
		public Token op;
		public List<PropFormulaContext> propFormula() {
			return getRuleContexts(PropFormulaContext.class);
		}
		public PropFormulaContext propFormula(int i) {
			return getRuleContext(PropFormulaContext.class,i);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(LDLParser.PAREN_CLOSE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(LDLParser.PAREN_OPEN, 0); }
		public TerminalNode NOT() { return getToken(LDLParser.NOT, 0); }
		public AtomicFormulaContext atomicFormula() {
			return getRuleContext(AtomicFormulaContext.class,0);
		}
		public TerminalNode AND() { return getToken(LDLParser.AND, 0); }
		public TerminalNode OR() { return getToken(LDLParser.OR, 0); }
		public TerminalNode IMPLY() { return getToken(LDLParser.IMPLY, 0); }
		public TerminalNode BIIMPLY() { return getToken(LDLParser.BIIMPLY, 0); }
		public PropFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).enterPropFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).exitPropFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDLVisitor ) return ((LDLVisitor<? extends T>)visitor).visitPropFormula(this);
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
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAREN_OPEN:
				{
				setState(66);
				((PropFormulaContext)_localctx).op = match(PAREN_OPEN);
				setState(67);
				propFormula(0);
				setState(68);
				match(PAREN_CLOSE);
				}
				break;
			case NOT:
				{
				setState(70);
				((PropFormulaContext)_localctx).op = match(NOT);
				setState(71);
				propFormula(4);
				}
				break;
			case Identifier:
			case StringExpr:
				{
				setState(72);
				atomicFormula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(83);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(81);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new PropFormulaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_propFormula);
						setState(75);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(76);
						((PropFormulaContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((PropFormulaContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(77);
						propFormula(4);
						}
						break;
					case 2:
						{
						_localctx = new PropFormulaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_propFormula);
						setState(78);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(79);
						((PropFormulaContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==BIIMPLY || _la==IMPLY) ) {
							((PropFormulaContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(80);
						propFormula(3);
						}
						break;
					}
					} 
				}
				setState(85);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		public TerminalNode Identifier() { return getToken(LDLParser.Identifier, 0); }
		public TerminalNode StringExpr() { return getToken(LDLParser.StringExpr, 0); }
		public AtomicFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).enterAtomicFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDLListener ) ((LDLListener)listener).exitAtomicFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LDLVisitor ) return ((LDLVisitor<? extends T>)visitor).visitAtomicFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicFormulaContext atomicFormula() throws RecognitionException {
		AtomicFormulaContext _localctx = new AtomicFormulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atomicFormula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_la = _input.LA(1);
			if ( !(_la==Identifier || _la==StringExpr) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean pathExpr_sempred(PathExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean propFormula_sempred(PropFormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0012Y\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001 \b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001(\b"+
		"\u0001\n\u0001\f\u0001+\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u00026\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002=\b\u0002\n\u0002\f\u0002@\t\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003J\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0005\u0003R\b\u0003\n\u0003\f\u0003U\t"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0000\u0003\u0002\u0004\u0006"+
		"\u0005\u0000\u0002\u0004\u0006\b\u0000\u0004\u0001\u0000\u0005\u0006\u0001"+
		"\u0000\u0003\u0004\u0001\u0000\t\n\u0001\u0000\u0001\u0002a\u0000\n\u0001"+
		"\u0000\u0000\u0000\u0002\u001f\u0001\u0000\u0000\u0000\u00045\u0001\u0000"+
		"\u0000\u0000\u0006I\u0001\u0000\u0000\u0000\bV\u0001\u0000\u0000\u0000"+
		"\n\u000b\u0003\u0002\u0001\u0000\u000b\f\u0005\u0000\u0000\u0001\f\u0001"+
		"\u0001\u0000\u0000\u0000\r\u000e\u0006\u0001\uffff\uffff\u0000\u000e\u000f"+
		"\u0005\f\u0000\u0000\u000f\u0010\u0003\u0002\u0001\u0000\u0010\u0011\u0005"+
		"\r\u0000\u0000\u0011 \u0001\u0000\u0000\u0000\u0012\u0013\u0005\u0007"+
		"\u0000\u0000\u0013 \u0003\u0002\u0001\u0006\u0014\u0015\u0005\u000e\u0000"+
		"\u0000\u0015\u0016\u0003\u0004\u0002\u0000\u0016\u0017\u0005\u000f\u0000"+
		"\u0000\u0017\u0018\u0003\u0002\u0001\u0003\u0018 \u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0005\u0010\u0000\u0000\u001a\u001b\u0003\u0004\u0002\u0000"+
		"\u001b\u001c\u0005\u0011\u0000\u0000\u001c\u001d\u0003\u0002\u0001\u0002"+
		"\u001d \u0001\u0000\u0000\u0000\u001e \u0003\b\u0004\u0000\u001f\r\u0001"+
		"\u0000\u0000\u0000\u001f\u0012\u0001\u0000\u0000\u0000\u001f\u0014\u0001"+
		"\u0000\u0000\u0000\u001f\u0019\u0001\u0000\u0000\u0000\u001f\u001e\u0001"+
		"\u0000\u0000\u0000 )\u0001\u0000\u0000\u0000!\"\n\u0005\u0000\u0000\""+
		"#\u0007\u0000\u0000\u0000#(\u0003\u0002\u0001\u0006$%\n\u0004\u0000\u0000"+
		"%&\u0007\u0001\u0000\u0000&(\u0003\u0002\u0001\u0005\'!\u0001\u0000\u0000"+
		"\u0000\'$\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000)\'\u0001\u0000"+
		"\u0000\u0000)*\u0001\u0000\u0000\u0000*\u0003\u0001\u0000\u0000\u0000"+
		"+)\u0001\u0000\u0000\u0000,-\u0006\u0002\uffff\uffff\u0000-.\u0005\f\u0000"+
		"\u0000./\u0003\u0004\u0002\u0000/0\u0005\r\u0000\u000006\u0001\u0000\u0000"+
		"\u000012\u0003\u0002\u0001\u000023\u0005\u000b\u0000\u000036\u0001\u0000"+
		"\u0000\u000046\u0003\u0006\u0003\u00005,\u0001\u0000\u0000\u000051\u0001"+
		"\u0000\u0000\u000054\u0001\u0000\u0000\u00006>\u0001\u0000\u0000\u0000"+
		"78\n\u0004\u0000\u000089\u0007\u0002\u0000\u00009=\u0003\u0004\u0002\u0005"+
		":;\n\u0005\u0000\u0000;=\u0005\b\u0000\u0000<7\u0001\u0000\u0000\u0000"+
		"<:\u0001\u0000\u0000\u0000=@\u0001\u0000\u0000\u0000><\u0001\u0000\u0000"+
		"\u0000>?\u0001\u0000\u0000\u0000?\u0005\u0001\u0000\u0000\u0000@>\u0001"+
		"\u0000\u0000\u0000AB\u0006\u0003\uffff\uffff\u0000BC\u0005\f\u0000\u0000"+
		"CD\u0003\u0006\u0003\u0000DE\u0005\r\u0000\u0000EJ\u0001\u0000\u0000\u0000"+
		"FG\u0005\u0007\u0000\u0000GJ\u0003\u0006\u0003\u0004HJ\u0003\b\u0004\u0000"+
		"IA\u0001\u0000\u0000\u0000IF\u0001\u0000\u0000\u0000IH\u0001\u0000\u0000"+
		"\u0000JS\u0001\u0000\u0000\u0000KL\n\u0003\u0000\u0000LM\u0007\u0000\u0000"+
		"\u0000MR\u0003\u0006\u0003\u0004NO\n\u0002\u0000\u0000OP\u0007\u0001\u0000"+
		"\u0000PR\u0003\u0006\u0003\u0003QK\u0001\u0000\u0000\u0000QN\u0001\u0000"+
		"\u0000\u0000RU\u0001\u0000\u0000\u0000SQ\u0001\u0000\u0000\u0000ST\u0001"+
		"\u0000\u0000\u0000T\u0007\u0001\u0000\u0000\u0000US\u0001\u0000\u0000"+
		"\u0000VW\u0007\u0003\u0000\u0000W\t\u0001\u0000\u0000\u0000\t\u001f\'"+
		")5<>IQS";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}