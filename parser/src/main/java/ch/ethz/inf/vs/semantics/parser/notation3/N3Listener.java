// Generated from antlr4\N3.g4 by ANTLR 4.3
package ch.ethz.inf.vs.semantics.parser.notation3;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link N3Parser}.
 */
public interface N3Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link N3Parser#iri}.
	 * @param ctx the parse tree
	 */
	void enterIri(@NotNull N3Parser.IriContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#iri}.
	 * @param ctx the parse tree
	 */
	void exitIri(@NotNull N3Parser.IriContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#verbObjectList}.
	 * @param ctx the parse tree
	 */
	void enterVerbObjectList(@NotNull N3Parser.VerbObjectListContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#verbObjectList}.
	 * @param ctx the parse tree
	 */
	void exitVerbObjectList(@NotNull N3Parser.VerbObjectListContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#subject}.
	 * @param ctx the parse tree
	 */
	void enterSubject(@NotNull N3Parser.SubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#subject}.
	 * @param ctx the parse tree
	 */
	void exitSubject(@NotNull N3Parser.SubjectContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#verb}.
	 * @param ctx the parse tree
	 */
	void enterVerb(@NotNull N3Parser.VerbContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#verb}.
	 * @param ctx the parse tree
	 */
	void exitVerb(@NotNull N3Parser.VerbContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(@NotNull N3Parser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(@NotNull N3Parser.StatementsContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(@NotNull N3Parser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(@NotNull N3Parser.CollectionContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#classification}.
	 * @param ctx the parse tree
	 */
	void enterClassification(@NotNull N3Parser.ClassificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#classification}.
	 * @param ctx the parse tree
	 */
	void exitClassification(@NotNull N3Parser.ClassificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#objectList}.
	 * @param ctx the parse tree
	 */
	void enterObjectList(@NotNull N3Parser.ObjectListContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#objectList}.
	 * @param ctx the parse tree
	 */
	void exitObjectList(@NotNull N3Parser.ObjectListContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#directive}.
	 * @param ctx the parse tree
	 */
	void enterDirective(@NotNull N3Parser.DirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#directive}.
	 * @param ctx the parse tree
	 */
	void exitDirective(@NotNull N3Parser.DirectiveContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull N3Parser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(@NotNull N3Parser.LiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void enterBlankNodePropertyList(@NotNull N3Parser.BlankNodePropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void exitBlankNodePropertyList(@NotNull N3Parser.BlankNodePropertyListContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#exvar}.
	 * @param ctx the parse tree
	 */
	void enterExvar(@NotNull N3Parser.ExvarContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#exvar}.
	 * @param ctx the parse tree
	 */
	void exitExvar(@NotNull N3Parser.ExvarContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#triple}.
	 * @param ctx the parse tree
	 */
	void enterTriple(@NotNull N3Parser.TripleContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#triple}.
	 * @param ctx the parse tree
	 */
	void exitTriple(@NotNull N3Parser.TripleContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#n3Doc}.
	 * @param ctx the parse tree
	 */
	void enterN3Doc(@NotNull N3Parser.N3DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#n3Doc}.
	 * @param ctx the parse tree
	 */
	void exitN3Doc(@NotNull N3Parser.N3DocContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#verbObject}.
	 * @param ctx the parse tree
	 */
	void enterVerbObject(@NotNull N3Parser.VerbObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#verbObject}.
	 * @param ctx the parse tree
	 */
	void exitVerbObject(@NotNull N3Parser.VerbObjectContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(@NotNull N3Parser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(@NotNull N3Parser.FormulaContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(@NotNull N3Parser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(@NotNull N3Parser.BooleanLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#blankNode}.
	 * @param ctx the parse tree
	 */
	void enterBlankNode(@NotNull N3Parser.BlankNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#blankNode}.
	 * @param ctx the parse tree
	 */
	void exitBlankNode(@NotNull N3Parser.BlankNodeContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#prefixID}.
	 * @param ctx the parse tree
	 */
	void enterPrefixID(@NotNull N3Parser.PrefixIDContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#prefixID}.
	 * @param ctx the parse tree
	 */
	void exitPrefixID(@NotNull N3Parser.PrefixIDContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#base}.
	 * @param ctx the parse tree
	 */
	void enterBase(@NotNull N3Parser.BaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#base}.
	 * @param ctx the parse tree
	 */
	void exitBase(@NotNull N3Parser.BaseContext ctx);

	/**
	 * Enter a parse tree produced by {@link N3Parser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(@NotNull N3Parser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link N3Parser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(@NotNull N3Parser.ObjectContext ctx);
}