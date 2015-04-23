package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;

import org.eclipse.californium.core.CoapResource;

/**
 * The class SRQuery implements a query interface for the reasoner.
 */
public class SRResource extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public SRResource(SemanticDataContainer semanticContainer) {
		super("sr");
		this.semanticContainer = semanticContainer;
		add(new SRQuery(this.semanticContainer));
		add(new SRMashup(this.semanticContainer));
		getAttributes().addResourceType("sr");
	}
}
