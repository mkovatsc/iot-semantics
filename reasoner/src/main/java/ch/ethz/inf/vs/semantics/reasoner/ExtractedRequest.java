package ch.ethz.inf.vs.semantics.reasoner;

import ch.ethz.inf.vs.semantics.parser.elements.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the requests extracted from the proof.
 */
public class ExtractedRequest {
	public final String id;
	private final N3Element.Object method;
	private final N3Element.Object uri;
	private final Set<String> dependencies;
	private final N3Element.Object resp;
	private final N3Element.Object reqBody;
	private final Formula related;
	private final Lemma lemma;

	// private N3Document doc;

	public ExtractedRequest(String id, RDFResource RDFResource, Formula context, N3Document doc, Lemma lemma) {
		// this.doc = doc;
		this.lemma = lemma;
		this.id = id;
		this.method = RDFResource.get("http://www.w3.org/2011/http#methodName");
		this.uri = RDFResource.get("http://www.w3.org/2011/http#requestURI");
		this.reqBody = RDFResource.get("http://www.w3.org/2011/http#reqBody");
		this.resp = RDFResource.get("http://www.w3.org/2011/http#resp");
		related = new Formula();
		if (resp != null) {
			extractRelatedTriples(context, resp);
		}
		if (reqBody != null) {
			extractRelatedTriples(context, reqBody);
		}
		extractRelatedTriples(context, uri);
		this.dependencies = new HashSet<String>();
	}

	/**
	 * Extract triples needed to parse response or extract request parameters
	 * 
	 * @param context
	 * @param ref
	 */
	private void extractRelatedTriples(Formula context, N3Element.Object ref) {
		if (ref instanceof Exvar) {
			RDFResource sub = context.get(ref.toString());
			if (sub != null) {
				related.add(sub);
				for (VerbObject obj : sub.verbObjects) {
					extractRelatedTriples(context, obj.object);
				}
			}
		} else if (ref instanceof List) {
			for (Object o : (List<?>) ref) {
				if (o instanceof N3Element.Object) {
					extractRelatedTriples(context, (N3Element.Object) o);
				}
			}
		}
	}

	@Override
	public String toString() {
		String str = id + " " + method.toString() + " " + uri.toString();

		if (reqBody != null) {
			str += " " + reqBody.toString();
		}

		str += " " + this.dependencies;
		if (resp != null) {
			str += " => " + resp.toString();
		}
		return str;
	}

	/**
	 * Add dependency between requests
	 * 
	 * @param r
	 */
	public void addDependency(ExtractedRequest r) {
		this.dependencies.add(r.id);
	}

	/**
	 * Serialize Extracted request as a {@link JSONObject}
	 * 
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("level", this.lemma.getLevel());
		obj.put("method", serialize(method));
		obj.put("uri", serialize(uri));
		if (reqBody != null) {
			obj.put("reqBody", serialize(reqBody));
		}
		if (resp != null) {
			obj.put("resp", serialize(resp));
		}
		if (dependencies.size() > 0) {
			obj.put("dependencies", new JSONArray(dependencies));
		}
		return obj;
	}

	private Object serialize(N3Element.Object resp) {
		if (resp instanceof Iri) {
			if (((Iri) resp).uriref) {
				return ((Iri) resp).text;
			} else {
				return resp.toString();
			}
		} else if (resp instanceof Exvar) {
			return new JSONObject().put("ref", resp.toString());

		} else if (resp instanceof ObjectCollection) {
			JSONArray jsonArray = new JSONArray();
			for (N3Element.Object o : (ObjectCollection) resp) {
				jsonArray.put(serialize(o));
			}
			return jsonArray;
		} else if (resp instanceof Literal) {
			return ((Literal) resp).text;
		} else if (resp instanceof Statements) {
			N3Document newdoc = new N3Document();
			newdoc.importStatements((Statements) resp);
			return newdoc.toString();
		}
		return resp.toString();
	}

	/**
	 * Serialize related triples
	 * 
	 * @return
	 */
	public N3Document getRelated() {

		N3Document newdoc = new N3Document();
		newdoc.importStatements(related);
		return newdoc;
	}

	public boolean hasDependency(ExtractedRequest r) {
		return dependencies.contains(r.id);
	}

	public void removeDependency(ExtractedRequest r) {
		this.dependencies.remove(r.id);
	}

}
