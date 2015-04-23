package ch.ethz.inf.vs.semantics.parser.elements;

import ch.ethz.inf.vs.semantics.parser.visitors.IN3ElementVisitor;
import ch.ethz.inf.vs.semantics.parser.visitors.ReplacePrefixVisitor;
import ch.ethz.inf.vs.semantics.parser.visitors.UsedPrefixesElementVisitor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class N3Document implements N3Element, Serializable {
	public final HashMap<String, Prefix> prefixes;
	public final HashMap<String, Prefix> prefixes_uri;
	public Statements statements;

	public N3Document() {

		this.statements = new Statements();
		this.prefixes = new HashMap<String, Prefix>();
		this.prefixes_uri = new HashMap<String, Prefix>();
	}

	public void importStatements(Statements s) {
		s = importToDocument(s);
		this.statements.addAll(s);
	}

	public Prefix getPrefixByUri(String uri) {
		return prefixes_uri.get(uri);
	}

	public <T extends N3Element> T importToDocument(T s) {
		s = deepCopy(s);
		Set<Prefix> usedPrefixes = s.accept(new UsedPrefixesElementVisitor());
		for (Prefix prefix : usedPrefixes) {
			// Prefix with same uri exists
			if (prefixes_uri.containsKey(prefix.getUri())) {
				// replace prefix with existing prefix
				Prefix other = prefixes_uri.get(prefix.getUri());
				s.accept(new ReplacePrefixVisitor(prefix, other));
			} else if (prefixes.containsKey(prefix.getName())) {
				Prefix other = prefixes.get(prefix.getName());
				// prefix has the same url
				if (other.getUri().equals(prefix.getUri())) {
					// replace prefix with existing prefix
					s.accept(new ReplacePrefixVisitor(prefix, other));
				} else {
					int index = 1;
					while (prefixes.containsKey("ns" + index + ":")) {
						index++;
					}
					prefix.setName("ns" + index + ":");
					prefixes.put(prefix.getName(), prefix);
					prefixes_uri.put(prefix.getUri(), prefix);
				}

			} else {
				prefixes.put(prefix.getName(), prefix);
				prefixes_uri.put(prefix.getUri(), prefix);
			}
		}
		return s;
	}

	private <T> T deepCopy(T s) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(s);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			return (T) new ObjectInputStream(bais).readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Can not copy objects");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Prefix prefix : prefixes.values()) {
			sb.append("@prefix ");
			sb.append(prefix.getName());
			sb.append(" <");
			sb.append(prefix.getUri());
			sb.append(">.\n");
		}
		sb.append("\n");
		for (N3Element.Statement s : statements) {
			sb.append(s.toString());
			sb.append(".\n\n");
		}

		return sb.toString();
	}

	@Override
	public <T> T accept(IN3ElementVisitor<T> b) {
		return b.visitN3Document(this);
	}

	@Override
	public Iterable<? extends N3Element> getChildern() {
		ArrayList<N3Element> elements = new ArrayList<N3Element>();
		elements.addAll(prefixes.values());
		elements.add(statements);
		return elements;
	}

	@Override
	public void replace(N3Element n, N3Element replace) {
		if (statements.equals(n)) {
			statements = (Statements) replace;
		}
		// Prefix not supported
	}

	public void addPrefix(Prefix prefix) {
		prefixes.put(prefix.getName(), prefix);
		prefixes_uri.put(prefix.getUri(), prefix);
	}

	public Prefix getPrefix(String prefix) {
		return prefixes.get(prefix + ":");
	}

}
