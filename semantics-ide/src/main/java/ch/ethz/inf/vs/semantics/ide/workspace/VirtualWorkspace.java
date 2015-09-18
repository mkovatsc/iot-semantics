package ch.ethz.inf.vs.semantics.ide.workspace;

import ch.ethz.inf.vs.semantics.ide.domain.Backup;
import ch.ethz.inf.vs.semantics.ide.domain.Device;
import ch.ethz.inf.vs.semantics.ide.domain.Query;
import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;
import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDataContainer;
import ch.ethz.inf.vs.semantics.server.semantics.SemanticDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class VirtualWorkspace extends Workspace {

	private String name;
	public HashMap<Integer, Device> devices;
	SemanticDataContainer semanticDataContainer;

	public VirtualWorkspace(int id, String name) {
		this.id = id;
		this.name = name;

		try {
			semanticDataContainer = new SemanticDataContainer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		devices = new HashMap<>();

	}

	@Override
	public Device deleteDevice(int id) {
		Device d = devices.get(id);
		d.setDisabled(true);
		update(d);
		semanticDataContainer.removeSemanticDescription(d.getSemanticDescription());
		return devices.remove(id);

	}

	@Override
	public Device addDevice(Device msg) {
		msg.setId(devices.keySet().stream().reduce(0, Integer::max) + 1);
		devices.put(msg.getId(), msg);
		SemanticDescription sd = new SemanticDescription("device" + msg.getId(), "coap://device:" + (msg.getId() + 1000), semanticDataContainer.getFileContainer());
		msg.setSemanticDescription(sd);
		semanticDataContainer.addSemanticDescription(sd);
		update(msg);
		return msg;
	}

	@Override
	public Collection<Device> getDevices() {
		return devices.values();
	}

	@Override
	public Device getDevice(int id) {
		return devices.get(id);
	}

	@Override
	public Device updateDevice(int id, Device msg) {

		Device d = devices.get(id);
		d.setName(msg.getName());
		if (d.getErrors() == null || d.getErrors().size() == 0) {
			d.setDisabled(msg.isDisabled());
		}
		d.setSemantics(msg.getSemantics());
		update(d);
		return d;
	}

	@Override
	public String executeQuery(String goal, String input, boolean raw) throws Exception {
		return semanticDataContainer.executeQuery(goal, input, raw);

	}

	@Override
	public void clearAnswers() {
		semanticDataContainer.clearAnswers();
	}

	@Override
	public Collection<String> getHints() {

		HashSet<String> hints = new HashSet<>();

		for (String k : semanticDataContainer.getActiveKeys()) {
			SemanticDescription sem = semanticDataContainer.getSemantics().get(k);
			if (sem.isLoaded()) {
				hints.addAll(N3Utils.getHints(sem.getContent()));
			}
		}
		for (String k : semanticDataContainer.getHardcodedFiles()) {
			try {
				hints.addAll(N3Utils.getHints(FileUtils.readFileToString(new File(k))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			hints.addAll(N3Utils.getHints(FileUtils.readFileToString(semanticDataContainer.getAnswersFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hints;
	}

	@Override
	public String planMashup(String query, String input) throws Exception {
		return semanticDataContainer.planMashup(query, input);
	}

	@Override
	public void addAnswer(String n3Document) {
		semanticDataContainer.addAnswer(N3Utils.parseN3Document(n3Document));

	}

	@Override
	public Backup loadBackup(Backup backup) {

		semanticDataContainer.clearAnswers();
		ArrayList<Device> devices = new ArrayList<Device>();
		devices.addAll(getDevices());
		for (Device d : devices) {
			deleteDevice(d.getId());
		}
		for (Device msg : backup.getDevices()) {
			msg.setRemote(false);
			msg.setUrl("");
			addDevice(msg);
		}

		queries.clear();

		for (Query q : backup.getQueries()) {
			addQuery(q);
		}
		return backup;
	}

	@Override
	public WorkspaceInfo getWorkspaceInfo() {
		return new WorkspaceInfo(id, name, "virtual");
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void save() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			WorkspaceInfo info = getWorkspaceInfo();
			info.setBackup(getBackup());
			mapper.writeValue(getFile(), info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void update(Device msg) {
		SemanticDescription semanticDescription = msg.getSemanticDescription();
		try {
			msg.setProcessed(semanticDescription.processFile(msg.getSemantics()));

		} catch (RuntimeException ex) {

		}
		msg.findErrors();
		if (msg.isDisabled()) {
			semanticDataContainer.removeActiveEndpoint(semanticDescription.getEndpoint());
		} else {

			semanticDataContainer.addActiveEndpoint(semanticDescription.getEndpoint());
		}
	}
}
