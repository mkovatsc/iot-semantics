package ch.ethz.inf.vs.semantics.server.semantics;

import ch.ethz.inf.vs.semantics.parser.elements.*;
import ch.ethz.inf.vs.semantics.parser.visitors.UsedPrefixesElementVisitor;
import ch.ethz.inf.vs.semantics.reasoner.ExtractedRequest;
import ch.ethz.inf.vs.semantics.reasoner.Reasoner;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.MapMaker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The class SemanticDataContainer keeps track of the semantic description of the devices.
 */
public class SemanticDataContainer {
	private static final Logger logger = LogManager.getLogger();
	private final Path fileContainer;
	private final File answersFile;
	private boolean answersFileActive;
	private ConcurrentMap<String, SemanticDescription> semantics;
	private HashMultimap<String, N3Document> answers;
	private final Set<String> activeKeys;
	private final Set<String> hardcodedFiles;
	private final ReentrantReadWriteLock fLock = new ReentrantReadWriteLock();
	private final Lock fReadLock = fLock.readLock();
	private final Lock fWriteLock = fLock.writeLock();

	public SemanticDataContainer() throws IOException {
		hardcodedFiles = new HashSet<>();
		fileContainer = Files.createTempDirectory("semantics_");
		fileContainer.toFile().deleteOnExit();
		semantics = new MapMaker().concurrencyLevel(4).makeMap();
		activeKeys = new HashSet<>();
		answers = HashMultimap.create();
		copyHardcodedSemantics();
		answersFile = File.createTempFile("answers", ".n3", fileContainer.toFile());
	}

	public Path getFileContainer() {
		return fileContainer;
	}

