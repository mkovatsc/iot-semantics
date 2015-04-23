package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlankNodePropertyList extends ArrayList<VerbObject> implements N3Element.Object, N3Element.Statement, N3Element.Subject, N3Element, Serializable {
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < size(); i++) {
			VerbObject vo = get(i);
			sb.append(vo.toString());
			if (i != size() - 1) {
				sb.append(";");
			}
		}
		sb.append(']');

		return sb.toString();
	}

	public N3Element.Object get(String verb) {
		for (VerbObject vo : this) {
			if (vo.verb.toString().equals(verb)) {
				return vo.object;
			}
		}
		return null;
	}

	public List<Object> getAll(String verb) {
		ArrayList<Object> objects = new ArrayList<Object>();
		for (VerbObject vo : this) {
			if (vo.verb.toString().equals(verb)) {
				objects.add(vo.object);
			} else if (vo.verb.getGlobalName().equals(verb)) {
				objects.add(vo.object);
			}
		}
		return objects;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitBlankNodePropertyList(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return this;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		N3Utils.replace(this, n, replace);
	}

	@Override
	public String toReadableString() {
		return toString();
	}
}
