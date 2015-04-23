package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class BooleanLiteral implements N3Element.Object, N3Element.Subject, N3Element.Statement, N3Element, Serializable {
	private final String text;

	public BooleanLiteral(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitBooleanLiteral(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return null;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		throw new RuntimeException("Unexpected call");
	}

	@Override
	public String toReadableString() {
		return text;
	}
}
