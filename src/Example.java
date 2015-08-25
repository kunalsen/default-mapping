import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;

public class Example {

    public static void main(String[] args) throws OWLOntologyStorageException,
                        OWLOntologyCreationException, Exception {
    	//Logger.getLogger("org.semanticweb.elk").setLevel(Level.OFF);
        OWLOntologyManager inputOntologyManager = OWLManager.createOWLOntologyManager();
      
        OWLOntologyManager outputOntologyManager = OWLManager.createOWLOntologyManager();
        		
        // Load your ontology.
        File file = new File("/media/kunal/kunal/ontologies/people.owl");
        OWLOntology ont = inputOntologyManager.loadOntologyFromOntologyDocument(file);

        // Create an ELK reasoner.
        OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        
        // Classify the ontology.
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.CLASS_ASSERTIONS);
        //reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS);
        
        System.out.println(reasoner.isConsistent());	
        for (OWLClass owlClass : ont.getClassesInSignature()) {
        	System.out.println("Printing instances of "+owlClass);
			System.out.println(reasoner.getInstances(owlClass, false));
			
		}
       
        reasoner.dispose();
    }
}
