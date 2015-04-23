package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.*;

public interface IN3ElementVisitor<T> {
	public T visitBlankNode(BlankNode blankNode);

	T visitBlankNodePropertyList(BlankNodePropertyList verbObjects);

	T visitBooleanLiteral(BooleanLiteral booleanLiteral);

	T vistClassification(Classification classification);

	T vistExvar(Exvar exvar);

	T vistFormula(Formula statements);

	T visitIri(Iri iri);

	T visitLiteral(Literal literal);

	T visitObjectCollection(ObjectCollection objects);

	T visitObjectList(ObjectList objects);

	T visitStatements(Statements statements);

	T visitRDFResource(RDFResource RDFResource);

	T visitVerb(Verb verb);

	T visitVerbObject(VerbObject verbObject);

	T visitN3Document(N3Document n3Document);

	T visitPrefix(Prefix p);
}
