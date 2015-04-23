package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * The class SRQuery implements a query interface for the reasoner.
 */
public class Answer extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public Answer(SemanticDataContainer semanticContainer) {
		super("answer");
		this.semanticContainer = semanticContainer;
	}

	@Override
	public void handlePUT(CoapExchange exchange) {
		String data = exchange.getRequestText();
		try {
			N3Document resp = N3Utils.parseN3Document(data);
			semanticContainer.addAnswer(resp);
			exchange.respond(CoAP.ResponseCode.CHANGED);
		} catch (Exception e) {
			exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
		}
	}
}
