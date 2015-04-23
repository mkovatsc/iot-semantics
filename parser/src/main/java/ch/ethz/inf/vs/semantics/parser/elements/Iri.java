package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import java.io.Serializable;

public class Iri implements N3Element.Object, N3Element.Verb, N3Element.Subject, N3Element, Serializable {
	public final boolean uriref;
	private Prefix prefix;
	public String text;

	public Iri(N3Document doc, String text) {

		if (text.startsWith("<") && text.endsWith(">")) {
			uriref = true;
			this.text = text.substring(1, text.length() - 1);
		} else {
			uriref = false;
			assert (text.split(":").length == 2);
			String[] parts = text.split(":");
			this.prefix = doc.getPrefix(parts[0]);
			assert (this.prefix != null);
			this.text = parts[1];
		}

	}

	@Override
	public String toString() {
		if (uriref) {
			return "<" + text + ">";
		}
		return this.prefix.getName() + text;
	}

	public String getGlobalName() {
		if (uriref) {
			return "<" + text + ">";
		}
		return this.prefix.getUri() + text;
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitIri(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		return null;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		throw new RuntimeException("Unexpected call");
	}

	@Override
	public String toReadableString() {
		return text;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}
}
