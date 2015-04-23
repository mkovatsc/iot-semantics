package ch.ethz.inf.vs.semantics.ide.rest;

import ch.ethz.inf.vs.semantics.ide.domain.Question;
import ch.ethz.inf.vs.semantics.ide.workspace.WorkspaceManager;
import ch.ethz.inf.vs.semantics.parser.N3Utils;
import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.parser.elements.N3Element;
import ch.ethz.inf.vs.semantics.parser.elements.RDFResource;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@Component
@RestxResource
public class QuestionResource {

	@GET("/{workspace}/questions")
	@PermitAll
	public List<Question> questions(int workspace) {
		String goal = "@prefix : <ex#>. \n" +
				"@prefix log: <http://www.w3.org/2000/10/swap/log#>.\n" +
				"@prefix e: <http://eulersharp.sourceforge.net/2003/03swap/log-rules#>.\n" +
				" \n" +
				"{  \n" +
				"\t?x a :openquestion.\n" +
				"\t?x :replyType ?t.\n" +
				"\t?x :text ?text.\n" +
				"\t (?S ?SP) e:findall ( { ?X :name ?L }\n" +
				"\t    { ?X a ?t. ?X :name ?L}\n" +
				"\t    ?ITEMS\n" +
				"\t  ) .\n" +
				"  ?ITEMS log:conjunction ?F.\n" +
				"}\n" +
				"=>\n" +
				"{  \n" +
				"\t?x :options ?F.\n" +
				"\t?x :replyType ?t.\n" +
				"\t?x :text ?text.\n" +
				"}.\n";

		String result = null;
		try {
			result = WorkspaceManager.get(workspace).executeQuery(goal, "", false);
		} catch (Exception e) {
			return null;
		}
		N3Document resp = N3Utils.parseN3Document(result);
		List<Question> list = new ArrayList<Question>();
		for (N3Element.Statement s : resp.statements) {
			if (s instanceof RDFResource) {
				RDFResource t = (RDFResource) s;
				String k = t.subject.toString();
				list.add(new Question(k, t));
			}
		}
		return list;
	}

	@DELETE("/{workspace}/questions")
	@PermitAll
	public String deleteAnswers(int workspace) {
		WorkspaceManager.get(workspace).clearAnswers();
		return "";
	}

	@PUT("/{workspace}/question/{id}")
	@PermitAll
	public Question putQuestion(int workspace, String id, Question q) {
		List<Question> questions = questions(workspace);
		for (Question question : questions) {
			if (question.getId().equals(q.getId())) {
				question.setAnswer(q.getAnswer());
				question.setAnswerId(q.getAnswerId());
				WorkspaceManager.get(workspace).addAnswer(question.sendResponse());
				return q;
			}
		}
		return q;
	}
}
