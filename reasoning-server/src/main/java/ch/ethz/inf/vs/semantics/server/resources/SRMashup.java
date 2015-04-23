package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticQueryHandler;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

class SRMashup extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public SRMashup(SemanticDataContainer semanticContainer) {
		super("mashup");
		this.semanticContainer = semanticContainer;
		getAttributes().addResourceType("sr-mashup");
	}

	@Override
	public void handlePOST(final CoapExchange exchange) {
		exchange.accept();
		String[] data = exchange.getRequestText().split("\n###########*\n");
		String goal = data[0];
		String input = null;
		if (data.length > 1) {
			input = data[1];
		}
		semanticContainer.planMashup(goal, input, new SemanticQueryHandler() {

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
