package ch.ethz.inf.vs.semantics.server.semantics;

public interface SemanticQueryHandler {
	public void onResult(String result);

	public void onError(String result);
}
