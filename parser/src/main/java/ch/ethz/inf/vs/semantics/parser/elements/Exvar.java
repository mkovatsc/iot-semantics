package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class Exvar implements N3Element.Statement, N3Element.Object, N3Element.Subject, N3Element, Serializable {
	private final String text;

	public Exvar(String text) {
		this.text = text;
		assert (text.startsWith("?"));

	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.vistExvar(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return null;
	}

	@Override
	public String toReadableString() {
		return toString();
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		throw new RuntimeException("Unexpected call");
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (obj instanceof Exvar) {
			return text.equals(((Exvar) obj).text);
		}
		return super.equals(obj);
	}
}
