package ch.ethz.inf.vs.semantics.server.semantics;

import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class SemanticDescription {
	private static final Logger logger = LogManager.getLogger();
	private final String endpoint;
	private final String uri;
	private final File temp;
	private boolean fetching;
	private String file;
	boolean loaded;
	private String content;
	private String originalContent;

	public SemanticDescription(String endpoint, String uri, Path fileContainer) {
		this.fetching = true;
		this.endpoint = endpoint;
		this.uri = uri;
		File file = null;
		try {
			file = File.createTempFile("temp-file-name", ".n3", fileContainer.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp = file;
		this.file = temp != null ? temp.getAbsolutePath() : null;
		loaded = false;
	}

	public void setFetching(boolean fetching) {
		this.fetching = fetching;
	}

	public boolean isFetching() {
		return fetching;
	}

	public String processFile(String n3file) {
		originalContent = n3file;
		// Parse and adjust namespace.
		N3Document doc = N3Utils.parseN3Document(n3file);
		String oldPrefix = "local:";
		Prefix localprefix = doc.getPrefixByUri("local#");
		if (localprefix != null) {
			oldPrefix = localprefix.getName();
		} else {
			n3file = "@prefix local: <local#>.\n" + n3file;
		}
		String newPrefix = "device_" + endpoint + ":";
		String namespace = "device_" + endpoint + "#";
		URI parsedUri;
		try {
			parsedUri = new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Invalid uri");
		}
		String host = parsedUri.getHost();
		if (host.equals("127.0.1.1")) {
			host = "127.0.0.1";
		}
		n3file += "\n" + "<coap://" + host + ":" + parsedUri.getPort() + "> a local:url.";
		String newFile = N3Utils.replaceN3Prefix(n3file, oldPrefix, newPrefix, namespace);
		loadFile(newFile);
		return newFile;
	}

	private void loadFile(String newFile) {
		try {
			content = newFile;
			FileUtils.writeStringToFile(temp, newFile);
			loaded = true;
		} catch (IOException e) {
			logger.catching(e);
		}
		setFetching(false);
	}

	public boolean isLoaded() {
		return loaded;
	}

	public String getFilePath() {
		return file;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getContent() {
		return content == null ? "" : content;
	}

	public String getOriginalContent() {
		return originalContent == null ? "" : originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}
}
