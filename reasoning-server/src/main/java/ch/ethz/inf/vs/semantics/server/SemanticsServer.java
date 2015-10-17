package ch.ethz.inf.vs.semantics.server;

import ch.ethz.inf.vs.semantics.server.resources.Answer;
import ch.ethz.inf.vs.semantics.server.resources.Debug;
import ch.ethz.inf.vs.semantics.server.resources.SRResource;
import ch.ethz.inf.vs.semantics.server.semantics.ResourceDirectorySynchronizationService;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoAPEndpoint;
import org.eclipse.californium.core.network.EndpointManager;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The class SemanticReasoner provides a first implementation of a semantic reasoner.
 */
public class SemanticsServer extends CoapServer {

	private static final Logger logger = LogManager.getLogger();

	// exit codes for runtime errors
	private static final int ERR_INIT_FAILED = 1;

	public static void main(String[] args) {
		try {
			SemanticsServer server = new SemanticsServer();
			server.addEndpoint(new CoAPEndpoint(new InetSocketAddress("2001:0470:cafe::38b2:cf50", 5681)));
			server.start();

			logger.info("Semantics-Server listening on port {}.\n", server.getEndpoints().get(0).getAddress().getPort());
		} catch (Throwable t) {
			logger.catching(t);
			System.exit(ERR_INIT_FAILED);
		}
	}

	SemanticsServer(int... ports) {
		super(ports);
		try {
			SemanticDataContainer semanticContainer = new SemanticDataContainer();
			new ResourceDirectorySynchronizationService(this, semanticContainer);
			// add resources to the server
			add(new SRResource(semanticContainer));
			add(new Answer(semanticContainer));
			add(new Debug(semanticContainer));
		} catch (IOException e) {
			logger.catching(e);
		}
	}

}