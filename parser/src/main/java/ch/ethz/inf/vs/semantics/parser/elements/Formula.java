package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class Formula extends Statements implements N3Element.Object, N3Element.Subject, N3Element, Serializable {

	public RDFResource get(String name) {
		return tripleMap.get(name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (N3Element.Statement s : this) {
			sb.append(s.toString());
			sb.append(". ");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.vistFormula(this);
	}

	@Override
	public String toReadableString() {
		return toString();
	}

}
