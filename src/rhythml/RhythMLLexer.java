package rhythml;

// Generated from RhythML.g4 by ANTLR 4.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RhythMLLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, Id=4, Beat=5, WS=6;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "'['", "':'", "Id", "Beat", "WS"
	};
	public static final String[] ruleNames = {
		"T__2", "T__1", "T__0", "Id", "Beat", "WS"
	};


	public RhythMLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RhythML.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 5: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\b/\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5%\n\5\3\6\3\6"+
		"\3\7\6\7*\n\7\r\7\16\7+\3\7\3\7\2\b\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b"+
		"\2\3\2\4\7\2//QQZZqqzz\5\2\13\f\17\17\"\"\66\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\3\17\3\2\2\2\5\21\3\2"+
		"\2\2\7\23\3\2\2\2\t$\3\2\2\2\13&\3\2\2\2\r)\3\2\2\2\17\20\7_\2\2\20\4"+
		"\3\2\2\2\21\22\7]\2\2\22\6\3\2\2\2\23\24\7<\2\2\24\b\3\2\2\2\25%\7D\2"+
		"\2\26\27\7U\2\2\27%\7P\2\2\30\31\7J\2\2\31%\7V\2\2\32\33\7N\2\2\33%\7"+
		"V\2\2\34\35\7H\2\2\35%\7V\2\2\36\37\7J\2\2\37%\7J\2\2 !\7T\2\2!%\7f\2"+
		"\2\"#\7E\2\2#%\7E\2\2$\25\3\2\2\2$\26\3\2\2\2$\30\3\2\2\2$\32\3\2\2\2"+
		"$\34\3\2\2\2$\36\3\2\2\2$ \3\2\2\2$\"\3\2\2\2%\n\3\2\2\2&\'\t\2\2\2\'"+
		"\f\3\2\2\2(*\t\3\2\2)(\3\2\2\2*+\3\2\2\2+)\3\2\2\2+,\3\2\2\2,-\3\2\2\2"+
		"-.\b\7\2\2.\16\3\2\2\2\5\2$+";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}