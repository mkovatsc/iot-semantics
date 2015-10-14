package ch.ethz.inf.vs.semantics.ide.domain;

import java.util.ArrayList;

public class QueryResult {

	private String result;
	private ArrayList<SemanticsErrors> errquery;
	private ArrayList<SemanticsErrors> errinput;

	public QueryResult() {

	}

	public QueryResult(String result, ArrayList<SemanticsErrors> errquery, ArrayList<SemanticsErrors> errinput) {

		this.setResult(result);
		this.setErrquery(errquery);
		this.setErrinput(errinput);
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ArrayList<SemanticsErrors> getErrquery() {
		return errquery;
	}

	public void setErrquery(ArrayList<SemanticsErrors> errquery) {
		this.errquery = errquery;
	}

	public ArrayList<SemanticsErrors> getErrinput() {
		return errinput;
	}

	public void setErrinput(ArrayList<SemanticsErrors> errinput) {
		this.errinput = errinput;
	}
}
