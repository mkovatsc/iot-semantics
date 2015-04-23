package ch.ethz.inf.vs.semantics.parser;

import ch.ethz.inf.vs.semantics.parser.elements.N3Document;
import ch.ethz.inf.vs.semantics.parser.elements.N3Element;
import ch.ethz.inf.vs.semantics.parser.notation3.N3BaseVisitor;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Lexer;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Parser;
import ch.ethz.inf.vs.semantics.parser.visitors.HintsVisitor;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class N3Utils {

	public static Set<String> getHints(String fileconent) {
		HashSet<String> e = new HashSet<String>();
		N3Document doc = parseN3Document(fileconent);
		new HintsVisitor(e).visitN3Document(doc);
		return e;
	}

	public static String replaceN3Prefix(String file, String oldprefix, String newprefix, String namespace) {
		N3Lexer nl = new N3Lexer(new ANTLRInputStream(file));
		CommonTokenStream ts = new CommonTokenStream(nl);
		N3Parser np = new N3Parser(ts);

		RenamePrefix rp = new RenamePrefix(oldprefix, newprefix, namespace);
		List<ReplaceString> items = rp.visit(np.n3Doc());
		StringBuilder sb = new StringBuilder();
		int last = 0;
		for (ReplaceString rs : items) {
			String token = file.substring(last, rs.startIndex);
			last = rs.stopIndex;
			sb.append(token);
			sb.append(rs.s);
		}
		String token = file.substring(last, file.length());
		sb.append(token);
		return sb.toString();
	}

	public static N3Document parseN3Document(String input) {
		N3Lexer nl = new N3Lexer(new ANTLRInputStream(input));
		CommonTokenStream ts = new CommonTokenStream(nl);
		N3Parser np = new N3Parser(ts);
		N3Parser.N3DocContext doc = np.n3Doc();
		if (np.getNumberOfSyntaxErrors() > 0) {
			throw new RuntimeException("Invalid input");
		}
		N3TransformationVisitor tv = new N3TransformationVisitor();
		return tv.visitN3Doc(doc);
	}

	static class ReplaceString {

		private final int startIndex;
		private final int stopIndex;
		private final String s;

		public ReplaceString(int startIndex, int stopIndex, String s) {

			this.startIndex = startIndex;
			this.stopIndex = stopIndex;
			this.s = s;
		}
	}

	@SuppressWarnings("unchecked")
	static public <E> void replace(List<E> l, N3Element original, N3Element replace) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).equals(original)) {
				l.set(i, (E) replace);
			}
		}
	}

	static class RenamePrefix extends N3BaseVisitor<List<ReplaceString>> {

		private final String oldprefix;
		private final String newprefix;
		private final String namespace;

		public RenamePrefix(String oldprefix, String newprefix, String namespace) {
			super();
			this.oldprefix = oldprefix;
			this.newprefix = newprefix;
			this.namespace = namespace;
		}

		@Override
		public List<ReplaceString> visitPrefixID(@NotNull N3Parser.PrefixIDContext ctx) {

			TerminalNode name = ctx.PNAME_NS();
			if (name.getText().startsWith(oldprefix)) {
				TerminalNode uri = ctx.IRIREF();
				List<ReplaceString> repl = new ArrayList<ReplaceString>();
				repl.add(new ReplaceString(name.getSymbol().getStartIndex(), name.getSymbol().getStopIndex() + 1, newprefix));
				repl.add(new ReplaceString(uri.getSymbol().getStartIndex(), uri.getSymbol().getStopIndex() + 1, "<" + namespace + ">"));
				return repl;
			}

			return null;
		}

		@Override
		public List<ReplaceString> visitIri(@NotNull N3Parser.IriContext ctx) {
			if (ctx.getText().startsWith(oldprefix)) {

				List<ReplaceString> repl = new ArrayList<ReplaceString>();
				int start = ctx.getStart().getStartIndex();
				repl.add(new ReplaceString(start, start + oldprefix.length(), newprefix));
				return repl;
			}
			return null;
		}

		@Override
		protected List<ReplaceString> aggregateResult(List<ReplaceString> aggregate, List<ReplaceString> nextResult) {
			if (aggregate == null) {
				return nextResult;
			} else if (nextResult != null) {
				aggregate.addAll(nextResult);
			}
			return aggregate;
		}
	}

}
