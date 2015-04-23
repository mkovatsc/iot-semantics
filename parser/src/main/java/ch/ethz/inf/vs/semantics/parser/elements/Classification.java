package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class Classification implements N3Element.Statement, N3Element, Serializable {
	private String classification;
	private N3Element.Object objects;
	private Statements statements;

	public Classification(String classification, N3Element.Object objects, Statements statements) {

		this.classification = classification;
		this.objects = objects;
		this.statements = statements;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.vistClassification(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		elements.add((N3Element) objects);
		elements.add(statements);
		return elements;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		if (objects.equals(n)) {
			objects = (Object) replace;
		}
		if (statements.equals(n)) {
			statements = (Statements) replace;
		}
	}
}
