package ch.ethz.inf.vs.semantics.server.semantics;

import ch.ethz.inf.vs.semantics.server.SemanticsServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.LinkFormat;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.californium.core.server.resources.Resource;

import java.util.*;

public class ResourceDirectorySynchronizationService {

	public static final String DEMO_IP = "[2001:470:cafe::38b2:cf50]";
	private static final Logger logger = LogManager.getLogger();
	private final CoapServer server;
	private final String id = UUID.randomUUID().toString();
	private boolean registered = false;
	private String rdLookupURI;
	SemanticDataContainer semanticDataContainer;

	public ResourceDirectorySynchronizationService(SemanticsServer server, SemanticDataContainer semanticDataContainer) {
		this.server = server;
		this.semanticDataContainer = semanticDataContainer;
		Timer timer = new Timer();
		timer.schedule(new SynchronizationTask(), 10000, 6000);
	}

	/**
	 * Find and process all the semantic resources registered in the resource
	 * directory.
	 */
	private void discoverSemanticResources() {
		String uri = getRDLookupURI();
		if (uri == null) {
			return;
		}
		CoapClient client = createClient();
		client.setURI(uri + "/res?rt=semantics");
		client.get(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				Set<WebLink> resources = Collections.emptySet();
				if (response.getOptions().getContentFormat() == MediaTypeRegistry.APPLICATION_LINK_FORMAT)
					resources = LinkFormat.parse(response.getResponseText());
				processSemanticResources(resources);
			}

			@Override
			public void onError() {
				logger.error("Failed to fetch all the semantic resources from the resource directory");
			}

		});
	}

	/**
	 * Process set of web links referencing semantic resources New resources are
	 * added to the semantics directory. If a resource is no longer present in
	 * the web link set delivered by the resource directory it will no longer be
	 * used for the reasoning queries.
	 *
	 * @param resources
	 */
	private void processSemanticResources(Set<WebLink> resources) {
		HashSet<String> availableKeys = new HashSet<String>();
		for (WebLink l : resources) {
			String key = l.getURI();
			if (!semanticDataContainer.containsSemantics(key)) {
				fetchSemantics(l);
			}
			availableKeys.add(key);
		}

		HashSet<String> inactiveKeys = new HashSet<String>();
		for (String k : semanticDataContainer.getActiveKeys()) {
			if (!availableKeys.contains(k) && !k.equals("SEMSERV")) {
				inactiveKeys.add(k);
			}
		}
		semanticDataContainer.removeInactiveEndpoint(inactiveKeys);
	}

	/**
	 * Fetch a given semantic resource
	 *
	 * @param link
	 *            WebLink referencing the semantic resource
	 */
	private void fetchSemantics(WebLink link) {
		final String uri = link.getURI();
		String endpoint = link.getAttributes().getAttributeValues("ep").iterator().next();
		SemanticDescription dsc = new SemanticDescription(endpoint, uri, semanticDataContainer.getFileContainer());
		// Atomically try to add the semantic description
		SemanticDescription item = semanticDataContainer.putSemanticsIfAbsent(uri, dsc);
		// Get SemanticDescription stored in map
		final SemanticDescription obj = item != null ? item : dsc;
		// Ensure that the object is not already loaded
		if (item != dsc || !(obj.isFetching() || obj.isLoaded())) {
			if (link.getAttributes().hasObservable()) {

				observeSemanticDescription(uri, obj);
			} else {

				fetchSemanticDescription(uri, obj);
			}

		}

	}

	private void fetchSemanticDescription(final String uri, final SemanticDescription obj) {
		CoapClient fetchClient = createClient().setURI(uri);
		fetchClient.get(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				String n3file = response.getResponseText();
				try {
					semanticDataContainer.acquireWriteLock();
					obj.processFile(n3file);
					if (obj.isLoaded()) {
						semanticDataContainer.addActiveEndpoint(uri);
						logger.info("Successfully fetched {}", uri);
					} else {
						logger.error("Failed to fetch {}", uri);
					}
				} finally {
					semanticDataContainer.releaseWriteLock();
				}
			}

			@Override
			public void onError() {
				obj.setFetching(false);
				logger.error("Failed to fetch {}", uri);
			}
		});
	}

	private void observeSemanticDescription(final String uri, final SemanticDescription obj) {
		CoapClient fetchClient = createClient().setURI(uri);
		fetchClient.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				String n3file = response.getResponseText();
				try {
					semanticDataContainer.acquireWriteLock();
					obj.processFile(n3file);
					if (obj.isLoaded()) {
						semanticDataContainer.addActiveEndpoint(uri);
						logger.info("Successfully updated {}", uri);
					} else {
						logger.error("Failed to fetch {}", uri);
					}
				} finally {
					semanticDataContainer.releaseWriteLock();
				}
			}

			@Override
			public void onError() {
				obj.setFetching(false);
				logger.error("Failed to fetch {}", uri);
			}
		}

		);
	}

	/**
	 * Find the uri of the resource of the type core.rd-lookup
	 *
	 * @return
	 */
	private String getRDLookupURI() {
		if (rdLookupURI != null) {
			return rdLookupURI;
		}
		CoapClient c = createClient();
		c.setURI("coap://" + DEMO_IP + ":5683");
		Set<WebLink> resources = c.discover("rt=core.rd-lookup");
		if (resources != null && resources.size() > 0) {
			WebLink w = resources.iterator().next();
			rdLookupURI = "coap://" + DEMO_IP + ":5683" + w.getURI();
		}
		return rdLookupURI;
	}

	/**
	 * Task used to periodically update the semantic files.
	 */
	public class SynchronizationTask extends TimerTask {
		@Override
		public void run() {
			logger.info("Synchronize semantics...");
			// Register server in resource directory.
			if (!registered) {
				registered = true;
				registerSelf();
			}
			discoverSemanticResources();
		}
	}

	/**
	 * Create a CoapClient associated with the current server
	 *
	 * @return
	 */
	private CoapClient createClient() {
		CoapClient client = new CoapClient();
		List<Endpoint> endpoints = server.getEndpoints();
		client.setExecutor(server.getRoot().getExecutor());
		if (!endpoints.isEmpty()) {
			Endpoint ep = endpoints.get(0);
			client.setEndpoint(ep);
		}
		return client;
	}

	private void registerSelf(String uri) {
		CoapClient client = createClient();
		client.setTimeout(5000);
		client.setURI(uri + "?ep=" + id);
		client.post(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				logger.info("Registered");
			}

			@Override
			public void onError() {
				registered = false;
				logger.error("Registration Failed");
			}

		}, discoverTree(), MediaTypeRegistry.APPLICATION_LINK_FORMAT);
	}

	private void registerSelf() {
		CoapClient c = createClient();
		c.setTimeout(5000);
		c.setURI("coap://" + DEMO_IP + ":5683");
		Set<WebLink> resources = c.discover("rt=core.rd");
		if (resources != null) {
			if (resources.size() > 0) {
				WebLink w = resources.iterator().next();
				String uri = "coap://" + DEMO_IP + ":5683" + w.getURI();
				registerSelf(uri);
			}
		} else {
			registered = false;
			logger.info("Discover timeout");
		}
	}

	/**
	 * Get all the resources hosted by this server. (Could be done by accessing
	 * /.well-known/core (COAP Bug))
	 *
	 * @return
	 */
	String discoverTree() {
		StringBuilder buffer = new StringBuilder();
		for (Resource child : server.getRoot().getChildren()) {
			LinkFormat.serializeTree(child, null, buffer);
		}
		// remove last comma ',' of the buffer
		if (buffer.length() > 1)
			buffer.delete(buffer.length() - 1, buffer.length());

		return buffer.toString();
	}
}
