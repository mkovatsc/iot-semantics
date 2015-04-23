package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.Iri;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;

public class ReplacePrefixVisitor extends N3BaseElementVisitor<Void> {

	private Prefix original;
	private Prefix replace;

	public ReplacePrefixVisitor(Prefix original, Prefix replace) {
		this.original = original;
		this.replace = replace;
	}

	@Override
	public Void visitIri(Iri iri) {
		Prefix p = iri.getPrefix();
		if (p == original) {
			iri.setPrefix(replace);
		}
		return null;
	}
}
