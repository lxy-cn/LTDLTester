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
		SQUARE_CLOSE=15, NEXT=16, UNTIL=17, FINALLY=18, GLOBALLY=19, RELEASE=20, 
		PREVIOUS=21, SINCE=22, PAST=23, HISTORICALLY=24, TRIGGER=25, Identifier=26, 
		StringExpr=27, WS=28;
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
			"SQUARE_CLOSE", "NEXT", "UNTIL", "FINALLY", "GLOBALLY", "RELEASE", "PREVIOUS", 
			"SINCE", "PAST", "HISTORICALLY", "TRIGGER", "Identifier", "StringExpr", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<->'", "'->'", "'&'", "'|'", "'!'", "'*'", "'+'", "';'", "'?'", 
			"'('", "')'", "'<'", "'>'", "'['", "']'", "'X'", "'U'", "'F'", "'G'", 
			"'R'", "'Y'", "'S'", "'P'", "'H'", "'T'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BIIMPLY", "IMPLY", "AND", "OR", "NOT", "STAR", "PLUS", "SEMI", 
			"QUESTION", "PAREN_OPEN", "PAREN_CLOSE", "ANGLE_OPEN", "ANGLE_CLOSE", 
			"SQUARE_OPEN", "SQUARE_CLOSE", "NEXT", "UNTIL", "FINALLY", "GLOBALLY", 
			"RELEASE", "PREVIOUS", "SINCE", "PAST", "HISTORICALLY", "TRIGGER", "Identifier", 
			"StringExpr", "WS"
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
	public String getGrammarFileName() { return "backup/LDL_LTL.g4"; }

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
		"\u0004\u0000\u001c\u0085\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0005"+
		"\u0019q\b\u0019\n\u0019\f\u0019t\t\u0019\u0001\u001a\u0001\u001a\u0005"+
		"\u001ax\b\u001a\n\u001a\f\u001a{\t\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0004\u001b\u0080\b\u001b\u000b\u001b\f\u001b\u0081\u0001\u001b"+
		"\u0001\u001b\u0001y\u0000\u001c\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c\u0001"+
		"\u0000\u0003\u0003\u0000AZ__az\u0004\u000009AZ__az\u0003\u0000\t\n\r\r"+
		"  \u0087\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000"+
		"\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000"+
		"!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001"+
		"\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000"+
		"\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000"+
		"\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003"+
		"\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000"+
		"\u0000\u0000\u00019\u0001\u0000\u0000\u0000\u0003=\u0001\u0000\u0000\u0000"+
		"\u0005@\u0001\u0000\u0000\u0000\u0007B\u0001\u0000\u0000\u0000\tD\u0001"+
		"\u0000\u0000\u0000\u000bF\u0001\u0000\u0000\u0000\rH\u0001\u0000\u0000"+
		"\u0000\u000fJ\u0001\u0000\u0000\u0000\u0011L\u0001\u0000\u0000\u0000\u0013"+
		"N\u0001\u0000\u0000\u0000\u0015P\u0001\u0000\u0000\u0000\u0017R\u0001"+
		"\u0000\u0000\u0000\u0019T\u0001\u0000\u0000\u0000\u001bV\u0001\u0000\u0000"+
		"\u0000\u001dX\u0001\u0000\u0000\u0000\u001fZ\u0001\u0000\u0000\u0000!"+
		"\\\u0001\u0000\u0000\u0000#^\u0001\u0000\u0000\u0000%`\u0001\u0000\u0000"+
		"\u0000\'b\u0001\u0000\u0000\u0000)d\u0001\u0000\u0000\u0000+f\u0001\u0000"+
		"\u0000\u0000-h\u0001\u0000\u0000\u0000/j\u0001\u0000\u0000\u00001l\u0001"+
		"\u0000\u0000\u00003n\u0001\u0000\u0000\u00005u\u0001\u0000\u0000\u0000"+
		"7\u007f\u0001\u0000\u0000\u00009:\u0005<\u0000\u0000:;\u0005-\u0000\u0000"+
		";<\u0005>\u0000\u0000<\u0002\u0001\u0000\u0000\u0000=>\u0005-\u0000\u0000"+
		">?\u0005>\u0000\u0000?\u0004\u0001\u0000\u0000\u0000@A\u0005&\u0000\u0000"+
		"A\u0006\u0001\u0000\u0000\u0000BC\u0005|\u0000\u0000C\b\u0001\u0000\u0000"+
		"\u0000DE\u0005!\u0000\u0000E\n\u0001\u0000\u0000\u0000FG\u0005*\u0000"+
		"\u0000G\f\u0001\u0000\u0000\u0000HI\u0005+\u0000\u0000I\u000e\u0001\u0000"+
		"\u0000\u0000JK\u0005;\u0000\u0000K\u0010\u0001\u0000\u0000\u0000LM\u0005"+
		"?\u0000\u0000M\u0012\u0001\u0000\u0000\u0000NO\u0005(\u0000\u0000O\u0014"+
		"\u0001\u0000\u0000\u0000PQ\u0005)\u0000\u0000Q\u0016\u0001\u0000\u0000"+
		"\u0000RS\u0005<\u0000\u0000S\u0018\u0001\u0000\u0000\u0000TU\u0005>\u0000"+
		"\u0000U\u001a\u0001\u0000\u0000\u0000VW\u0005[\u0000\u0000W\u001c\u0001"+
		"\u0000\u0000\u0000XY\u0005]\u0000\u0000Y\u001e\u0001\u0000\u0000\u0000"+
		"Z[\u0005X\u0000\u0000[ \u0001\u0000\u0000\u0000\\]\u0005U\u0000\u0000"+
		"]\"\u0001\u0000\u0000\u0000^_\u0005F\u0000\u0000_$\u0001\u0000\u0000\u0000"+
		"`a\u0005G\u0000\u0000a&\u0001\u0000\u0000\u0000bc\u0005R\u0000\u0000c"+
		"(\u0001\u0000\u0000\u0000de\u0005Y\u0000\u0000e*\u0001\u0000\u0000\u0000"+
		"fg\u0005S\u0000\u0000g,\u0001\u0000\u0000\u0000hi\u0005P\u0000\u0000i"+
		".\u0001\u0000\u0000\u0000jk\u0005H\u0000\u0000k0\u0001\u0000\u0000\u0000"+
		"lm\u0005T\u0000\u0000m2\u0001\u0000\u0000\u0000nr\u0007\u0000\u0000\u0000"+
		"oq\u0007\u0001\u0000\u0000po\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000"+
		"\u0000rp\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000s4\u0001\u0000"+
		"\u0000\u0000tr\u0001\u0000\u0000\u0000uy\u0005\'\u0000\u0000vx\t\u0000"+
		"\u0000\u0000wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yz\u0001"+
		"\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000z|\u0001\u0000\u0000\u0000"+
		"{y\u0001\u0000\u0000\u0000|}\u0005\'\u0000\u0000}6\u0001\u0000\u0000\u0000"+
		"~\u0080\u0007\u0002\u0000\u0000\u007f~\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0006\u001b\u0000\u0000\u00848\u0001\u0000\u0000\u0000\u0004\u0000ry"+
		"\u0081\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}