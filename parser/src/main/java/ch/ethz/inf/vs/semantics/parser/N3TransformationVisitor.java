package ch.ethz.inf.vs.semantics.parser;

import ch.ethz.inf.vs.semantics.parser.elements.*;
import ch.ethz.inf.vs.semantics.parser.notation3.N3BaseVisitor;
import ch.ethz.inf.vs.semantics.parser.notation3.N3Parser;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;

public class N3TransformationVisitor extends N3BaseVisitor<Object> {
	private N3Document n3doc;

	@Override
	public Iri visitIri(@NotNull N3Parser.IriContext ctx) {
		return new Iri(n3doc, ctx.getText());
	}

	@Override
	public Exvar visitExvar(@NotNull N3Parser.ExvarContext ctx) {
		return new Exvar(ctx.getText());
	}

	@Override
	public Literal visitLiteral(@NotNull N3Parser.LiteralContext ctx) {
		return new Literal(ctx.getText());
	}

	@Override
	public BlankNode visitBlankNode(@NotNull N3Parser.BlankNodeContext ctx) {
		return new BlankNode(ctx.getText());
	}

	@Override
	public BlankNodePropertyList visitBlankNodePropertyList(@NotNull N3Parser.BlankNodePropertyListContext ctx) {
		BlankNodePropertyList list = new BlankNodePropertyList();
		list.addAll(visitVerbObjectList(ctx.verbObjectList()));
		return list;
	}

	@Override
	public ArrayList<VerbObject> visitVerbObjectList(@NotNull N3Parser.VerbObjectListContext ctx) {
		ArrayList<VerbObject> verbObjects = new ArrayList<VerbObject>();
		for (N3Parser.VerbObjectContext vo : ctx.verbObject()) {
			verbObjects.add(visitVerbObject(vo));
		}
		return verbObjects;
	}

	@Override
	public VerbObject visitVerbObject(@NotNull N3Parser.VerbObjectContext ctx) {
		return new VerbObject(visitVerb(ctx.v), visitObjectList(ctx.o));
	}

	@Override
	public N3Element.Object visitObjectList(@NotNull N3Parser.ObjectListContext ctx) {
		ObjectList objects = new ObjectList();
		for (N3Parser.ObjectContext o : ctx.object()) {
			objects.add(visitObject(o));
		}
		if (objects.size() == 1) {
			return objects.get(0);
		}
		return objects;
	}

	@Override
	public N3Element.Verb visitVerb(@NotNull N3Parser.VerbContext ctx) {
		N3Element.Verb verb = (N3Element.Verb) super.visitVerb(ctx);
		if (verb == null) {
			verb = new Verb(ctx.getText());
		}
		return verb;
	}

	@Override
	public N3Element.Object visitObject(@NotNull N3Parser.ObjectContext ctx) {
		return (N3Element.Object) super.visitObject(ctx);
	}

	@Override
	public N3Element.Object visitCollection(@NotNull N3Parser.CollectionContext ctx) {
		ObjectCollection objects = new ObjectCollection();
		for (N3Parser.ObjectContext o : ctx.object()) {
			objects.add(visitObject(o));
		}
		return objects;
	}

	@Override
	public Object visitFormula(@NotNull N3Parser.FormulaContext ctx) {
		Formula f = new Formula();
		f.addAll(visitStatements(ctx.statements()));
		return f;
	}

	@Override
	public Statements visitStatements(@NotNull N3Parser.StatementsContext ctx) {
		Statements statements = new Statements();
		if (ctx == null) {
			return statements;
		}
		N3Parser.BooleanLiteralContext booleanLiteral = ctx.booleanLiteral();
		if (booleanLiteral != null) {
			statements.add(visitBooleanLiteral(booleanLiteral));
		}

		N3Parser.TripleContext triple = ctx.triple();
		if (triple != null) {
			statements.add(visitTriple(triple));
		}

		N3Parser.ClassificationContext classification = ctx.classification();
		if (classification != null) {
			statements.add(visitClassification(classification));
		}

		N3Parser.StatementsContext next_statement = ctx.statements();
		if (next_statement != null) {
			statements.addAll(visitStatements(next_statement));
		}
		return statements;
	}

	@Override
	public BooleanLiteral visitBooleanLiteral(@NotNull N3Parser.BooleanLiteralContext ctx) {
		return new BooleanLiteral(ctx.getText());
	}

	@Override
	public N3Element.Statement visitTriple(@NotNull N3Parser.TripleContext ctx) {
		N3Parser.VerbObjectListContext vo = ctx.verbObjectList();
		ArrayList<VerbObject> v = null;
		if (vo != null) {
			v = visitVerbObjectList(vo);
		}
		N3Parser.BlankNodePropertyListContext blanknode = ctx.blankNodePropertyList();
		if (blanknode != null) {
			BlankNodePropertyList bn = visitBlankNodePropertyList(blanknode);
			if (vo == null) {
				return bn;
			}
			return new RDFResource(bn, v);
		}
		N3Element.Subject subj = visitSubject(ctx.subject());

		return new RDFResource(subj, v);
	}

	@Override
	public N3Element.Subject visitSubject(@NotNull N3Parser.SubjectContext ctx) {
		return (N3Element.Subject) super.visitSubject(ctx);
	}

	@Override
	public Classification visitClassification(@NotNull N3Parser.ClassificationContext ctx) {
		return new Classification(ctx.LANGTAG().getText(), visitObjectList(ctx.objectList()), visitStatements(ctx.statements()));
	}

	@Override
	public N3Document visitN3Doc(@NotNull N3Parser.N3DocContext ctx) {
		n3doc = new N3Document();
		for (N3Parser.DirectiveContext d : ctx.directive()) {
			N3Parser.PrefixIDContext prefix = d.prefixID();
			if (prefix != null) {
				n3doc.addPrefix(new Prefix(prefix.PNAME_NS().getText(), prefix.IRIREF().getText()));
			}
		}
		n3doc.statements.addAll(visitStatements(ctx.statements()));

		return n3doc;
	}
}
