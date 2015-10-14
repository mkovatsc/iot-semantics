package ch.ethz.inf.vs.semantics.ide.domain;

public class WorkspaceInfo {

	private int id;
	private String name;
	private Backup backup;
	private String type;
	private String url;

	public WorkspaceInfo() {
	}

	public WorkspaceInfo(int id, String name, String type, String url) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Backup getBackup() {
		return backup;
	}

	public void setBackup(Backup backup) {
		this.backup = backup;
	}
}
