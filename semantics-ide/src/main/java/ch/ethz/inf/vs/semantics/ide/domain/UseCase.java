package ch.ethz.inf.vs.semantics.ide.domain;

public class UseCase {

	private String name;
	private String file;

	public UseCase(String name, String file) {

		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public String getFile() {
		return file;
	}
}
