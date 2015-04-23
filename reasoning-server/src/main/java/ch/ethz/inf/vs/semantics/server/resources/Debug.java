package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;

import org.eclipse.californium.core.CoapResource;

public class Debug extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public Debug(SemanticDataContainer semanticContainer) {
		super("debug");
		this.semanticContainer = semanticContainer;
		getAttributes().addResourceType("debug-interface");
		add(new DebugDevices(this.semanticContainer));
		add(new DebugHints(this.semanticContainer));
	}

}
