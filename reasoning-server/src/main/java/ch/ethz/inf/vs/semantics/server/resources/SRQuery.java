package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticQueryHandler;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

class SRQuery extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public SRQuery(SemanticDataContainer semanticContainer) {
		super("query");
		this.semanticContainer = semanticContainer;
		getAttributes().addResourceType("sr-query");
	}

	@Override
	public void handlePOST(final CoapExchange exchange) {
		exchange.accept();
		boolean raw = exchange.getRequestOptions().getUriQuery().contains("raw=true");

		String[] data = exchange.getRequestText().split("\n###########*\n");
		String goal = data[0];
		String input = null;
		if (data.length > 1) {
			input = data[1];
		}
		semanticContainer.executeQuery(goal, input, raw, new SemanticQueryHandler() {

			@Override
			public void onResult(String result) {
				exchange.respond(result);
			}

			@Override
			public void onError(String result) {
				exchange.respond(CoAP.ResponseCode.BAD_REQUEST, result);
			}
		});
	}
}
