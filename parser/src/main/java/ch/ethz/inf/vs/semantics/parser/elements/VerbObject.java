package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class VerbObject implements N3Element, Serializable {
	public N3Element.Verb verb;
	public N3Element.Object object;

	public VerbObject(N3Element.Verb v, N3Element.Object o) {
		verb = v;
		object = o;
	}

	@Override
	public String toString() {
		return verb.toString() + " " + object.toString();
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitVerbObject(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		elements.add((N3Element) verb);
		elements.add((N3Element) object);
		return elements;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		if (verb.equals(n)) {
			verb = (Verb) replace;
		}
		if (object.equals(n)) {
			object = (Object) replace;
		}
	}
}
