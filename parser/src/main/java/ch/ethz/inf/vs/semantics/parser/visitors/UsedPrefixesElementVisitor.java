package ch.ethz.inf.vs.semantics.parser.visitors;

import ch.ethz.inf.vs.semantics.parser.elements.Iri;
import ch.ethz.inf.vs.semantics.parser.elements.Prefix;

import java.util.HashSet;
import java.util.Set;

public class UsedPrefixesElementVisitor extends N3BaseElementVisitor<Set<Prefix>> {
	@Override
	public Set<Prefix> defaultResult() {
		return new HashSet<Prefix>();
	}

	@Override
	public Set<Prefix> aggregateResult(Set<Prefix> result, Set<Prefix> cResult) {
		if (cResult != null) {
			result.addAll(cResult);
		}
		return result;
	}

	@Override
	public Set<Prefix> visitIri(Iri iri) {
		HashSet<Prefix> list = new HashSet<Prefix>();
		Prefix p = iri.getPrefix();
		if (p != null) {
			list.add(p);
		}
		return list;
	}
}