	private void copyHardcodedSemantics() {
		try {
			InputStream[] files = new InputStream[] { getClass().getResourceAsStream("/states.n3") };
			for (InputStream file : files) {
				File temp = File.createTempFile("hardcoded", ".n3", fileContainer.toFile());
				Files.copy(file, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
				hardcodedFiles.add(temp.getAbsolutePath());
			}
			InputStream questions = getClass().getResourceAsStream("/question.n3");
			// UGLY Hack needed since the server can not access his own
			// resources (COAP bug)
			SemanticDescription desc = new SemanticDescription("SEMSERV", "coap://localhost:5681", fileContainer);
			desc.processFile(IOUtils.toString(questions));
			semantics.put("SEMSERV", desc);
			activeKeys.add("SEMSERV");
		} catch (Exception e) {
			logger.catching(e);
		}
	}

	public void addActiveEndpoint(String ep) {
		activeKeys.add(ep);
		updateAnswerFile();
	}

	public void removeInactiveEndpoint(Collection<String> inactiveKeys) {
		activeKeys.removeAll(inactiveKeys);
		updateAnswerFile();
	}

	public void removeActiveEndpoint(String ep) {
		activeKeys.remove(ep);
		updateAnswerFile();
	}

	public void addSemanticDescription(SemanticDescription sd) {
		semantics.put(sd.getEndpoint(), sd);
	}

	public void removeSemanticDescription(SemanticDescription sd) {

		semantics.remove(sd.getEndpoint());
	}

	public void executeQuery(String goal, String input, boolean raw, SemanticQueryHandler semanticQueryHandler) {
		try {
			String r = executeQuery(goal, input, raw);

			semanticQueryHandler.onResult(r);
		} catch (Exception e) {

			semanticQueryHandler.onError(e.getMessage());
		}

	}

	public void clearAnswers() {
		answers.clear();
		updateAnswerFile();
	}

	public String executeQuery(String goal, String input, boolean raw) throws Exception {
		// Todo: Async
		File inputFile = null;
		File temp = null;
		String r;
		try {
			fReadLock.lock();
			inputFile = File.createTempFile("input", ".n3", fileContainer.toFile());
			if (input == null) {
				input = "";
			}
			FileUtils.writeStringToFile(inputFile, input);

			temp = File.createTempFile("goal", ".n3", fileContainer.toFile());
			FileUtils.writeStringToFile(temp, goal);
			List<String> uris = new ArrayList<String>();
			for (String k : getActiveKeys()) {
				SemanticDescription sem = semantics.get(k);
				if (sem.isLoaded()) {
					uris.add(sem.getFilePath());
				}
			}
			for (String k : hardcodedFiles) {
				uris.add(k);

			}
			uris.add(inputFile.getAbsolutePath());
			if (answersFileActive) {
				uris.add(answersFile.getAbsolutePath());
			}
			if (raw) {
				r = Reasoner.doProof(fileContainer.toFile().getAbsolutePath(), uris, temp.getAbsolutePath(), false, false);
			} else {
				N3Document result = Reasoner.proofGoal(fileContainer.toFile().getAbsolutePath(), uris, temp.getAbsolutePath(), false, true);
				for(Prefix prefix: result.getPrefixes().values()){
					if(prefix.getUri().startsWith("file://")){
						String[] parts = prefix.getUri().split("/");
						prefix.setUri(parts[parts.length-1]);
					}
				}
				BlankNodePropertyList bn = null;
				for (N3Element.Statement k : result.statements) {
					if (k instanceof BlankNodePropertyList) {
						bn = (BlankNodePropertyList) k;
						break;
					}
				}
				if (bn != null) {
					N3Document d = new N3Document();
					d.importStatements((Statements) bn.get("r:gives"));
					r = d.toString();
				} else {
					r = result.toString();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fReadLock.unlock();
			if (inputFile != null) {
				inputFile.delete();
			}
			if (temp != null) {
				temp.delete();
			}

		}
		return r;
	}

	public void planMashup(String goal, String input, SemanticQueryHandler semanticQueryHandler) {
		try {
			String r = planMashup(goal, input);
			semanticQueryHandler.onResult(r);
		} catch (Exception e) {

			semanticQueryHandler.onError(e.getMessage());
		}

	}

	public String planMashup(String goal, String input) throws Exception {
		// Todo: Async
		File inputFile = null;
		File temp = null;
		String r;
		try {
			fReadLock.lock();
			inputFile = File.createTempFile("input", ".n3", fileContainer.toFile());
			if (input == null) {
				input = "";
			}
			FileUtils.writeStringToFile(inputFile, input);

			temp = File.createTempFile("goal", ".n3", fileContainer.toFile());
			FileUtils.writeStringToFile(temp, goal);
			List<String> uris = new ArrayList<String>();
			for (String k : getActiveKeys()) {
				SemanticDescription sem = semantics.get(k);
				if (sem.isLoaded()) {
					uris.add(sem.getFilePath());
				}
			}
			for (String k : hardcodedFiles) {
				uris.add(k);

			}
			uris.add(inputFile.getAbsolutePath());
			if (answersFileActive) {
				uris.add(answersFile.getAbsolutePath());
			}
			Set<ExtractedRequest> results = Reasoner.getExecutionPlan(fileContainer.toFile().getAbsolutePath(), uris, temp.getAbsolutePath());

			r = Reasoner.serializeExecutionPlan(results).toString();

		} catch (Exception e) {
			throw e;
		} finally {
			fReadLock.unlock();
			if (inputFile != null) {
				inputFile.delete();
			}
			if (temp != null) {
				temp.delete();
			}
		}
		return r;
	}

	public void addAnswer(N3Document resp) {
		Set<Prefix> prefixes = new UsedPrefixesElementVisitor().visitN3Document(resp);
		// Associate the received document with all the referenced device local
		// namespaces.
		for (Prefix p : prefixes) {
			String uri = p.getUri();
			uri = uri.substring(uri.lastIndexOf('/') + 1);
			if (uri.startsWith("device_") && uri.endsWith("#")) {
				String endpoint = uri.substring("device_".length(), uri.length() - 1);
				answers.put(endpoint, resp);
			}
		}
		updateAnswerFile();
	}

	/**
	 * Regenerate the answer file by concatenating all the received statements
	 */
	private void updateAnswerFile() {
		Set<N3Document> docs = new HashSet<N3Document>();

		for (String k : answers.keys()) {
			docs.addAll(answers.get(k));
		}
		N3Document doc = new N3Document();
		for (N3Document d : docs) {
			doc.importStatements(d.statements);
		}

		try {
			synchronized (answersFile) {
				answersFileActive = docs.size() > 0;
				FileUtils.writeStringToFile(answersFile, doc.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ConcurrentMap<String, SemanticDescription> getSemantics() {
		return semantics;
	}

	public Set<String> getActiveKeys() {
		return activeKeys;
	}

	public Set<String> getHardcodedFiles() {
		return hardcodedFiles;
	}

	public File getAnswersFile() {
		return answersFile;
	}

	public boolean containsSemantics(String key) {
		return semantics.containsKey(key);
	}

	public SemanticDescription putSemanticsIfAbsent(String uri, SemanticDescription dsc) {
		return semantics.putIfAbsent(uri, dsc);
	}

	public void acquireWriteLock() {
		fWriteLock.lock();
	}

	public void releaseWriteLock() {
		fWriteLock.unlock();
	}
}
