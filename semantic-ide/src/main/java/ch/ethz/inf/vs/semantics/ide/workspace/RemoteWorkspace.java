package ch.ethz.inf.vs.semantics.ide.workspace;

import ch.ethz.inf.vs.semantics.ide.domain.Backup;
import ch.ethz.inf.vs.semantics.ide.domain.Device;
import ch.ethz.inf.vs.semantics.ide.domain.Query;
import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class RemoteWorkspace extends Workspace {
	private String name;
	private final String url;
	private final ObjectMapper mapper;
	private final Timer synchronizeDevices;

	Collection<Device> devices;

	public RemoteWorkspace(int id, String name, String url) {
		mapper = new ObjectMapper();
		this.id = id;
		this.name = name;
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		this.url = url;

		synchronizeDevices(1000);
		synchronizeDevices = new Timer();
		synchronizeDevices.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					synchronizeDevices(7000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1000, 20000);
	}

	private void synchronizeDevices(int timeout) throws RuntimeException {
		CoapClient c = new CoapClient();
		c.setTimeout(timeout);
		c.setURI(this.url + "/debug/devices");

		String data = c.get().getResponseText();
		Object devices = null;
		try {
			devices = mapper.readValue(data, new TypeReference<Collection<Device>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException();
		}
		RemoteWorkspace.this.devices = (Collection<Device>) devices;

	}

	@Override
	public Device deleteDevice(int id) {
		return null;
	}

	@Override
	public Device addDevice(Device msg) {
		return null;
	}

	@Override
	public Collection<Device> getDevices() {
		return devices;
	}

	@Override
	public Device getDevice(int id) {
		return null;
	}

	@Override
	public Device updateDevice(int id, Device msg) {
		return null;
	}

	@Override
	public String executeQuery(String goal, String input, boolean raw) throws Exception {
		CoapClient c = new CoapClient();
		c.setTimeout(5000);
		c.setURI(url + "/sr/query?raw=" + raw);

		String data = c.post(goal + "\n#####################\n" + input, MediaTypeRegistry.APPLICATION_JSON).getResponseText();
		return data;
	}

	@Override
	public void clearAnswers() {

	}

	@Override
	public Collection<String> getHints() {

		CoapClient c = new CoapClient();
		c.setTimeout(10000);
		c.setURI(url + "/debug/hints");

		try {
			Object hints = null;
			hints = mapper.readValue(c.get().getResponseText(), new TypeReference<Collection<String>>() {
			});
			return (Collection<String>) hints;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashSet<>();
	}

	@Override
	public String planMashup(String goal, String input) throws Exception {
		CoapClient c = new CoapClient();
		c.setTimeout(5000);
		c.setURI(url + "/sr/mashup");

		String data = c.post(goal + "\n#####################\n" + input, MediaTypeRegistry.APPLICATION_JSON).getResponseText();
		return data;
	}

	@Override
	public void addAnswer(String n3Document) {

		CoapClient c = new CoapClient();
		c.setTimeout(5000);
		c.setURI(url + "/answer");
		c.put(n3Document, MediaTypeRegistry.TEXT_PLAIN);

	}

	@Override
	public Backup loadBackup(Backup backup) {
		queries.clear();

		for (Query q : backup.getQueries()) {
			addQuery(q);
		}
		save();
		return backup;
	}

	@Override
	public void save() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			WorkspaceInfo info = getWorkspaceInfo();
			mapper.writeValue(getFile(), info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public WorkspaceInfo getWorkspaceInfo() {
		return new WorkspaceInfo(id, name, "remote", url);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			synchronizeDevices.cancel();
		} finally {
			super.finalize();
		}
	}
}
