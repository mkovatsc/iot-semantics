package ch.ethz.inf.vs.semantics.ide.domain;

import ch.ethz.inf.vs.semantics.parser.elements.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.UUID;

public class Question {
	private String id;
	@JsonIgnore
	private RDFResource RDFResource;
	private String answer;
	private String answerId;
	private String questionText;
	private ArrayList<Option> options;

	public Question() {

	}

	public Question(String id, RDFResource t) {
		this.setId(id);
		update(t);
	}

	public void update(RDFResource t) {
		this.RDFResource = t;
		questionText = t.getReadableString(":text");
		setAnswer("");
		Object o = t.get(":options");

		setOptions(new ArrayList<Option>());
		if (o instanceof Formula) {
			Formula f = (Formula) o;
			for (N3Element.Statement s : f) {
				if (s instanceof RDFResource) {
					Option qoption = new Option(((RDFResource) s).subject, ((RDFResource) s).getReadableString(":name"));
					getOptions().add(qoption);
				}
			}
		}
	}

	public String sendResponse() {
		N3Document input = new N3Document();
		input.addPrefix(new Prefix("in:", "<in#>"));
		input.addPrefix(new Prefix(":", "<ex#>"));
		if (getAnswer() != null) {
			Iri answerObject = new Iri(input, "in:" + UUID.randomUUID().toString().replace("-", ""));

			Iri replyType = input.importToDocument((Iri) RDFResource.get(":replyType"));
			RDFResource t = new RDFResource(answerObject).add(new Iri(input, ":name"), new Literal("\"" + StringEscapeUtils.escapeJava(getAnswer()) + "\"")).add(new Verb("a"), new Iri(input, ":answer")).add(new Verb("a"), replyType);

			return sendResponse(t);

		} else if (getAnswerId() != null) {
			Iri answerObject = new Iri(input, getAnswerId());

			RDFResource t = new RDFResource(answerObject);

			return sendResponse(t);
		}
		throw new RuntimeException("Invalid answer");
	}

	public String sendResponse(N3Element.Subject subject) {
		return sendResponse(new RDFResource(subject));
	}

	public String sendResponse(RDFResource t) {

		N3Document input = new N3Document();
		input.addPrefix(new Prefix(":", "<ex#>"));
		Iri question = input.importToDocument((Iri) RDFResource.subject);
		t = input.importToDocument(t);
		t.add(new Iri(input, ":answers"), question);
		input.statements.add(t);
		input.statements.add(new RDFResource(question).add(new Iri(input, ":answer"), (N3Element.Object) t.subject));

		return input.toString();
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	static public class Option {

		@JsonIgnore
		public N3Element.Subject subject;
		private String name;
		private String id;

		public Option() {

		}

		public Option(N3Element.Subject subject, String name) {
			this.setId(subject.toString());
			this.subject = subject;
			this.setName(name);
		}

		@Override
		public String toString() {
			return getName();
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
