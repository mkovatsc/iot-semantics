package ch.ethz.inf.vs.semantics.ide.rest;

import ch.ethz.inf.vs.semantics.ide.domain.Backup;
import ch.ethz.inf.vs.semantics.ide.domain.UseCase;
import ch.ethz.inf.vs.semantics.ide.workspace.Workspace;
import ch.ethz.inf.vs.semantics.ide.workspace.WorkspaceManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RestxResource
public class BackupResource {

	@POST("/{workspace}/all")
	@PermitAll
	public Backup postBackup(int workspace, Backup backup) {
		Workspace ws = WorkspaceManager.get(workspace);
		ws.loadBackup(backup);
		ws.save();
		return backup;
	}

	@GET("/{workspace}/load/{file}")
	@PermitAll
	public Backup loadFile(int workspace, String file) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			Backup backup = mapper.readValue(IOUtils.toString(getClass().getResourceAsStream("/" + file + ".json")), Backup.class);
			return postBackup(workspace, backup);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET("/usecases")
	@PermitAll
	public ArrayList<UseCase> useCases() {
		ArrayList<UseCase> usecases = new ArrayList<UseCase>();
		usecases.add(new UseCase("Empty configuration", "empty"));
		usecases.add(new UseCase("Thermostat with semantic questions", "thermostat"));
		usecases.add(new UseCase("Thermostat", "thermostat_no_question"));
		usecases.add(new UseCase("Moving Robots", "bots"));
		usecases.add(new UseCase("Smart Home", "smarthome"));
		return usecases;
	}

	@GET("/{workspace}/all")
	@PermitAll
	public Backup getBackup(int workspace) {
		return WorkspaceManager.get(workspace).getBackup();
	}

}
