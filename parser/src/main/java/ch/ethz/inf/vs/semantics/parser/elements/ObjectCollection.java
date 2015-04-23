package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectCollection extends ArrayList<N3Element.Object> implements N3Element.Object, N3Element.Subject, N3Element, Serializable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (int i = 0; i < size(); i++) {
			N3Element.Object vo = get(i);
			sb.append(vo.toString());
			if (i != size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(')');

		return sb.toString();
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitObjectCollection(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		for (N3Element.Object o : this) {
			elements.add((N3Element) o);

		}
		return elements;
	}

	@Override
	public String toReadableString() {
		StringBuilder sb = new StringBuilder();
		for (N3Element.Object vo : this) {
			sb.append(vo.toReadableString());
		}

		return sb.toString();
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		N3Utils.replace(this, n, replace);
	}
}
