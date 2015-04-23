package ch.ethz.inf.vs.semantics.ide.domain;

import ch.ethz.inf.vs.semantics.server.semantics.SemanticDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Device {
	private int id;

	private String name;

	private String semantics;

	private String url;

	private boolean remote;

	private boolean disabled;

	private ArrayList<SemanticsErrors> errors;

	@JsonIgnore
	private SemanticDescription semanticDescription;
	private String processed;

	public String getName() {
		return name;
	}

	public Device setName(final String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "Message{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getSemantics() {
		return semantics;
	}

	public Device setSemantics(String semantics) {

		this.semantics = semantics;
		return this;
	}

	public void findErrors() {
		String input = this.semantics;
		ArrayList<SemanticsErrors> errors = N3Linter.lint(input);
		this.errors = errors;
	}

	public int getId() {
		return id;
	}

	public Device setId(int id) {
		this.id = id;
		return this;
	}

	public boolean isDisabled() {
		return disabled || (errors != null && errors.size() > 0);
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public SemanticDescription getSemanticDescription() {
		return semanticDescription;
	}

	public void setSemanticDescription(SemanticDescription semanticDescription) {
		this.semanticDescription = semanticDescription;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public ArrayList<SemanticsErrors> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<SemanticsErrors> errors) {
		this.errors = errors;
	}


	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
