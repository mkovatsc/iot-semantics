package ch.ethz.inf.vs.semantics.ide.domain;

public class SemanticsErrors {

	private int line;
	private int charPositionInLine;
	private String msg;

	public SemanticsErrors() {

	}

	public SemanticsErrors(int line, int charPositionInLine, String msg) {

		this.setLine(line);
		this.setCharPositionInLine(charPositionInLine);
		this.setMsg(msg);
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCharPositionInLine() {
		return charPositionInLine;
	}

	public void setCharPositionInLine(int charPositionInLine) {
		this.charPositionInLine = charPositionInLine;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "line " + line + ":" + charPositionInLine + " " + msg;
	}
}
