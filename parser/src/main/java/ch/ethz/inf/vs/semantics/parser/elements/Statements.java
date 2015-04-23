package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Statements extends ArrayList<N3Element.Statement> implements N3Element, Serializable {

	protected final Map<String, RDFResource> tripleMap;

	public Statements() {
		tripleMap = new HashMap<String, RDFResource>();
	}

	@Override
	public boolean add(N3Element.Statement statement) {
		if (statement instanceof RDFResource) {
			N3Element.Subject subject = ((RDFResource) statement).subject;
			if (subject instanceof Iri || subject instanceof Exvar) {
				if (tripleMap.containsKey(subject.toString())) {
					tripleMap.get(subject.toString()).verbObjects.addAll(((RDFResource) statement).verbObjects);
					return true;
				} else {
					tripleMap.put(subject.toString(), (RDFResource) statement);
				}
			}
		}
		return super.add(statement);
	}

	@Override
	public boolean addAll(Collection<? extends N3Element.Statement> c) {
		for (N3Element.Statement s : c) {
			add(s);
		}
		return true;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitStatements(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		for (Statement s : this) {
			elements.add((N3Element) s);

		}
		return elements;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		N3Utils.replace(this, n, replace);
	}
}
