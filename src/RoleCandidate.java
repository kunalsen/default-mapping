import org.semanticweb.owlapi.model.OWLNamedIndividual;


public class RoleCandidate {
	private OWLNamedIndividual a1;
	private OWLNamedIndividual a2;
	
	public RoleCandidate(OWLNamedIndividual a1, OWLNamedIndividual a2) {
		super();
		this.a1 = a1;
		this.a2 = a2;
	}
	@Override
	public boolean equals(Object obj) {
		RoleCandidate cand = (RoleCandidate) obj;
		if(cand.getA1().equals(a1) && cand.getA2().equals(a2)){
			return true;
		}
		return false;
	}
	public OWLNamedIndividual getA1() {
		return a1;
	}
	public void setA1(OWLNamedIndividual a1) {
		this.a1 = a1;
	}
	public OWLNamedIndividual getA2() {
		return a2;
	}
	public void setA2(OWLNamedIndividual a2) {
		this.a2 = a2;
	}
	
	
	@Override
	public String toString() {
		// 
		return "("+a1+", "+a2+")";
	}
	
}
