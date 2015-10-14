package ch.ethz.inf.vs.semantics.ide.rest;

import ch.ethz.inf.vs.semantics.ide.domain.Backup;
import ch.ethz.inf.vs.semantics.ide.domain.WorkspaceInfo;
import ch.ethz.inf.vs.semantics.ide.workspace.WorkspaceManager;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.Collection;

@Component
@RestxResource
public class WorkspaceResource {

	@GET("/workspaces")
	@PermitAll
	public Collection<WorkspaceInfo> getWorkspaces() {
		return WorkspaceManager.getWorkspaces();
	}

	@POST("/workspaces")
	@PermitAll
	public static WorkspaceInfo postWorkspace(WorkspaceInfo workspace) {
		return WorkspaceManager.getInstance().createWorkspace(workspace);
	}

	@DELETE("/workspaces/{id}")
	@PermitAll
	public static WorkspaceInfo deleteWorkspace(int id) {

		return WorkspaceManager.getInstance().deleteWorkspace(id);
	}

	@PUT("/workspaces/{id}")
	@PermitAll
	public static WorkspaceInfo updateWorkspace(int id, WorkspaceInfo ws) {
		WorkspaceManager.get(id).setName(ws.getName());
		return ws;
	}

	@POST("/{workspace}/copy")
	@PermitAll
	public static WorkspaceInfo copyWorkspace(int workspace, WorkspaceInfo body) {
		Backup b = WorkspaceManager.get(workspace).getBackup();
		WorkspaceInfo ws = WorkspaceManager.getInstance().createWorkspace(body);
		WorkspaceManager.get(ws.getId()).loadBackup(b);
		return ws;
	}

}
