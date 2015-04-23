package ch.ethz.inf.vs.semantics.ide;

import com.google.common.base.Optional;
import restx.server.WebServer;
import restx.server.simple.simple.SimpleWebServer;

/**
 * This class can be used to run the app.
 * <p/>
 * Alternatively, you can deploy the app as a war in a regular container like tomcat or jetty.
 * <p/>
 * Reading the port from system env PORT makes it compatible with heroku.
 */
public class IDEServer {
	public static void main(String[] args) throws Exception {
		int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("8080"));
		WebServer server = SimpleWebServer.builder().setRouterPath("").setPort(port).build();

        /*
		 * load mode from system property if defined, or default to prod
         * when using this class to launch your server in development, launch it with -Drestx.mode=dev
         */
		System.setProperty("restx.mode", System.getProperty("restx.mode", "prod"));
		System.setProperty("restx.app.package", "dynamicserver");

		server.startAndAwait();

		System.out.println("VISIT " + server.baseUrl() + "/ for Semantics IDE");
	}
}
