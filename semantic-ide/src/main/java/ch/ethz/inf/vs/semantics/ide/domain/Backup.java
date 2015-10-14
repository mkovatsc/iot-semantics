package ch.ethz.inf.vs.semantics.ide.domain;

import java.util.Collection;

public class Backup {
	private Collection<Device> devices;
	private Collection<Query> queries;

	public Backup() {

	}

	public Backup(Collection<Device> devices, Collection<Query> queries) {

		this.devices = devices;
		this.queries = queries;
	}

	public Collection<Device> getDevices() {
		return devices;
	}

	public void setDevices(Collection<Device> devices) {
		this.devices = devices;
	}

	public Collection<Query> getQueries() {
		return queries;
	}

	public void setQueries(Collection<Query> queries) {
		this.queries = queries;
	}
}
