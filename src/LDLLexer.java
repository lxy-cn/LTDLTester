// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/ANTLR/testLDL/src/LDL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LDLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Identifier=1, StringExpr=2, BIIMPLY=3, IMPLY=4, AND=5, OR=6, NOT=7, STAR=8, 
		PLUS=9, SEMI=10, QUESTION=11, PAREN_OPEN=12, PAREN_CLOSE=13, ANGLE_OPEN=14, 
		ANGLE_CLOSE=15, SQUARE_OPEN=16, SQUARE_CLOSE=17, WS=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Identifier", "StringExpr", "BIIMPLY", "IMPLY", "AND", "OR", "NOT", "STAR", 
			"PLUS", "SEMI", "QUESTION", "PAREN_OPEN", "PAREN_CLOSE", "ANGLE_OPEN", 
			"ANGLE_CLOSE", "SQUARE_OPEN", "SQUARE_CLOSE", "WS"
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


	public LDLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LDL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012]\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001"+
		"\u0000\u0001\u0000\u0005\u0000(\b\u0000\n\u0000\f\u0000+\t\u0000\u0001"+
		"\u0001\u0001\u0001\u0005\u0001/\b\u0001\n\u0001\f\u00012\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0004\u0011X\b\u0011\u000b\u0011\f\u0011"+
		"Y\u0001\u0011\u0001\u0011\u00010\u0000\u0012\u0001\u0001\u0003\u0002\u0005"+
		"\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n"+
		"\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012\u0001\u0000\u0003\u0003\u0000AZ__az\u0004\u000009AZ__az\u0003"+
		"\u0000\t\n\r\r  _\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0001"+
		"%\u0001\u0000\u0000\u0000\u0003,\u0001\u0000\u0000\u0000\u00055\u0001"+
		"\u0000\u0000\u0000\u00079\u0001\u0000\u0000\u0000\t<\u0001\u0000\u0000"+
		"\u0000\u000b>\u0001\u0000\u0000\u0000\r@\u0001\u0000\u0000\u0000\u000f"+
		"B\u0001\u0000\u0000\u0000\u0011D\u0001\u0000\u0000\u0000\u0013F\u0001"+
		"\u0000\u0000\u0000\u0015H\u0001\u0000\u0000\u0000\u0017J\u0001\u0000\u0000"+
		"\u0000\u0019L\u0001\u0000\u0000\u0000\u001bN\u0001\u0000\u0000\u0000\u001d"+
		"P\u0001\u0000\u0000\u0000\u001fR\u0001\u0000\u0000\u0000!T\u0001\u0000"+
		"\u0000\u0000#W\u0001\u0000\u0000\u0000%)\u0007\u0000\u0000\u0000&(\u0007"+
		"\u0001\u0000\u0000\'&\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000"+
		")\'\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*\u0002\u0001\u0000"+
		"\u0000\u0000+)\u0001\u0000\u0000\u0000,0\u0005\'\u0000\u0000-/\t\u0000"+
		"\u0000\u0000.-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u000001\u0001"+
		"\u0000\u0000\u00000.\u0001\u0000\u0000\u000013\u0001\u0000\u0000\u0000"+
		"20\u0001\u0000\u0000\u000034\u0005\'\u0000\u00004\u0004\u0001\u0000\u0000"+
		"\u000056\u0005<\u0000\u000067\u0005-\u0000\u000078\u0005>\u0000\u0000"+
		"8\u0006\u0001\u0000\u0000\u00009:\u0005-\u0000\u0000:;\u0005>\u0000\u0000"+
		";\b\u0001\u0000\u0000\u0000<=\u0005&\u0000\u0000=\n\u0001\u0000\u0000"+
		"\u0000>?\u0005|\u0000\u0000?\f\u0001\u0000\u0000\u0000@A\u0005!\u0000"+
		"\u0000A\u000e\u0001\u0000\u0000\u0000BC\u0005*\u0000\u0000C\u0010\u0001"+
		"\u0000\u0000\u0000DE\u0005+\u0000\u0000E\u0012\u0001\u0000\u0000\u0000"+
		"FG\u0005;\u0000\u0000G\u0014\u0001\u0000\u0000\u0000HI\u0005?\u0000\u0000"+
		"I\u0016\u0001\u0000\u0000\u0000JK\u0005(\u0000\u0000K\u0018\u0001\u0000"+
		"\u0000\u0000LM\u0005)\u0000\u0000M\u001a\u0001\u0000\u0000\u0000NO\u0005"+
		"<\u0000\u0000O\u001c\u0001\u0000\u0000\u0000PQ\u0005>\u0000\u0000Q\u001e"+
		"\u0001\u0000\u0000\u0000RS\u0005[\u0000\u0000S \u0001\u0000\u0000\u0000"+
		"TU\u0005]\u0000\u0000U\"\u0001\u0000\u0000\u0000VX\u0007\u0002\u0000\u0000"+
		"WV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000"+
		"\u0000YZ\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[\\\u0006\u0011"+
		"\u0000\u0000\\$\u0001\u0000\u0000\u0000\u0004\u0000)0Y\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}