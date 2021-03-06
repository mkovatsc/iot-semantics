package ch.ethz.inf.vs.semantics.ide.rest;

import ch.ethz.inf.vs.semantics.ide.domain.Device;
import ch.ethz.inf.vs.semantics.ide.workspace.Workspace;
import ch.ethz.inf.vs.semantics.ide.workspace.WorkspaceManager;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.Collection;

@Component
@RestxResource
public class DevicesResource {

	@DELETE("/{workspace}/devices/{id}")
	@PermitAll
	public static Device deleteDevice(int workspace, int id) {

		Workspace ws = WorkspaceManager.get(workspace);
		Device device = ws.deleteDevice(id);
		ws.save();
		return device;
	}

	@POST("/{workspace}/devices")
	@PermitAll
	public static Device postDevice(int workspace, Device msg) {
		Workspace ws = WorkspaceManager.get(workspace);
		Device device = ws.addDevice(msg);
		ws.save();
		return device;
	}

	@GET("/{workspace}/devices")
	@PermitAll
	public Collection<Device> devices(int workspace) {
		return WorkspaceManager.get(workspace).getDevices();
	}

	@GET("/{workspace}/devices/{id}")
	@PermitAll
	public Device getDevice(int workspace, int id) {
		return WorkspaceManager.get(workspace).getDevice(id);
	}

	@PUT("/{workspace}/devices/{id}")
	@PermitAll
	public Device putDevice(int workspace, int id, Device msg) {
		Workspace ws = WorkspaceManager.get(workspace);
		Device device = ws.updateDevice(id, msg);
		ws.save();
		return device;
	}
}
