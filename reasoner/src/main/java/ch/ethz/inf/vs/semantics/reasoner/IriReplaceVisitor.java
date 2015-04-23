package ch.ethz.inf.vs.semantics.reasoner;

import ch.ethz.inf.vs.semantics.parser.elements.Iri;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;
import ch.ethz.inf.vs.semantics.parser.visitors.N3BaseElementVisitor;

public class IriReplaceVisitor extends N3BaseElementVisitor<Void> {

	private String path;

	public IriReplaceVisitor(String path) {
		this.path = path;
	}

	@Override
	public Void visitIri(Iri iri) {

		if (iri.text.startsWith(path)) {
			iri.text = iri.text.substring(path.length());
		}

		return null;
	}

	@Override
	public Void visitPrefix(Prefix p) {

		if (p.getUri().startsWith(path)) {
			p.setUri(p.getUri().substring(path.length()));
		}

		return null;
	}
}
