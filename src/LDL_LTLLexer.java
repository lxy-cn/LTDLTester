// Generated from /Users/lxy/Documents/Doc-LXY-iMac/RecentDoc/Development/LDLTester/src/LDL_LTL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LDL_LTLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BIIMPLY=1, IMPLY=2, AND=3, OR=4, NOT=5, STAR=6, PLUS=7, SEMI=8, QUESTION=9, 
		PAREN_OPEN=10, PAREN_CLOSE=11, ANGLE_OPEN=12, ANGLE_CLOSE=13, SQUARE_OPEN=14, 
		SQUARE_CLOSE=15, NEXT=16, PREV=17, GLOBALLY=18, FINALLY=19, UNTIL=20, 
		RELEASE=21, Identifier=22, StringExpr=23, WS=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BIIMPLY", "IMPLY", "AND", "OR", "NOT", "STAR", "PLUS", "SEMI", "QUESTION", 
			"PAREN_OPEN", "PAREN_CLOSE", "ANGLE_OPEN", "ANGLE_CLOSE", "SQUARE_OPEN", 
			"SQUARE_CLOSE", "NEXT", "PREV", "GLOBALLY", "FINALLY", "UNTIL", "RELEASE", 
			"Identifier", "StringExpr", "WS"
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


	public LDL_LTLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LDL_LTL.g4"; }

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
		"\u0004\u0000\u0018u\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0005\u0015a\b\u0015\n\u0015\f\u0015d\t\u0015\u0001\u0016"+
		"\u0001\u0016\u0005\u0016h\b\u0016\n\u0016\f\u0016k\t\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0004\u0017p\b\u0017\u000b\u0017\f\u0017q\u0001"+
		"\u0017\u0001\u0017\u0001i\u0000\u0018\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014)\u0015+\u0016-\u0017/\u0018\u0001\u0000\u0003\u0003\u0000"+
		"AZ__az\u0004\u000009AZ__az\u0003\u0000\t\n\r\r  w\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u0001"+
		"1\u0001\u0000\u0000\u0000\u00035\u0001\u0000\u0000\u0000\u00058\u0001"+
		"\u0000\u0000\u0000\u0007:\u0001\u0000\u0000\u0000\t<\u0001\u0000\u0000"+
		"\u0000\u000b>\u0001\u0000\u0000\u0000\r@\u0001\u0000\u0000\u0000\u000f"+
		"B\u0001\u0000\u0000\u0000\u0011D\u0001\u0000\u0000\u0000\u0013F\u0001"+
		"\u0000\u0000\u0000\u0015H\u0001\u0000\u0000\u0000\u0017J\u0001\u0000\u0000"+
		"\u0000\u0019L\u0001\u0000\u0000\u0000\u001bN\u0001\u0000\u0000\u0000\u001d"+
		"P\u0001\u0000\u0000\u0000\u001fR\u0001\u0000\u0000\u0000!T\u0001\u0000"+
		"\u0000\u0000#V\u0001\u0000\u0000\u0000%X\u0001\u0000\u0000\u0000\'Z\u0001"+
		"\u0000\u0000\u0000)\\\u0001\u0000\u0000\u0000+^\u0001\u0000\u0000\u0000"+
		"-e\u0001\u0000\u0000\u0000/o\u0001\u0000\u0000\u000012\u0005<\u0000\u0000"+
		"23\u0005-\u0000\u000034\u0005>\u0000\u00004\u0002\u0001\u0000\u0000\u0000"+
		"56\u0005-\u0000\u000067\u0005>\u0000\u00007\u0004\u0001\u0000\u0000\u0000"+
		"89\u0005&\u0000\u00009\u0006\u0001\u0000\u0000\u0000:;\u0005|\u0000\u0000"+
		";\b\u0001\u0000\u0000\u0000<=\u0005!\u0000\u0000=\n\u0001\u0000\u0000"+
		"\u0000>?\u0005*\u0000\u0000?\f\u0001\u0000\u0000\u0000@A\u0005+\u0000"+
		"\u0000A\u000e\u0001\u0000\u0000\u0000BC\u0005;\u0000\u0000C\u0010\u0001"+
		"\u0000\u0000\u0000DE\u0005?\u0000\u0000E\u0012\u0001\u0000\u0000\u0000"+
		"FG\u0005(\u0000\u0000G\u0014\u0001\u0000\u0000\u0000HI\u0005)\u0000\u0000"+
		"I\u0016\u0001\u0000\u0000\u0000JK\u0005<\u0000\u0000K\u0018\u0001\u0000"+
		"\u0000\u0000LM\u0005>\u0000\u0000M\u001a\u0001\u0000\u0000\u0000NO\u0005"+
		"[\u0000\u0000O\u001c\u0001\u0000\u0000\u0000PQ\u0005]\u0000\u0000Q\u001e"+
		"\u0001\u0000\u0000\u0000RS\u0005X\u0000\u0000S \u0001\u0000\u0000\u0000"+
		"TU\u0005Y\u0000\u0000U\"\u0001\u0000\u0000\u0000VW\u0005G\u0000\u0000"+
		"W$\u0001\u0000\u0000\u0000XY\u0005F\u0000\u0000Y&\u0001\u0000\u0000\u0000"+
		"Z[\u0005U\u0000\u0000[(\u0001\u0000\u0000\u0000\\]\u0005R\u0000\u0000"+
		"]*\u0001\u0000\u0000\u0000^b\u0007\u0000\u0000\u0000_a\u0007\u0001\u0000"+
		"\u0000`_\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000"+
		"\u0000\u0000bc\u0001\u0000\u0000\u0000c,\u0001\u0000\u0000\u0000db\u0001"+
		"\u0000\u0000\u0000ei\u0005\'\u0000\u0000fh\t\u0000\u0000\u0000gf\u0001"+
		"\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000"+
		"ig\u0001\u0000\u0000\u0000jl\u0001\u0000\u0000\u0000ki\u0001\u0000\u0000"+
		"\u0000lm\u0005\'\u0000\u0000m.\u0001\u0000\u0000\u0000np\u0007\u0002\u0000"+
		"\u0000on\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000qr\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0006"+
		"\u0017\u0000\u0000t0\u0001\u0000\u0000\u0000\u0004\u0000biq\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}