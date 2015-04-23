package ch.ethz.inf.vs.semantics.ide.rest;

import ch.ethz.inf.vs.semantics.ide.domain.N3Linter;
import ch.ethz.inf.vs.semantics.ide.domain.Query;
import ch.ethz.inf.vs.semantics.ide.domain.QueryResult;
import ch.ethz.inf.vs.semantics.ide.domain.SemanticsErrors;
import ch.ethz.inf.vs.semantics.ide.workspace.WorkspaceManager;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.ArrayList;
import java.util.Collection;

@Component
@RestxResource
public class QueryResource {

	@POST("/{workspace}/queries")
	@PermitAll
	public static Query postQuery(int workspace, Query msg) {
		return WorkspaceManager.get(workspace).addQuery(msg);
	}

	@GET("/{workspace}/queries")
	@PermitAll
	public Collection<Query> queries(int workspace) {
		return WorkspaceManager.get(workspace).getQueries();
	}

	@GET("/{workspace}/queries/{id}")
	@PermitAll
	public Query getQuery(int workspace, int id) {
		return WorkspaceManager.get(workspace).getQuery(id);
	}

	@DELETE("/{workspace}/queries/{id}")
	@PermitAll
	public Query deleteQuery(int workspace, int id) {
		return WorkspaceManager.get(workspace).deleteQuery(id);
	}

	@PUT("/{workspace}/queries/{id}")
	@PermitAll
	public Query putQuery(int workspace, int id, Query msg) {
		return WorkspaceManager.get(workspace).updateQuery(id, msg);
	}

	@GET("/{workspace}/hints")
	@PermitAll
	public Collection<String> hints(int workspace) {
		return WorkspaceManager.get(workspace).getHints();
	}

	@POST("/{workspace}/query/{raw}")
	@PermitAll
	public QueryResult query(int workspace, Query query, boolean raw) {
		String result = null;

		try {
			result = WorkspaceManager.get(workspace).executeQuery(query.getQuery(), query.getInput(), raw);
		} catch (Exception ex) {
			String error = "";
			ArrayList<SemanticsErrors> errquery = N3Linter.lint(query.getQuery());
			ArrayList<SemanticsErrors> errinput = N3Linter.lint(query.getInput());
			if (!errquery.isEmpty()) {

				error += "Query:\n" + toString(errquery) + "\n";
			}
			if (!errinput.isEmpty()) {
				error += "Input:\n" + toString(errinput) + "\n";
			}
			if (ex.getMessage() != null) {
				error = ex.getMessage() + "\n\n" + error;
			}

			return new QueryResult(error, errquery, errinput);
		}

		return new QueryResult(result, null, null);
	}

	@POST("/{workspace}/plan")
	@PermitAll
	public QueryResult plan(int workspace, Query query) {
		String result = null;

		try {
			result = WorkspaceManager.get(workspace).planMashup(query.getQuery(), query.getInput());
		} catch (Exception ex) {
			String error = "";
			ArrayList<SemanticsErrors> errquery = N3Linter.lint(query.getQuery());
			ArrayList<SemanticsErrors> errinput = N3Linter.lint(query.getInput());
			if (!errquery.isEmpty()) {

				error += "Query:\n" + toString(errquery) + "\n";
			}
			if (!errinput.isEmpty()) {
				error += "Input:\n" + toString(errinput) + "\n";
			}
			if (ex.getMessage() != null) {
				error = ex.getMessage() + "\n\n" + error;
			}

			return new QueryResult(error, errquery, errinput);
		}

		return new QueryResult(result, null, null);
	}

	private String toString(ArrayList<SemanticsErrors> errquery) {

		return errquery.stream().map((x) -> x.toString()).reduce("", (x, y) -> x + "\n" + y).trim();
	}

}
