package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

public interface N3Element {

	static public interface Verb {

		public String getGlobalName();
	}

	static public interface Subject {

	}

	static public interface Object {
		String toReadableString();

	}

	static public interface Statement {
	}

	public <T> T accept(IN3ElementVisitor<T> b);

	public Iterable<? extends N3Element> getChildern();

	void replace(N3Element n, N3Element replace);
}