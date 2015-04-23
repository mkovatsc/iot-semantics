package ch.ethz.inf.vs.semantics.server.resources;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDescription;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DebugDevices extends CoapResource {
	private final SemanticDataContainer semanticContainer;
	private int ID;
	private ConcurrentHashMap<String, Integer> nameToId;

	public DebugDevices(SemanticDataContainer semanticContainer) {
		super("devices");
		this.semanticContainer = semanticContainer;
		this.nameToId = new ConcurrentHashMap<>();
		getAttributes().addResourceType("debug-devices");
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		JSONArray serializedDevices = new JSONArray();
		for (Map.Entry<String, SemanticDescription> item : semanticContainer.getSemantics().entrySet()) {
			JSONObject serializedDescription = new JSONObject();
			String key = item.getKey();
			if (!key.startsWith("coap://"))
				continue;
			if (!nameToId.containsKey(key)) {
				nameToId.putIfAbsent(key, ++ID);
			}
			String semantics = item.getValue().getOriginalContent();
			String name = key;
			if (semantics.startsWith("#")) {
				name = semantics.split("\n")[0].substring(1).trim();
			}
			serializedDescription.put("id", nameToId.get(key));
			serializedDescription.put("name", name);
			serializedDescription.put("url", key);
			serializedDescription.put("remote", true);
			serializedDescription.put("semantics", semantics);
			serializedDescription.put("processed", item.getValue().getContent());
			serializedDescription.put("disabled", !semanticContainer.getActiveKeys().contains(key));
			serializedDevices.put(serializedDescription);
		}
		exchange.respond(serializedDevices.toString());
	}
}
