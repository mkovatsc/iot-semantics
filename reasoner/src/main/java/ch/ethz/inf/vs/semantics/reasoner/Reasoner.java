package ch.ethz.inf.vs.semantics.reasoner;

import ch.ethz.inf.vs.semantics.parser.N3TransformationVisitor;
import ch.ethz.inf.vs.semantics.parser.elements.Iri;
import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.parser.elements.N3Element;
import ch.ethz.inf.vs.semantics.parser.elements.RDFResource;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Lexer;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Parser;
import euler.ProofEngine;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Reasoner {

	public static Set<ExtractedRequest> getExecutionPlan(List<String> ruleURIs, String goal) {
		N3Document parsedProof = proofGoal(ruleURIs, goal, true);
		HashMap<String, Lemma> lemmas = extractLemmas(parsedProof);
		// Get first lemma
		Lemma lemma1 = lemmas.get("<#lemma1>");
		if (lemma1 != null) {
			Set<ExtractedRequest> requests = lemma1.getRequests();
			lemma1.setLevel(0);
			return requests;
		}
		return null;
	}

	public static N3Document proofGoal(List<String> ruleURIs, String goal, boolean quick_answer) {
		String proof;
		proof = doProof(ruleURIs, goal, quick_answer) + "\n\n";
		N3Parser.N3DocContext tdc = parseProof(proof);
		N3Document doc = transformParseTree(tdc);
		String path = "file://" + new File(new File(goal).getParent()).getAbsolutePath() + "/";
		doc.accept(new IriReplaceVisitor(path));
		return doc;
	}

	public static JSONObject serializeExecutionPlan(Set<ExtractedRequest> requests) {
		JSONObject plan = new JSONObject();
		JSONArray requestslist = new JSONArray();
		N3Document related = null;
		for (ExtractedRequest r : requests) {
			requestslist.put(r.toJSON());
			N3Document related_triples = r.getRelated();

			if (related_triples != null) {

				if (related != null) {
					related.importStatements(related_triples.statements);
				} else {
					related = related_triples;
				}
			}
		}
		if (related != null) {

			plan.put("related", related.toString());
		}
		plan.put("requests", requestslist);
		return plan;
	}

	static HashMap<String, Lemma> extractLemmas(N3Document parsedProof) {
		HashMap<String, Lemma> lemmas = new HashMap<String, Lemma>();
		for (N3Element.Statement item : parsedProof.statements) {
			if (item instanceof RDFResource) {
				if (((RDFResource) item).subject instanceof Iri) {
					String name = ((RDFResource) item).subject.toString();
					if (name.startsWith("<#lemma")) {
						lemmas.put(name, new Lemma(name, (RDFResource) item, parsedProof));
					}
				}
			}

		}
		for (Lemma el : lemmas.values()) {
			el.setDependencies(lemmas);
		}
		return lemmas;
	}

	static N3Document transformParseTree(N3Parser.N3DocContext doc) {
		N3TransformationVisitor tv = new N3TransformationVisitor();
		return tv.visitN3Doc(doc);
	}

	static N3Parser.N3DocContext parseProof(String proof) {
		N3Lexer nl = new N3Lexer(new ANTLRInputStream(proof));
		CommonTokenStream ts = new CommonTokenStream(nl);
		N3Parser np = new N3Parser(ts);
		return np.n3Doc();
	}

	public static String doProof(List<String> ruleURIs, String queryURI, boolean quick_answer) {
		List<String> arguments = new ArrayList<String>();
		// arguments.add("--no-qvars");
		if (quick_answer) {
			// needed to avoid cyclic proofs
			arguments.add("--quick-answer");
		}
		arguments.addAll(ruleURIs);

		arguments.add("--step");
		arguments.add("20000000");

		// add the query option to the input
		arguments.add("--query");
		arguments.add(queryURI);

		// String eyeCommand = "eye ";
		// for (String arg : arguments) {
		// eyeCommand += arg + " ";
		// }
		// System.out.println("EYE Command: " + eyeCommand);

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			ByteArrayOutputStream os2 = new ByteArrayOutputStream();
			PrintStream ps2 = new PrintStream(os2);
			euler.Process proof = ProofEngine.createProofEngine(arguments.toArray(new String[0]), ps, ps2);
			proof.execute(80000);
			return os.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
