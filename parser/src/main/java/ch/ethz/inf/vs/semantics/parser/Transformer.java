package ch.ethz.inf.vs.semantics.parser;

import ch.ethz.inf.vs.semantics.parser.elements.RDFResource;

import java.util.Map;

public interface Transformer {
	Map<String, Object> transform(Object result, RDFResource param);

	boolean transforms(Object o);
}
