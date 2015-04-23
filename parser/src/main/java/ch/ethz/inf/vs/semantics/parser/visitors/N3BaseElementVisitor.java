package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.*;

public class N3BaseElementVisitor<T> implements IN3ElementVisitor<T> {

	public T visitChildern(N3Element node) {
		T result = defaultResult();
		Iterable<? extends N3Element> childern = node.getChildern();
		if (childern != null) {
			for (N3Element n : childern) {
				T cResult = n.accept(this);
				result = aggregateResult(result, cResult);
			}
		}
		return result;
	}

	public T aggregateResult(T result, T cResult) {
		return cResult;
	}

	public T defaultResult() {
		return null;
	}

	@Override
	public T visitBlankNode(BlankNode blankNode) {
		return visitChildern(blankNode);
	}

	@Override
	public T visitBlankNodePropertyList(BlankNodePropertyList verbObjects) {
		return visitChildern(verbObjects);
	}

	@Override
	public T visitBooleanLiteral(BooleanLiteral booleanLiteral) {
		return visitChildern(booleanLiteral);
	}

	@Override
	public T vistClassification(Classification classification) {
		return visitChildern(classification);
	}

	@Override
	public T vistExvar(Exvar exvar) {
		return visitChildern(exvar);
	}

	@Override
	public T vistFormula(Formula statements) {
		return visitChildern(statements);
	}

	@Override
	public T visitIri(Iri iri) {
		return visitChildern(iri);
	}

	@Override
	public T visitLiteral(Literal literal) {
		return visitChildern(literal);
	}

	@Override
	public T visitObjectCollection(ObjectCollection objects) {
		return visitChildern(objects);
	}

	@Override
	public T visitObjectList(ObjectList objects) {
		return visitChildern(objects);
	}

	@Override
	public T visitStatements(Statements statements) {
		return visitChildern(statements);
	}

	@Override
	public T visitRDFResource(RDFResource RDFResource) {
		return visitChildern(RDFResource);
	}

	@Override
	public T visitVerb(Verb verb) {
		return visitChildern(verb);
	}

	@Override
	public T visitVerbObject(VerbObject verbObject) {
		return visitChildern(verbObject);
	}

	@Override
	public T visitN3Document(N3Document n3Document) {

		return visitChildern(n3Document);
	}

	@Override
	public T visitPrefix(Prefix p) {
		return visitChildern(p);
	}
}
