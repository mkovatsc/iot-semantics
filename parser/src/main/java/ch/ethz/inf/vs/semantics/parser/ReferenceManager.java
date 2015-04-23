package ch.ethz.inf.vs.semantics.parser;

import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.parser.elements.N3Element;
import ch.ethz.inf.vs.semantics.parser.elements.RDFResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferenceManager {
	private N3Document related;
	private Map<String, Object> references;
	private List<Transformer> transformers;

	public ReferenceManager() {
		references = new HashMap<String, Object>();
		transformers = new ArrayList<Transformer>();
	}

	public void setRelated(String related) {
		this.related = N3Utils.parseN3Document(related);
	}

	public void set(String r, Object result) {
		references.put(r, result);
		if (related != null) {
			for (N3Element.Statement s : related.statements) {
				if (s instanceof RDFResource && ((RDFResource) s).subject.toString().equals(r)) {
					process(result, (RDFResource) s);
					return;
				}
			}
		}
	}

	private <T> void process(T result, RDFResource parameters) {
		for (Transformer t : transformers) {
			if (t.transforms(result)) {
				Map<String, Object> results = t.transform(result, parameters);
				if (results != null) {
					for (Map.Entry<String, Object> entry : results.entrySet()) {
						set(entry.getKey(), entry.getValue());
					}
				}
				return;
			}
		}
	}

	public void addTransformer(Transformer t) {
		transformers.add(t);
	}

	public String get(String ref) {
		if (!references.containsKey(ref)) {
			return "";
		}
		return references.get(ref).toString();
	}
}
