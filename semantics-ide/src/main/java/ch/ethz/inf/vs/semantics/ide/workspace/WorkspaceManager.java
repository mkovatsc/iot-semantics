package ch.ethz.inf.vs.semantics.ide.workspace;

import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorkspaceManager {
	private static volatile WorkspaceManager instance = null;
	private Map<Integer, Workspace> workspaces;
	private int ID;

	private WorkspaceManager() {
		workspaces = new HashMap<>();
		ID = 1;
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
		WorkspaceInfo ws = get(id).getWorkspaceInfo();
		workspaces.remove(id);
		return ws;
	}

	public WorkspaceInfo createWorkspace(WorkspaceInfo ws) {
		synchronized (this) {
			int id = ID;
			ID++;
			Workspace workspace;
			if ("remote".equals(ws.getType())) {

				workspace = new RemoteWorkspace(id, ws.getName(), ws.getUrl());
			} else {

				workspace = new VirtualWorkspace(id, ws.getName());
			}
			workspaces.put(id, workspace);
			return workspace.getWorkspaceInfo();
		}
	}
}
