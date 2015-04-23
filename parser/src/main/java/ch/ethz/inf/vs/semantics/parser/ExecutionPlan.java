package ch.ethz.inf.vs.semantics.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExecutionPlan {
	public final ReferenceManager refManager;
	Map<String, Request> requests;
	private IRequestFactory factory;

	public ExecutionPlan(String plan, IRequestFactory factory) {
		this.refManager = new ReferenceManager();
		this.factory = factory;
		JSONObject obj = new JSONObject(plan);
		try {
			this.refManager.setRelated(obj.getString("related"));
		} catch (JSONException ex) {

		}
		parse(obj);
	}

	private void parse(JSONObject obj) {
		JSONArray objRequests = obj.getJSONArray("requests");
		requests = new HashMap<String, Request>();
		for (int i = 0; i < objRequests.length(); i++) {
			Request req = parseRequest(objRequests.getJSONObject(i));
			requests.put(req.getID(), req);
		}
		for (int i = 0; i < objRequests.length(); i++) {
			JSONObject item = objRequests.getJSONObject(i);
			if (item.has("dependencies")) {
				JSONArray dependencies = item.getJSONArray("dependencies");
				int id = item.getInt("id");
				Request r = requests.get(id);
				for (int j = 0; j < dependencies.length(); j++) {
					r.addDependency(requests.get(dependencies.getInt(j)));
				}
			}
		}
	}

	private Request parseRequest(JSONObject jsonObject) {
		RequestValue method = unserialize(jsonObject.get("method"));
		RequestValue uri = unserialize(jsonObject.get("uri"));
		RequestValue reqBody = null;
		if (jsonObject.has("reqBody"))
			reqBody = unserialize(jsonObject.get("reqBody"));
		RequestValue resp = null;
		if (jsonObject.has("resp"))
			resp = unserialize(jsonObject.get("resp"));

		return factory.getRequest(jsonObject.getString("id"), method, uri, reqBody, resp);

	}

	private RequestValue unserialize(Object val) {
		if (val instanceof JSONObject) {
			String ref = ((JSONObject) val).getString("ref");
			if (ref != null) {
				return new ReferenceValue(ref);
			}
		} else if (val instanceof JSONArray) {
			ListValue values = new ListValue();

			for (int i = 0; i < ((JSONArray) val).length(); i++) {
				values.add(unserialize(((JSONArray) val).get(i)));
			}
			return values;

		} else if (val instanceof String) {
			return new StringValue((String) val);
		}
		throw new RuntimeException("Unknown format");
	}

	public void addTransformer(Transformer t) {
		refManager.addTransformer(t);
	}

	public <T> void execute(final ExecutionPlan.RequestCallback callback) {
		execute(callback, null);
	}

	public <T> void execute(final ExecutionPlan.RequestCallback callback, T result) {
		for (final Request r : requests.values()) {
			if (!r.isDone() && !r.isRunning() && r.isExecutable()) {
				r.execute(new RequestCallback() {
					@Override
					public void onComplete(Object result) {
						if (r.resp instanceof ReferenceValue) {
							refManager.set(((ReferenceValue) r.resp).ref, result);
						}
						execute(callback, result);
					}
				});
			} else if (r.isDone()) {
				if (callback != null) {
					callback.onComplete(result);
				}
			}
		}
	}

	public static interface RequestValue {

	}

	static public interface IRequestFactory {
		public Request getRequest(String id, RequestValue method, RequestValue uri, RequestValue reqBody, RequestValue resp);
	}

	static public interface RequestCallback {
		public void onComplete(Object result);
	}

	static public abstract class Request {

		public String id;
		public RequestValue method;
		public RequestValue uri;
		public RequestValue reqBody;
		public RequestValue resp;
		public boolean running;
		public boolean done;

		public Request(String id, RequestValue method, RequestValue uri, RequestValue reqBody, RequestValue resp) {
			this.id = id;
			this.method = method;
			this.uri = uri;
			this.reqBody = reqBody;
			this.resp = resp;
		}

		private ArrayList<Request> dependencies = new ArrayList<Request>();

		public abstract void execute(RequestCallback callback);

		public String getID() {
			return id;
		}

		public void addDependency(Request request) {

			dependencies.add(request);
		}

		public boolean isRunning() {
			return running;
		}

		public boolean isDone() {
			return done;
		}

		public boolean isExecutable() {
			for (Request d : dependencies) {
				if (!d.isDone()) {
					return false;
				}
			}
			return true;
		}
	}

	public class ReferenceValue implements RequestValue {
		public final String ref;

		public ReferenceValue(String ref) {
			this.ref = ref;
		}

		@Override
		public String toString() {
			return refManager.get(ref);
		}
	}

	public class StringValue implements RequestValue {
		private String value;

		public StringValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}

	public class ListValue extends ArrayList<RequestValue> implements RequestValue {

		private static final long serialVersionUID = -1256112403589855134L;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (RequestValue v : this) {
				sb.append(v.toString());
			}
			return sb.toString();
		}

	}

}
