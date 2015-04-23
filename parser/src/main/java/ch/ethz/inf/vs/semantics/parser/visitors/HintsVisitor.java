package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.Iri;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;

import java.util.Set;

public class HintsVisitor extends N3BaseElementVisitor<Void> {

	private Set<String> hints;

	public HintsVisitor(Set<String> hints) {

		this.hints = hints;
	}

	@Override
	public Void visitPrefix(Prefix p) {
		try {
			hints.add(p.toString());

		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public Void visitIri(Iri iri) {
		try {
			hints.add(iri.toString());

		} catch (Exception e) {

		}
		return null;
	}
}