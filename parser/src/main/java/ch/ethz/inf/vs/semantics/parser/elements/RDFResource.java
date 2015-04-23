package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RDFResource implements N3Element.Statement, N3Element, Serializable {
	public N3Element.Subject subject;
	public final ArrayList<VerbObject> verbObjects;

	public RDFResource(N3Element.Subject subject, ArrayList<VerbObject> v) {
		this.subject = subject;
		verbObjects = v;
	}

	public RDFResource(N3Element.Subject bn) {
		subject = bn;
		verbObjects = new ArrayList<VerbObject>();
	}

	public RDFResource add(Verb verb, Object obj) {
		verbObjects.add(new VerbObject(verb, obj));
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(subject);
		sb.append(' ');
		for (int i = 0; i < verbObjects.size(); i++) {
			VerbObject vo = verbObjects.get(i);
			sb.append(vo.toString());
			if (i != verbObjects.size() - 1) {
				sb.append("; ");
			}
		}

		return sb.toString();
	}

	public String getString(String verb) {
		N3Element.Object item = get(verb);
		if (item == null) {
			return null;
		}
		return item.toString();
	}

	public String getReadableString(String verb) {
		N3Element.Object item = get(verb);
		if (item == null) {
			return null;
		}
		return item.toReadableString();
	}

	public N3Element.Object get(String verb) {
		for (VerbObject vo : verbObjects) {
			if (vo.verb.toString().equals(verb)) {
				return vo.object;
			} else if (vo.verb.getGlobalName().equals(verb)) {
				return vo.object;
			}
		}
		return null;
	}

	public List<Object> getAll(String verb) {
		ArrayList<Object> objects = new ArrayList<Object>();
		for (VerbObject vo : verbObjects) {
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
		return b.visitRDFResource(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		elements.add((N3Element) subject);
		elements.addAll(verbObjects);
		return elements;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		if (subject.equals(n)) {
			subject = (Subject) replace;
		}
		N3Utils.replace(verbObjects, n, replace);
	}

}
