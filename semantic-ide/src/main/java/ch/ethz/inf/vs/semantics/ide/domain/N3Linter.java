package ch.ethz.inf.vs.semantics.ide.domain;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;

import ch.ethz.inf.vs.semantics.parser.notation3.N3Lexer;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Parser;

import java.util.ArrayList;
import java.util.BitSet;

public class N3Linter {

	public static ArrayList<SemanticsErrors> lint(String input) {
		ArrayList<SemanticsErrors> errors = new ArrayList<SemanticsErrors>();
		N3Lexer nl = new N3Lexer(new ANTLRInputStream(input));
		CommonTokenStream ts = new CommonTokenStream(nl);
		N3Parser np = new N3Parser(ts);
		np.addErrorListener(new ANTLRErrorListener() {
			@Override
			public void syntaxError(@NotNull Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, @NotNull String msg, RecognitionException e) {
				errors.add(new SemanticsErrors(line, charPositionInLine, msg));

			}

			@Override
			public void reportAmbiguity(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, @NotNull ATNConfigSet configs) {

			}

			@Override
			public void reportAttemptingFullContext(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, @NotNull ATNConfigSet configs) {

			}

			@Override
			public void reportContextSensitivity(@NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, int prediction, @NotNull ATNConfigSet configs) {

			}
		});
		np.n3Doc();
		return errors;
	}

}
