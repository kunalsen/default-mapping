import org.semanticweb.owlapi.model.OWLNamedIndividual;


public class ClassCandidate {
		private OWLNamedIndividual a;

		public ClassCandidate(OWLNamedIndividual a) {
			super();
			this.a = a;
		}

		public OWLNamedIndividual getA() {
			return a;
		}

		public void setA(OWLNamedIndividual a) {
			this.a = a;
		}
		
		@Override
		public boolean equals(Object obj) {
			ClassCandidate b = (ClassCandidate)obj;
			
		 return a.toString().equals(b.getA().toString());
		}
		
		@Override
		public String toString() {
			return a.toString();
		}
}
