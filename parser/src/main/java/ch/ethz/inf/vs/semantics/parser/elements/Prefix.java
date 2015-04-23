package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class Prefix implements N3Element, Serializable {
	private String name;
	private String uri;

	public Prefix(String name, String uri) {
		this.name = name;
		this.uri = uri.substring(1, uri.length() - 1);
	}

	public String getName() {
		return name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitPrefix(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return null;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		throw new RuntimeException("Unexpected call");
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("@prefix ");
		sb.append(getName());
		sb.append(" <");
		sb.append(getUri());
		sb.append(">.");
		return sb.toString();
	}
}
