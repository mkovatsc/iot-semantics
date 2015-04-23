package ch.ethz.inf.vs.semantics.ide;

import com.google.common.collect.ImmutableMap;
import restx.ResourcesRoute;
import restx.factory.Module;
import restx.factory.Provides;

@Module
public class AppModule {

	@Provides(priority = 2000)
	// We set priority to 2000 to make sure static assets are matched only if no other route is matched.
	public ResourcesRoute assetsRoute() {
		return new ResourcesRoute(
				"assets", // route name, for log and debug only
				"/",      // assets are served on / HTTP path
				"assets", // assets are searched in /assets directory in classpath (src/main/resources/assets in sources)
				ImmutableMap.of("", "index.html") // alias empty string to index.html, so asking for / will look for /index.html
		);
	}

}
