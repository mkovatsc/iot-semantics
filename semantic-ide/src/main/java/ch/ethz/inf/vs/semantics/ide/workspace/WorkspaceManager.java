package ch.ethz.inf.vs.semantics.ide.workspace;

import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorkspaceManager {
	private static volatile WorkspaceManager instance = null;
	private static File persistenceFolder;
	private static Object lock = new Object();
	private Map<Integer, Workspace> workspaces;
	private int ID;

	private WorkspaceManager() {
		workspaces = new HashMap<>();
		ID = 1;
		for (File ws : getFolder().listFiles()) {
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
			try {
				WorkspaceInfo workspaceinfo = mapper.readValue(ws, WorkspaceInfo.class);
				createWorkspace(workspaceinfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static WorkspaceManager getInstance() {
		if (instance == null) {
			synchronized (WorkspaceManager.class) {
				if (instance == null) {
					instance = new WorkspaceManager();
				}
			}
		}
		return instance;
	}

	public static File getFolder() {
		if (persistenceFolder==null) {
			synchronized (lock) {
				if (persistenceFolder==null) {
					persistenceFolder = new File("workspaces");
					if (!persistenceFolder.exists()) {
						persistenceFolder.mkdir();
					}
				}
			}
		}
		return persistenceFolder;
	}

	public Workspace getWorkspace(int id) {
		return workspaces.get(id);
	}

	public static Workspace get(int workspace) {
		return getInstance().getWorkspace(workspace);
	}

	public static Collection<WorkspaceInfo> getWorkspaces() {
		ArrayList<WorkspaceInfo> workspaces = new ArrayList<>();
		for (Workspace w : getInstance().workspaces.values()) {
			workspaces.add(w.getWorkspaceInfo());
		}
		return workspaces;
	}

	public WorkspaceInfo deleteWorkspace(int id) {
		Workspace workspace = get(id);
		WorkspaceInfo ws = workspace.getWorkspaceInfo();
		workspaces.remove(id);
		workspace.remove();
		return ws;
	}

	public WorkspaceInfo createWorkspace(WorkspaceInfo ws) {
		synchronized (this) {
			int id;
			if (!workspaces.containsKey(ws.getId()) && ws.getId()!=0) {
				id = ws.getId();
				ID = Math.max(ID, id + 1);
			} else {
				id = ID;
				ID++;
			}
			Workspace workspace;
			if ("remote".equals(ws.getType())) {

				workspace = new RemoteWorkspace(id, ws.getName(), ws.getUrl());
			} else {

				workspace = new VirtualWorkspace(id, ws.getName());
			}
			workspaces.put(id, workspace);
			if (ws.getBackup()!=null) {
				workspace.loadBackup(ws.getBackup());
			}
			workspace.save();
			return workspace.getWorkspaceInfo();
		}
	}
}
