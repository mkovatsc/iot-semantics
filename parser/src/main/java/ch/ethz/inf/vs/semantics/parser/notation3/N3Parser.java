// Generated from antlr4\N3.g4 by ANTLR 4.3
package ch.ethz.inf.vs.semantics.parser.notation3;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class N3Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__15=1, T__14=2, T__13=3, T__12=4, T__11=5, T__10=6, T__9=7, T__8=8, 
		T__7=9, T__6=10, T__5=11, T__4=12, T__3=13, T__2=14, T__1=15, T__0=16, 
		NumericLiteral=17, BooleanLiteral=18, String=19, WS=20, COMMENT=21, PN_PREFIX=22, 
		IRIREF=23, PNAME_NS=24, PrefixedName=25, PNAME_LN=26, BLANK_NODE_LABEL=27, 
		LANGTAG=28, EXVAR=29, ALPHANUM=30, INTEGER=31, DECIMAL=32, DOUBLE=33, 
		EXPONENT=34, STRING_LITERAL_LONG_SINGLE_QUOTE=35, STRING_LITERAL_LONG_QUOTE=36, 
		STRING_LITERAL_QUOTE=37, STRING_LITERAL_SINGLE_QUOTE=38, UCHAR=39, ECHAR=40, 
		ANON_WS=41, ANON=42, SC=43, PN_CHARS_BASE=44, PN_CHARS_U=45, PN_CHARS=46, 
		PN_LOCAL=47, PLX=48, PERCENT=49, HEX=50, PN_LOCAL_ESC=51;
	public static final String[] tokenNames = {
		"<INVALID>", "'PREFIX'", "'['", "'^^'", "'{'", "';'", "']'", "'}'", "'=>'", 
		"'a'", "'('", "')'", "'@prefix'", "','", "'BASE'", "'@base'", "'.'", "NumericLiteral", 
		"BooleanLiteral", "String", "WS", "COMMENT", "PN_PREFIX", "IRIREF", "PNAME_NS", 
		"PrefixedName", "PNAME_LN", "BLANK_NODE_LABEL", "LANGTAG", "EXVAR", "ALPHANUM", 
		"INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL_LONG_SINGLE_QUOTE", 
		"STRING_LITERAL_LONG_QUOTE", "STRING_LITERAL_QUOTE", "STRING_LITERAL_SINGLE_QUOTE", 
		"UCHAR", "ECHAR", "ANON_WS", "ANON", "SC", "PN_CHARS_BASE", "PN_CHARS_U", 
		"PN_CHARS", "PN_LOCAL", "PLX", "PERCENT", "HEX", "PN_LOCAL_ESC"
	};
	public static final int
		RULE_n3Doc = 0, RULE_statements = 1, RULE_directive = 2, RULE_classification = 3, 
		RULE_prefixID = 4, RULE_base = 5, RULE_triple = 6, RULE_verbObject = 7, 
		RULE_verbObjectList = 8, RULE_objectList = 9, RULE_verb = 10, RULE_subject = 11, 
		RULE_object = 12, RULE_formula = 13, RULE_literal = 14, RULE_blankNodePropertyList = 15, 
		RULE_collection = 16, RULE_booleanLiteral = 17, RULE_iri = 18, RULE_blankNode = 19, 
		RULE_exvar = 20;
	public static final String[] ruleNames = {
		"n3Doc", "statements", "directive", "classification", "prefixID", "base", 
		"triple", "verbObject", "verbObjectList", "objectList", "verb", "subject", 
		"object", "formula", "literal", "blankNodePropertyList", "collection", 
		"booleanLiteral", "iri", "blankNode", "exvar"
	};

	@Override
	public String getGrammarFileName() { return "N3.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public N3Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class N3DocContext extends ParserRuleContext {
		public DirectiveContext directive(int i) {
			return getRuleContext(DirectiveContext.class,i);
		}
		public List<DirectiveContext> directive() {
			return getRuleContexts(DirectiveContext.class);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public N3DocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_n3Doc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterN3Doc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitN3Doc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitN3Doc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final N3DocContext n3Doc() throws RecognitionException {
		N3DocContext _localctx = new N3DocContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_n3Doc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__4) | (1L << T__2) | (1L << T__1))) != 0)) {
				{
				{
				setState(42); directive();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__12) | (1L << T__6) | (1L << NumericLiteral) | (1L << BooleanLiteral) | (1L << String) | (1L << IRIREF) | (1L << PrefixedName) | (1L << BLANK_NODE_LABEL) | (1L << LANGTAG) | (1L << EXVAR) | (1L << ANON))) != 0)) {
				{
				setState(48); statements();
				setState(49); match(T__0);
				}
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

	public static class StatementsContext extends ParserRuleContext {
		public ExvarContext exvar() {
			return getRuleContext(ExvarContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public ClassificationContext classification() {
			return getRuleContext(ClassificationContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public TripleContext triple() {
			return getRuleContext(TripleContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statements);
		try {
			setState(78);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(53); booleanLiteral();
				setState(56);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(54); match(T__0);
					setState(55); statements();
					}
					break;
				}
				setState(59);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(58); match(T__0);
					}
					break;
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61); triple();
				setState(64);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(62); match(T__0);
					setState(63); statements();
					}
					break;
				}
				setState(67);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(66); match(T__0);
					}
					break;
				}
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(69); exvar();
				setState(72);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(70); match(T__0);
					setState(71); statements();
					}
					break;
				}
				setState(75);
				switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
				case 1:
					{
					setState(74); match(T__0);
					}
					break;
				}
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(77); classification();
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

	public static class DirectiveContext extends ParserRuleContext {
		public BaseContext base() {
			return getRuleContext(BaseContext.class,0);
		}
		public PrefixIDContext prefixID() {
			return getRuleContext(PrefixIDContext.class,0);
		}
		public DirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitDirective(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DirectiveContext directive() throws RecognitionException {
		DirectiveContext _localctx = new DirectiveContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_directive);
		try {
			setState(82);
			switch (_input.LA(1)) {
			case T__15:
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(80); prefixID();
				}
				break;
			case T__2:
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(81); base();
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

	public static class ClassificationContext extends ParserRuleContext {
		public ObjectListContext objectList() {
			return getRuleContext(ObjectListContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode LANGTAG() { return getToken(N3Parser.LANGTAG, 0); }
		public ClassificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterClassification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitClassification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitClassification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassificationContext classification() throws RecognitionException {
		ClassificationContext _localctx = new ClassificationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classification);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); match(LANGTAG);
			setState(85); objectList();
			setState(86); match(T__0);
			setState(87); statements();
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

	public static class PrefixIDContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(N3Parser.IRIREF, 0); }
		public TerminalNode PNAME_NS() { return getToken(N3Parser.PNAME_NS, 0); }
		public PrefixIDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterPrefixID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitPrefixID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitPrefixID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixIDContext prefixID() throws RecognitionException {
		PrefixIDContext _localctx = new PrefixIDContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_prefixID);
		try {
			setState(96);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(89); match(T__4);
				setState(90); match(PNAME_NS);
				setState(91); match(IRIREF);
				setState(92); match(T__0);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(93); match(T__15);
				setState(94); match(PNAME_NS);
				setState(95); match(IRIREF);
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

	public static class BaseContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(N3Parser.IRIREF, 0); }
		public BaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_base; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterBase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitBase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitBase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseContext base() throws RecognitionException {
		BaseContext _localctx = new BaseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_base);
		try {
			setState(103);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(98); match(T__1);
				setState(99); match(IRIREF);
				setState(100); match(T__0);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(101); match(T__2);
				setState(102); match(IRIREF);
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

	public static class TripleContext extends ParserRuleContext {
		public SubjectContext subject() {
			return getRuleContext(SubjectContext.class,0);
		}
		public VerbObjectListContext verbObjectList() {
			return getRuleContext(VerbObjectListContext.class,0);
		}
		public BlankNodePropertyListContext blankNodePropertyList() {
			return getRuleContext(BlankNodePropertyListContext.class,0);
		}
		public TripleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterTriple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitTriple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitTriple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TripleContext triple() throws RecognitionException {
		TripleContext _localctx = new TripleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_triple);
		int _la;
		try {
			setState(112);
			switch (_input.LA(1)) {
			case T__12:
			case T__6:
			case NumericLiteral:
			case BooleanLiteral:
			case String:
			case IRIREF:
			case PrefixedName:
			case BLANK_NODE_LABEL:
			case EXVAR:
			case ANON:
				enterOuterAlt(_localctx, 1);
				{
				setState(105); subject();
				setState(106); verbObjectList();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(108); blankNodePropertyList();
				setState(110);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__7) | (1L << IRIREF) | (1L << PrefixedName) | (1L << BLANK_NODE_LABEL) | (1L << EXVAR))) != 0)) {
					{
					setState(109); verbObjectList();
					}
				}

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

	public static class VerbObjectContext extends ParserRuleContext {
		public VerbContext v;
		public ObjectListContext o;
		public ObjectListContext objectList() {
			return getRuleContext(ObjectListContext.class,0);
		}
		public VerbContext verb() {
			return getRuleContext(VerbContext.class,0);
		}
		public VerbObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verbObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterVerbObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitVerbObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitVerbObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbObjectContext verbObject() throws RecognitionException {
		VerbObjectContext _localctx = new VerbObjectContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_verbObject);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114); ((VerbObjectContext)_localctx).v = verb();
			setState(115); ((VerbObjectContext)_localctx).o = objectList();
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

	public static class VerbObjectListContext extends ParserRuleContext {
		public VerbObjectContext verbObject(int i) {
			return getRuleContext(VerbObjectContext.class,i);
		}
		public List<VerbObjectContext> verbObject() {
			return getRuleContexts(VerbObjectContext.class);
		}
		public VerbObjectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verbObjectList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterVerbObjectList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitVerbObjectList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitVerbObjectList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbObjectListContext verbObjectList() throws RecognitionException {
		VerbObjectListContext _localctx = new VerbObjectListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_verbObjectList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117); verbObject();
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(118); match(T__11);
				setState(119); verbObject();
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ObjectListContext extends ParserRuleContext {
		public ObjectContext object(int i) {
			return getRuleContext(ObjectContext.class,i);
		}
		public List<ObjectContext> object() {
			return getRuleContexts(ObjectContext.class);
		}
		public ObjectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterObjectList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitObjectList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitObjectList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectListContext objectList() throws RecognitionException {
		ObjectListContext _localctx = new ObjectListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_objectList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125); object();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(126); match(T__3);
				setState(127); object();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class VerbContext extends ParserRuleContext {
		public TerminalNode EXVAR() { return getToken(N3Parser.EXVAR, 0); }
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode BLANK_NODE_LABEL() { return getToken(N3Parser.BLANK_NODE_LABEL, 0); }
		public VerbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verb; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterVerb(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitVerb(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitVerb(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbContext verb() throws RecognitionException {
		VerbContext _localctx = new VerbContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_verb);
		try {
			setState(138);
			switch (_input.LA(1)) {
			case IRIREF:
			case PrefixedName:
				enterOuterAlt(_localctx, 1);
				{
				setState(133); iri();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(134); match(T__7);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(135); match(T__8);
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 4);
				{
				setState(136); match(BLANK_NODE_LABEL);
				}
				break;
			case EXVAR:
				enterOuterAlt(_localctx, 5);
				{
				setState(137); match(EXVAR);
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

	public static class SubjectContext extends ParserRuleContext {
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public ExvarContext exvar() {
			return getRuleContext(ExvarContext.class,0);
		}
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public CollectionContext collection() {
			return getRuleContext(CollectionContext.class,0);
		}
		public SubjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterSubject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitSubject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitSubject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubjectContext subject() throws RecognitionException {
		SubjectContext _localctx = new SubjectContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_subject);
		try {
			setState(147);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(140); booleanLiteral();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(141); iri();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(142); blankNode();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(143); collection();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(144); exvar();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(145); formula();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(146); literal();
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

	public static class ObjectContext extends ParserRuleContext {
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public ExvarContext exvar() {
			return getRuleContext(ExvarContext.class,0);
		}
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public CollectionContext collection() {
			return getRuleContext(CollectionContext.class,0);
		}
		public BlankNodePropertyListContext blankNodePropertyList() {
			return getRuleContext(BlankNodePropertyListContext.class,0);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_object);
		try {
			setState(156);
			switch (_input.LA(1)) {
			case IRIREF:
			case PrefixedName:
				enterOuterAlt(_localctx, 1);
				{
				setState(149); iri();
				}
				break;
			case ANON:
				enterOuterAlt(_localctx, 2);
				{
				setState(150); blankNode();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(151); collection();
				}
				break;
			case BLANK_NODE_LABEL:
			case EXVAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(152); exvar();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 5);
				{
				setState(153); blankNodePropertyList();
				}
				break;
			case NumericLiteral:
			case BooleanLiteral:
			case String:
				enterOuterAlt(_localctx, 6);
				{
				setState(154); literal();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 7);
				{
				setState(155); formula();
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

	public static class FormulaContext extends ParserRuleContext {
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158); match(T__12);
			setState(160);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__12) | (1L << T__6) | (1L << NumericLiteral) | (1L << BooleanLiteral) | (1L << String) | (1L << IRIREF) | (1L << PrefixedName) | (1L << BLANK_NODE_LABEL) | (1L << LANGTAG) | (1L << EXVAR) | (1L << ANON))) != 0)) {
				{
				setState(159); statements();
				}
			}

			setState(162); match(T__9);
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode NumericLiteral() { return getToken(N3Parser.NumericLiteral, 0); }
		public TerminalNode String() { return getToken(N3Parser.String, 0); }
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode BooleanLiteral() { return getToken(N3Parser.BooleanLiteral, 0); }
		public TerminalNode LANGTAG() { return getToken(N3Parser.LANGTAG, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_literal);
		try {
			setState(172);
			switch (_input.LA(1)) {
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(164); match(String);
				setState(168);
				switch (_input.LA(1)) {
				case LANGTAG:
					{
					setState(165); match(LANGTAG);
					}
					break;
				case T__13:
					{
					setState(166); match(T__13);
					setState(167); iri();
					}
					break;
				case T__14:
				case T__12:
				case T__11:
				case T__10:
				case T__9:
				case T__8:
				case T__7:
				case T__6:
				case T__5:
				case T__3:
				case T__0:
				case NumericLiteral:
				case BooleanLiteral:
				case String:
				case IRIREF:
				case PrefixedName:
				case BLANK_NODE_LABEL:
				case EXVAR:
				case ANON:
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case NumericLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(170); match(NumericLiteral);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(171); match(BooleanLiteral);
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

	public static class BlankNodePropertyListContext extends ParserRuleContext {
		public VerbObjectListContext verbObjectList() {
			return getRuleContext(VerbObjectListContext.class,0);
		}
		public BlankNodePropertyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNodePropertyList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterBlankNodePropertyList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitBlankNodePropertyList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitBlankNodePropertyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodePropertyListContext blankNodePropertyList() throws RecognitionException {
		BlankNodePropertyListContext _localctx = new BlankNodePropertyListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_blankNodePropertyList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174); match(T__14);
			setState(175); verbObjectList();
			setState(176); match(T__10);
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

	public static class CollectionContext extends ParserRuleContext {
		public ObjectContext object(int i) {
			return getRuleContext(ObjectContext.class,i);
		}
		public List<ObjectContext> object() {
			return getRuleContexts(ObjectContext.class);
		}
		public CollectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitCollection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionContext collection() throws RecognitionException {
		CollectionContext _localctx = new CollectionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_collection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178); match(T__6);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__12) | (1L << T__6) | (1L << NumericLiteral) | (1L << BooleanLiteral) | (1L << String) | (1L << IRIREF) | (1L << PrefixedName) | (1L << BLANK_NODE_LABEL) | (1L << EXVAR) | (1L << ANON))) != 0)) {
				{
				{
				setState(179); object();
				}
				}
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(185); match(T__5);
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

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode BooleanLiteral() { return getToken(N3Parser.BooleanLiteral, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_booleanLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187); match(BooleanLiteral);
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

	public static class IriContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(N3Parser.IRIREF, 0); }
		public TerminalNode PrefixedName() { return getToken(N3Parser.PrefixedName, 0); }
		public IriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterIri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitIri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriContext iri() throws RecognitionException {
		IriContext _localctx = new IriContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_iri);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_la = _input.LA(1);
			if ( !(_la==IRIREF || _la==PrefixedName) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class BlankNodeContext extends ParserRuleContext {
		public TerminalNode ANON() { return getToken(N3Parser.ANON, 0); }
		public BlankNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterBlankNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitBlankNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitBlankNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodeContext blankNode() throws RecognitionException {
		BlankNodeContext _localctx = new BlankNodeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191); match(ANON);
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

	public static class ExvarContext extends ParserRuleContext {
		public TerminalNode EXVAR() { return getToken(N3Parser.EXVAR, 0); }
		public TerminalNode BLANK_NODE_LABEL() { return getToken(N3Parser.BLANK_NODE_LABEL, 0); }
		public ExvarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exvar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).enterExvar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof N3Listener ) ((N3Listener)listener).exitExvar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof N3Visitor ) return ((N3Visitor<? extends T>)visitor).visitExvar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExvarContext exvar() throws RecognitionException {
		ExvarContext _localctx = new ExvarContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_exvar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			_la = _input.LA(1);
			if ( !(_la==BLANK_NODE_LABEL || _la==EXVAR) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\65\u00c6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\3\2\3\2\5\2\66\n\2\3\3\3\3\3\3\5\3;\n\3\3\3\5\3>\n\3\3\3\3\3\3\3"+
		"\5\3C\n\3\3\3\5\3F\n\3\3\3\3\3\3\3\5\3K\n\3\3\3\5\3N\n\3\3\3\5\3Q\n\3"+
		"\3\4\3\4\5\4U\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6"+
		"c\n\6\3\7\3\7\3\7\3\7\3\7\5\7j\n\7\3\b\3\b\3\b\3\b\3\b\5\bq\n\b\5\bs\n"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\n\7\n{\n\n\f\n\16\n~\13\n\3\13\3\13\3\13\7\13"+
		"\u0083\n\13\f\13\16\13\u0086\13\13\3\f\3\f\3\f\3\f\3\f\5\f\u008d\n\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0096\n\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\5\16\u009f\n\16\3\17\3\17\5\17\u00a3\n\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\5\20\u00ab\n\20\3\20\3\20\5\20\u00af\n\20\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\7\22\u00b7\n\22\f\22\16\22\u00ba\13\22\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\26\2\2\27\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*\2\4\4\2\31\31\33\33\4\2\35\35\37\37\u00d8\2/\3\2\2"+
		"\2\4P\3\2\2\2\6T\3\2\2\2\bV\3\2\2\2\nb\3\2\2\2\fi\3\2\2\2\16r\3\2\2\2"+
		"\20t\3\2\2\2\22w\3\2\2\2\24\177\3\2\2\2\26\u008c\3\2\2\2\30\u0095\3\2"+
		"\2\2\32\u009e\3\2\2\2\34\u00a0\3\2\2\2\36\u00ae\3\2\2\2 \u00b0\3\2\2\2"+
		"\"\u00b4\3\2\2\2$\u00bd\3\2\2\2&\u00bf\3\2\2\2(\u00c1\3\2\2\2*\u00c3\3"+
		"\2\2\2,.\5\6\4\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\65\3\2"+
		"\2\2\61/\3\2\2\2\62\63\5\4\3\2\63\64\7\22\2\2\64\66\3\2\2\2\65\62\3\2"+
		"\2\2\65\66\3\2\2\2\66\3\3\2\2\2\67:\5$\23\289\7\22\2\29;\5\4\3\2:8\3\2"+
		"\2\2:;\3\2\2\2;=\3\2\2\2<>\7\22\2\2=<\3\2\2\2=>\3\2\2\2>Q\3\2\2\2?B\5"+
		"\16\b\2@A\7\22\2\2AC\5\4\3\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DF\7\22\2\2"+
		"ED\3\2\2\2EF\3\2\2\2FQ\3\2\2\2GJ\5*\26\2HI\7\22\2\2IK\5\4\3\2JH\3\2\2"+
		"\2JK\3\2\2\2KM\3\2\2\2LN\7\22\2\2ML\3\2\2\2MN\3\2\2\2NQ\3\2\2\2OQ\5\b"+
		"\5\2P\67\3\2\2\2P?\3\2\2\2PG\3\2\2\2PO\3\2\2\2Q\5\3\2\2\2RU\5\n\6\2SU"+
		"\5\f\7\2TR\3\2\2\2TS\3\2\2\2U\7\3\2\2\2VW\7\36\2\2WX\5\24\13\2XY\7\22"+
		"\2\2YZ\5\4\3\2Z\t\3\2\2\2[\\\7\16\2\2\\]\7\32\2\2]^\7\31\2\2^c\7\22\2"+
		"\2_`\7\3\2\2`a\7\32\2\2ac\7\31\2\2b[\3\2\2\2b_\3\2\2\2c\13\3\2\2\2de\7"+
		"\21\2\2ef\7\31\2\2fj\7\22\2\2gh\7\20\2\2hj\7\31\2\2id\3\2\2\2ig\3\2\2"+
		"\2j\r\3\2\2\2kl\5\30\r\2lm\5\22\n\2ms\3\2\2\2np\5 \21\2oq\5\22\n\2po\3"+
		"\2\2\2pq\3\2\2\2qs\3\2\2\2rk\3\2\2\2rn\3\2\2\2s\17\3\2\2\2tu\5\26\f\2"+
		"uv\5\24\13\2v\21\3\2\2\2w|\5\20\t\2xy\7\7\2\2y{\5\20\t\2zx\3\2\2\2{~\3"+
		"\2\2\2|z\3\2\2\2|}\3\2\2\2}\23\3\2\2\2~|\3\2\2\2\177\u0084\5\32\16\2\u0080"+
		"\u0081\7\17\2\2\u0081\u0083\5\32\16\2\u0082\u0080\3\2\2\2\u0083\u0086"+
		"\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\25\3\2\2\2\u0086"+
		"\u0084\3\2\2\2\u0087\u008d\5&\24\2\u0088\u008d\7\13\2\2\u0089\u008d\7"+
		"\n\2\2\u008a\u008d\7\35\2\2\u008b\u008d\7\37\2\2\u008c\u0087\3\2\2\2\u008c"+
		"\u0088\3\2\2\2\u008c\u0089\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008b\3\2"+
		"\2\2\u008d\27\3\2\2\2\u008e\u0096\5$\23\2\u008f\u0096\5&\24\2\u0090\u0096"+
		"\5(\25\2\u0091\u0096\5\"\22\2\u0092\u0096\5*\26\2\u0093\u0096\5\34\17"+
		"\2\u0094\u0096\5\36\20\2\u0095\u008e\3\2\2\2\u0095\u008f\3\2\2\2\u0095"+
		"\u0090\3\2\2\2\u0095\u0091\3\2\2\2\u0095\u0092\3\2\2\2\u0095\u0093\3\2"+
		"\2\2\u0095\u0094\3\2\2\2\u0096\31\3\2\2\2\u0097\u009f\5&\24\2\u0098\u009f"+
		"\5(\25\2\u0099\u009f\5\"\22\2\u009a\u009f\5*\26\2\u009b\u009f\5 \21\2"+
		"\u009c\u009f\5\36\20\2\u009d\u009f\5\34\17\2\u009e\u0097\3\2\2\2\u009e"+
		"\u0098\3\2\2\2\u009e\u0099\3\2\2\2\u009e\u009a\3\2\2\2\u009e\u009b\3\2"+
		"\2\2\u009e\u009c\3\2\2\2\u009e\u009d\3\2\2\2\u009f\33\3\2\2\2\u00a0\u00a2"+
		"\7\6\2\2\u00a1\u00a3\5\4\3\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a4\u00a5\7\t\2\2\u00a5\35\3\2\2\2\u00a6\u00aa\7\25\2"+
		"\2\u00a7\u00ab\7\36\2\2\u00a8\u00a9\7\5\2\2\u00a9\u00ab\5&\24\2\u00aa"+
		"\u00a7\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00af\3\2"+
		"\2\2\u00ac\u00af\7\23\2\2\u00ad\u00af\7\24\2\2\u00ae\u00a6\3\2\2\2\u00ae"+
		"\u00ac\3\2\2\2\u00ae\u00ad\3\2\2\2\u00af\37\3\2\2\2\u00b0\u00b1\7\4\2"+
		"\2\u00b1\u00b2\5\22\n\2\u00b2\u00b3\7\b\2\2\u00b3!\3\2\2\2\u00b4\u00b8"+
		"\7\f\2\2\u00b5\u00b7\5\32\16\2\u00b6\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2"+
		"\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00b8"+
		"\3\2\2\2\u00bb\u00bc\7\r\2\2\u00bc#\3\2\2\2\u00bd\u00be\7\24\2\2\u00be"+
		"%\3\2\2\2\u00bf\u00c0\t\2\2\2\u00c0\'\3\2\2\2\u00c1\u00c2\7,\2\2\u00c2"+
		")\3\2\2\2\u00c3\u00c4\t\3\2\2\u00c4+\3\2\2\2\31/\65:=BEJMPTbipr|\u0084"+
		"\u008c\u0095\u009e\u00a2\u00aa\u00ae\u00b8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}