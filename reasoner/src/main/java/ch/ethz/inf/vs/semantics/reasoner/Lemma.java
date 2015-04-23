package ch.ethz.inf.vs.semantics.reasoner;

import ch.ethz.inf.vs.semantics.parser.elements.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Lemma {
	private final String type;
	private final RDFResource RDFResource;
	private N3Document doc;
	private final String name;
	private Set<Lemma> dependencies;
	private Set<ExtractedRequest> localRequests;
	private Set<ExtractedRequest> requests;
	private Set<String> evidence;
	private int level;

	public Lemma(String name, RDFResource value, N3Document doc) {
		this.name = name;
		this.RDFResource = value;
		this.doc = doc;
		this.type = ((Iri) value.get("a")).getGlobalName();
		extractEvidence();
	}

	/**
	 * Extract evidence from n3 triple set. By parsing the triple <subject>
	 * r:evidence ?
	 */
	private void extractEvidence() {
		evidence = new HashSet<String>();
		ObjectCollection n3evidence = (ObjectCollection) RDFResource.get("http://www.w3.org/2000/10/swap/reason#evidence");
		if (n3evidence != null) {
			for (N3Element.Object e : n3evidence) {
				addEvidence(e);
			}
		} else {
			addEvidence(n3evidence);
		}
	}

	private void addEvidence(N3Element.Object el1) {
		if (el1 instanceof Iri) {
			evidence.add(el1.toString());
		}
	}

	/**
	 * Extract add all the lemmas listed as evidence in to the dependency list
	 *
	 * @param lemmas
	 */
	public void setDependencies(HashMap<String, Lemma> lemmas) {
		dependencies = new HashSet<Lemma>();
		for (String e : evidence) {
			if (e.startsWith("<#lemma")) {
				dependencies.add(lemmas.get(e));
			}
		}
	}

	/**
	 * Extract requests from this lemma and its dependencies
	 *
	 * @return
	 */
	public Set<ExtractedRequest> getRequests() {

		if (requests != null) {
			return requests;
		}
		requests = new HashSet<ExtractedRequest>();

		extractLocalRequests();
		requests.addAll(localRequests);

		for (Lemma d : dependencies) {
			for (ExtractedRequest r : d.getRequests()) {
				for (ExtractedRequest l : localRequests) {
					l.addDependency(r);
				}
				requests.add(r);
			}
		}
		for (ExtractedRequest r1 : requests) {

			for (ExtractedRequest r2 : requests) {

				for (ExtractedRequest r3 : requests) {
					if (r3.hasDependency(r2) && r2.hasDependency(r1)) {
						r3.removeDependency(r1);
					}
				}
			}
		}
		return requests;
	}

	/**
	 * Extract requests form this lemma.
	 */
	private void extractLocalRequests() {
		if (localRequests != null) {
			return;
		}
		localRequests = new HashSet<ExtractedRequest>();
		if ("http://www.w3.org/2000/10/swap/reason#Inference".equals(type)) {
			Formula gives = (Formula) RDFResource.get("http://www.w3.org/2000/10/swap/reason#gives");
			for (N3Element.Statement s : gives) {
				if (s instanceof RDFResource) {
					if (((RDFResource) s).get("http://www.w3.org/2011/http#requestURI") != null) {
						ExtractedRequest request = new ExtractedRequest(name, (RDFResource) s, gives, doc, this);
						localRequests.add(request);
					}
				}
			}
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = Math.max(level, this.level);
		for (Lemma dp : dependencies) {
			if (localRequests.size() == 0) {
				dp.setLevel(this.level);

			} else {
				dp.setLevel(this.level + 1);

			}
		}
	}
}
