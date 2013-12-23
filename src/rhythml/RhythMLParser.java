package rhythml;

// Generated from RhythML.g4 by ANTLR 4.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RhythMLParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, Id=4, Beat=5, WS=6;
	public static final String[] tokenNames = {
		"<INVALID>", "']'", "'['", "':'", "Id", "Beat", "WS"
	};
	public static final int
		RULE_p = 0, RULE_rhy = 1, RULE_inst = 2, RULE_bar = 3, RULE_beats = 4;
	public static final String[] ruleNames = {
		"p", "rhy", "inst", "bar", "beats"
	};

	@Override
	public String getGrammarFileName() { return "RhythML.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public RhythMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PContext extends ParserRuleContext {
		public RhyContext rhy() {
			return getRuleContext(RhyContext.class,0);
		}
		public PContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_p; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).enterP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).exitP(this);
		}
	}

	public final PContext p() throws RecognitionException {
		PContext _localctx = new PContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_p);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10); rhy();
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

	public static class RhyContext extends ParserRuleContext {
		public RhyContext rhy() {
			return getRuleContext(RhyContext.class,0);
		}
		public InstContext inst() {
			return getRuleContext(InstContext.class,0);
		}
		public RhyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rhy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).enterRhy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).exitRhy(this);
		}
	}

	public final RhyContext rhy() throws RecognitionException {
		RhyContext _localctx = new RhyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_rhy);
		try {
			setState(16);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(12); inst();
				setState(13); rhy();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(15); inst();
				}
				break;
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

	public static class InstContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(RhythMLParser.Id, 0); }
		public BarContext bar() {
			return getRuleContext(BarContext.class,0);
		}
		public InstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).enterInst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).exitInst(this);
		}
	}

	public final InstContext inst() throws RecognitionException {
		InstContext _localctx = new InstContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_inst);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); match(Id);
			setState(19); match(3);
			setState(20); bar();
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

	public static class BarContext extends ParserRuleContext {
		public BeatsContext beats() {
			return getRuleContext(BeatsContext.class,0);
		}
		public BarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).enterBar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).exitBar(this);
		}
	}

	public final BarContext bar() throws RecognitionException {
		BarContext _localctx = new BarContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_bar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22); match(2);
			setState(23); beats();
			setState(24); match(1);
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

	public static class BeatsContext extends ParserRuleContext {
		public BeatsContext beats() {
			return getRuleContext(BeatsContext.class,0);
		}
		public TerminalNode Beat() { return getToken(RhythMLParser.Beat, 0); }
		public BeatsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beats; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).enterBeats(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RhythMLListener ) ((RhythMLListener)listener).exitBeats(this);
		}
	}

	public final BeatsContext beats() throws RecognitionException {
		BeatsContext _localctx = new BeatsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_beats);
		try {
			setState(29);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(26); match(Beat);
				setState(27); beats();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(28); match(Beat);
				}
				break;
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

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\b\"\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\3\3\3\3\3\3\3\5\3\23\n\3\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\5\6 \n\6\3\6\2\7\2\4\6\b\n\2\2\36"+
		"\2\f\3\2\2\2\4\22\3\2\2\2\6\24\3\2\2\2\b\30\3\2\2\2\n\37\3\2\2\2\f\r\5"+
		"\4\3\2\r\3\3\2\2\2\16\17\5\6\4\2\17\20\5\4\3\2\20\23\3\2\2\2\21\23\5\6"+
		"\4\2\22\16\3\2\2\2\22\21\3\2\2\2\23\5\3\2\2\2\24\25\7\6\2\2\25\26\7\5"+
		"\2\2\26\27\5\b\5\2\27\7\3\2\2\2\30\31\7\4\2\2\31\32\5\n\6\2\32\33\7\3"+
		"\2\2\33\t\3\2\2\2\34\35\7\7\2\2\35 \5\n\6\2\36 \7\7\2\2\37\34\3\2\2\2"+
		"\37\36\3\2\2\2 \13\3\2\2\2\4\22\37";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}