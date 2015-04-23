package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.Serializable;

public class Literal implements N3Element.Object, N3Element.Subject, N3Element, Serializable {
	public final boolean string;
	public final String text;

	public Literal(String text) {
		this(text, false);
	}

	public Literal(String text, boolean makeString) {
		if (!makeString) {
			if (text.startsWith("\"") && text.endsWith("\"")) {
				string = true;
				text = text.substring(1, text.length() - 1);
				this.text = StringEscapeUtils.unescapeJava(text);
			} else {
				string = false;
				this.text = text;
			}
		} else {

			string = true;
			this.text = text;
		}
	}

	@Override
	public String toString() {
		if (string) {
			return "\"" + StringEscapeUtils.escapeJava(text) + "\"";
		} else {
			return text;
		}
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitLiteral(this);
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
}
