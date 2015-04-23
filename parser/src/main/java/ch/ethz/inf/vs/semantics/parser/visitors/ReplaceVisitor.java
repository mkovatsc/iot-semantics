package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.N3Element;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;

public class ReplaceVisitor extends N3BaseElementVisitor<Void> {

	private N3Element original;
	private N3Element replace;

	public ReplaceVisitor(N3Element original, N3Element replace) {
		if (replace instanceof Prefix || original instanceof Prefix) {
			throw new RuntimeException("Prefix Replace not supported");
		}
		this.original = original;
		this.replace = replace;
	}

	public Void visitChildern(N3Element node) {
		Iterable<? extends N3Element> childern = node.getChildern();
		if (childern != null) {
			for (N3Element n : childern) {
				if (n.equals(original)) {

					node.replace(n, replace);
				} else {
					n.accept(this);
				}
			}
		}
		return null;
	}
}