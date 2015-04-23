// Generated from antlr4\N3.g4 by ANTLR 4.3
package ch.ethz.inf.vs.semantics.parser.notation3;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link N3Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface N3Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link N3Parser#iri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIri(@NotNull N3Parser.IriContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#verbObjectList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerbObjectList(@NotNull N3Parser.VerbObjectListContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#subject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubject(@NotNull N3Parser.SubjectContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#verb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerb(@NotNull N3Parser.VerbContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(@NotNull N3Parser.StatementsContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#collection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollection(@NotNull N3Parser.CollectionContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#classification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassification(@NotNull N3Parser.ClassificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#objectList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectList(@NotNull N3Parser.ObjectListContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirective(@NotNull N3Parser.DirectiveContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull N3Parser.LiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNodePropertyList(@NotNull N3Parser.BlankNodePropertyListContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#exvar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExvar(@NotNull N3Parser.ExvarContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#triple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriple(@NotNull N3Parser.TripleContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#n3Doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitN3Doc(@NotNull N3Parser.N3DocContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#verbObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerbObject(@NotNull N3Parser.VerbObjectContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(@NotNull N3Parser.FormulaContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull N3Parser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#blankNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNode(@NotNull N3Parser.BlankNodeContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#prefixID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixID(@NotNull N3Parser.PrefixIDContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase(@NotNull N3Parser.BaseContext ctx);

	/**
	 * Visit a parse tree produced by {@link N3Parser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(@NotNull N3Parser.ObjectContext ctx);
}