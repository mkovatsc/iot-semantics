package ch.ethz.inf.vs.semantics.ide.workspace;

import ch.ethz.inf.vs.semantics.ide.domain.Backup;
import ch.ethz.inf.vs.semantics.ide.domain.Device;
import ch.ethz.inf.vs.semantics.ide.domain.Query;
import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;

import java.util.Collection;
import java.util.HashMap;

public abstract class Workspace {
	HashMap<Integer, Query> queries = new HashMap<>();

	public abstract Device deleteDevice(int id);

	public abstract Device addDevice(Device msg);

	public abstract Collection<Device> getDevices();

	public abstract Device getDevice(int id);

	public abstract Device updateDevice(int id, Device msg);

	public abstract String executeQuery(String goal, String input, boolean raw) throws Exception;

	public abstract void clearAnswers();

	public Query addQuery(Query msg) {

		msg.setId(queries.keySet().stream().reduce(0, Integer::max) + 1);
		queries.put(msg.getId(), msg);
		return msg;
	}

	public Collection<Query> getQueries() {
		return queries.values();
	}

	public Query getQuery(int id) {
		return queries.get(id);
	}

	public Query deleteQuery(int id) {
		return queries.remove(id);
	}

	public Query updateQuery(int id, Query msg) {

		Query d = queries.get(id);
		d.setName(msg.getName());
		d.setWatch(msg.isWatch());
		d.setInput(msg.getInput());
		d.setQuery(msg.getQuery());
		return d;
	}

	public abstract Collection<String> getHints();

	public abstract String planMashup(String query, String input) throws Exception;

	public abstract void addAnswer(String n3Document);

	public abstract Backup loadBackup(Backup backup);

	public Backup getBackup() {

		Backup backup = new Backup();
		backup.setDevices(getDevices());
		backup.setQueries(getQueries());
		return backup;
	}

	public abstract WorkspaceInfo getWorkspaceInfo();

	public abstract void setName(String name);
}
