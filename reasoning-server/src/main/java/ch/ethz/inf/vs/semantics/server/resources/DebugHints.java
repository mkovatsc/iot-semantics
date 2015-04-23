package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDescription;

import org.apache.commons.io.FileUtils;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class DebugHints extends CoapResource {
	private final SemanticDataContainer semanticContainer;

	public DebugHints(SemanticDataContainer semanticContainer) {
		super("hints");
		this.semanticContainer = semanticContainer;
		getAttributes().addResourceType("debug-hints");
	}

	@Override
	public void handleGET(CoapExchange exchange) {

		HashSet<String> hints = new HashSet<>();

		for (String k : semanticContainer.getActiveKeys()) {
			SemanticDescription sem = semanticContainer.getSemantics().get(k);
			if (sem.isLoaded()) {
				hints.addAll(N3Utils.getHints(sem.getContent()));
			}
		}
		for (String k : semanticContainer.getHardcodedFiles()) {
			try {
				hints.addAll(N3Utils.getHints(FileUtils.readFileToString(new File(k))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			hints.addAll(N3Utils.getHints(FileUtils.readFileToString(semanticContainer.getAnswersFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray jsonHints = new JSONArray();
		for (String hint : hints) {
			jsonHints.put(hint);
		}
		exchange.respond(jsonHints.toString());
	}
}
