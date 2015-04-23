package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class Verb implements N3Element.Verb, N3Element, Serializable {
	private final String text;

	public Verb(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitVerb(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return null;
	}

	@Override
	public String getGlobalName() {
		if (text.equals("a")) {
			return "http://www.w3.org/2000/10/swap/log#implies";
		} else if (text.equals("=")) {
			return "http://www.w3.org/2002/07/owl#sameAs";
		} else if (text.equals("=>")) {
			return "http://www.w3.org/2000/10/swap/log#implies";
		}
		return null;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		throw new RuntimeException("Unexpected call");
	}

}
