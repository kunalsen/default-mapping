import java.io.File;
import java.util.Set;

import org.ihmc.owl4.OWLOntologyTranslator;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.ox.krr.logmap2.reasoning.HermiTAccess;


public class ParaconsistentTest {
	public static void main(String[] args) throws Exception{
		//testMarriage();
		testBio();
	}
	
	private static void testMarriage()throws Exception{
		testPara("/media/kunal/kunal/logmap/marriage.owl", "/meida/kunal/kunal/logmap/moutput.owl");
	}
	private static void testBio()throws Exception{
		testPara("/media/kunal/kunal/logmap/bio5.owl", "/meida/kunal/kunal/logmap/boutput.owl");
	}
	private static void testPara(String filepath, String outputPath) throws Exception{
		OWLOntologyTranslator translator = new OWLOntologyTranslator();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		IRI inputIRI = IRI.create(new File(filepath));
		OWLOntology onto = manager.loadOntology(inputIRI);
		
		IRI outputIRI = IRI.create(new File(outputPath));
		
		OWLOntology outputOnto = translator.InitializeOutputOntology(inputIRI, outputIRI);
		
		translator.TranslateOntology(onto, outputOnto);
		Set<OWLOntology> outputOntologies = translator.OutputOntologies();
		
		System.out.println(outputOntologies.size());
		OWLOntology outputOntology = outputOntologies.iterator().next();
		
		HermiTAccess hermitAccess = new HermiTAccess(manager, outputOntology, true);
		OWLReasoner reasoner = hermitAccess.getReasoner();
		
		OWLClass thing = manager.getOWLDataFactory().getOWLThing();
		Set<OWLNamedIndividual> inds =
		reasoner.getInstances(thing, false).getFlattened();
		for (OWLNamedIndividual i : inds) {
		System.out.println(i);
		Set<OWLClass> set =
		reasoner.getTypes(i, false).getFlattened();
		for (OWLClass c : set) {
		System.out.println("\t" + c);
		}
		}
		
		
		Set<OWLNamedIndividual> indis =
				outputOntology.getIndividualsInSignature();
				Set<OWLObjectProperty> props =
				outputOntology.getObjectPropertiesInSignature();
				for (OWLNamedIndividual i : indis)
				{
				for (OWLObjectProperty p : props)
				{
				Set<OWLNamedIndividual> values =
				reasoner.getObjectPropertyValues(i, p).getFlattened();
				for (OWLNamedIndividual v : values)
				{
				System.out.println(i + " " + p + " " + v + "\n");
				}
				}
				}
		
	}
}
