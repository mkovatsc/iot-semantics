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
	private final ExecutionPlan.IRequestFactory factory;

	public ExecutionPlan(String plan, ExecutionPlan.IRequestFactory factory) {
		refManager = new ReferenceManager();
		this.factory = factory;
		JSONObject obj = new JSONObject(plan);
		try {
			refManager.setRelated(obj.getString("related"));
		} catch (JSONException ex) {

		}
		this.parse(obj);
	}

	private void parse(JSONObject obj) {
		JSONArray objRequests = obj.getJSONArray("requests");
		this.requests = new HashMap<String, Request>();
		for (int i = 0; i < objRequests.length(); i++) {
			Request req = this.parseRequest(objRequests.getJSONObject(i));
			this.requests.put(req.getID(), req);
		}
		for (int i = 0; i < objRequests.length(); i++) {
			JSONObject item = objRequests.getJSONObject(i);
			if (item.has("dependencies")) {
				JSONArray dependencies = item.getJSONArray("dependencies");
				String id = item.getString("id");
				Request r = this.requests.get(id);
				for (int j = 0; j < dependencies.length(); j++) {
					r.addDependency(this.requests.get(dependencies.getString(j)));
				}
			}
		}
	}

	public Map<String, Request> getRequests() {
		return requests;
	}

	private Request parseRequest(JSONObject jsonObject) {
		ExecutionPlan.RequestValue method = this.unserialize(jsonObject.get("method"));
		ExecutionPlan.RequestValue uri = this.unserialize(jsonObject.get("uri"));
		ExecutionPlan.RequestValue reqBody = null;
		if (jsonObject.has("reqBody"))
			reqBody = this.unserialize(jsonObject.get("reqBody"));
		ExecutionPlan.RequestValue resp = null;
		if (jsonObject.has("resp"))
			resp = this.unserialize(jsonObject.get("resp"));

		return this.factory.getRequest(jsonObject.getString("id"), method, uri, reqBody, resp);

	}

	private ExecutionPlan.RequestValue unserialize(Object val) {
		if (val instanceof JSONObject) {
			String ref = ((JSONObject) val).getString("ref");
			if (ref != null) {
				return new ExecutionPlan.ReferenceValue(ref);
			}
		} else if (val instanceof JSONArray) {
			ExecutionPlan.ListValue values = new ExecutionPlan.ListValue();

			for (int i = 0; i < ((JSONArray) val).length(); i++) {
				values.add(this.unserialize(((JSONArray) val).get(i)));
			}
			return values;

		} else if (val instanceof String) {
			return new StringValue((String) val);
		}
		throw new RuntimeException("Unknown format");
	}

	public void addTransformer(Transformer t) {
		this.refManager.addTransformer(t);
	}

	public <T> void execute(RequestCallback callback) {
		this.execute(callback, null);
	}

	public <T> void execute(final RequestCallback callback, T result) {
		for (final Request r : this.requests.values()) {
			if (!r.isDone() && !r.isRunning() && r.isExecutable()) {
				r.execute(new ExecutionPlan.RequestCallback() {
					@Override
					public void onComplete(Object result) {
						if (r.resp instanceof ExecutionPlan.ReferenceValue) {
							ExecutionPlan.this.refManager.set(((ExecutionPlan.ReferenceValue) r.resp).ref, result);
						}
						ExecutionPlan.this.execute(callback, result);
					}
				});
			} else if (r.isDone()) {
				if (callback != null) {
					callback.onComplete(result);
				}
			}
		}
	}

	public interface RequestValue {

	}

	public interface IRequestFactory {
		Request getRequest(String id, ExecutionPlan.RequestValue method, ExecutionPlan.RequestValue uri, ExecutionPlan.RequestValue reqBody, ExecutionPlan.RequestValue resp);
	}

	public interface RequestCallback {
		void onComplete(Object result);
	}

	public abstract static class Request {

		public String id;
		public ExecutionPlan.RequestValue method;
		public ExecutionPlan.RequestValue uri;
		public ExecutionPlan.RequestValue reqBody;
		public ExecutionPlan.RequestValue resp;
		public boolean running;
		public boolean done;

		public Request(String id, ExecutionPlan.RequestValue method, ExecutionPlan.RequestValue uri, ExecutionPlan.RequestValue reqBody, ExecutionPlan.RequestValue resp) {
			this.id = id;
			this.method = method;
			this.uri = uri;
			this.reqBody = reqBody;
			this.resp = resp;
		}

		private final ArrayList<Request> dependencies = new ArrayList<Request>();

		public abstract void execute(ExecutionPlan.RequestCallback callback);

		public String getID() {
			return this.id;
		}

		public void addDependency(Request request) {

			this.dependencies.add(request);
		}

		public boolean isRunning() {
			return this.running;
		}

		public boolean isDone() {
			return this.done;
		}

		public boolean isExecutable() {
			for (Request d : this.dependencies) {
				if (!d.isDone()) {
					return false;
				}
			}
			return true;
		}
	}

	public class ReferenceValue implements ExecutionPlan.RequestValue {
		public final String ref;

		public ReferenceValue(String ref) {
			this.ref = ref;
		}

		@Override
		public String toString() {
			return ExecutionPlan.this.refManager.get(this.ref);
		}
	}

	public class StringValue implements ExecutionPlan.RequestValue {
		private final String value;

		public StringValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

	}

	public class ListValue extends ArrayList<ExecutionPlan.RequestValue> implements ExecutionPlan.RequestValue {

		private static final long serialVersionUID = -1256112403589855134L;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (ExecutionPlan.RequestValue v : this) {
				sb.append(v);
			}
			return sb.toString();
		}

	}

}
