

import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;


import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.LogMap2_RepairFacility;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;
import uk.ac.ox.krr.logmap2.owlapi.SynchronizedOWLManager;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 * Example of using LogMap's matching facility 
 *  * 
 * @author Ernesto
 *
 */
public class LogMapTest {

	
	OWLOntology onto1;
	OWLOntology onto2;

	OWLOntologyManager onto_manager;
	
	public LogMapTest(){
		
		try{
						
			String onto1_iri = "file:/media/kunal/kunal/ontologies/ekaw.owl";
			String onto2_iri = "file:/media/kunal/kunal/ontologies/cmt.owl";
			
			onto_manager = OWLManager.createOWLOntologyManager();
			
			onto1 = onto_manager.loadOntology(IRI.create(onto1_iri));
			onto2 = onto_manager.loadOntology(IRI.create(onto2_iri));
			
			
			LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1, onto2);
			//Optionally LogMap also accepts the IRI strings as input 
			//LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1_iri, onto2_iri);
			
			//Set of mappings computed my LogMap
			Set<MappingObjectStr> logmap2_mappings = logmap2.getLogmap2_Mappings();
			
			System.out.println("Number of mappings computed by LogMap: " + logmap2_mappings.size());
			System.out.println(logmap2_mappings);
			
			
			/*
			 * Accessing mapping objects
			 *  
			for (MappingObjectStr mapping: logmap2_mappings){
				System.out.println("Mapping: ");
				System.out.println("\t"+ mapping.getIRIStrEnt1());
				System.out.println("\t"+ mapping.getIRIStrEnt2());
				System.out.println("\t"+ mapping.getConfidence());
				
				//MappingObjectStr.EQ or MappingObjectStr.SUB or MappingObjectStr.SUP
				System.out.println("\t"+ mapping.getMappingDirection()); //Utilities.EQ;
				
				//MappingObjectStr.CLASSES or MappingObjectStr.OBJECTPROPERTIES or MappingObjectStr.DATAPROPERTIES or MappingObjectStr.INSTANCES
				System.out.println("\t"+ mapping.getTypeOfMapping());
				
			}*/
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void testMarriageMapping(){
		try{
			OWLOntology onto1;
			OWLOntology onto2;

			OWLOntologyManager onto_manager;
			String onto1_iri = "file:/media/kunal/kunal/ontologies/marriage1.owl";
			String onto2_iri = "file:/media/kunal/kunal/ontologies/marriage2.owl";
			
			onto_manager = OWLManager.createOWLOntologyManager();
			
			onto1 = onto_manager.loadOntology(IRI.create(onto1_iri));
			onto2 = onto_manager.loadOntology(IRI.create(onto2_iri));
			
			
			LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1, onto2);
			//Optionally LogMap also accepts the IRI strings as input 
			//LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1_iri, onto2_iri);
			
			//Set of mappings computed my LogMap
			Set<MappingObjectStr> mappings = logmap2.getLogmap2_Mappings();
			
			System.out.println("Number of mappings computed by LogMap: " + mappings.size());
			System.out.println(mappings);
			LogMap2_RepairFacility logmap2_repair = new LogMap2_RepairFacility(onto1, onto2, mappings, false, false, false, "/media/kunal/kunal/logmap/marriage-output.owl");
			//System.out.println(logmap2_repair.getOWLOntology4CleanMappings(mappings));
			OWLOntology mappingsOntology = logmap2_repair.getOWLOntology4CleanMappings(mappings);
			
			Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
			axioms.addAll(onto1.getAxioms());
			axioms.addAll(onto2.getAxioms());
			axioms.addAll(mappingsOntology.getAxioms());
			System.out.println(mappingsOntology.getAxioms());		
			//OWLOntologyManager managerMerged = OWLManager.createOWLOntologyManager();
			OWLOntologyManager managerMerged = SynchronizedOWLManager.createOWLOntologyManager();
			OWLOntology mergedOntology = managerMerged.createOntology(axioms, IRI.create("http://daselab.org/integration.owl"));
		
			//logmap2_repair.checkSatisfiabilityInputMappings();
			//logmap2_repair.checkSatisfiabilityInputMappings();
			System.out.println(mergedOntology);
			OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
	        OWLReasoner reasoner = reasonerFactory.createReasoner(mergedOntology);
	        System.out.println(reasoner.isConsistent());
			
			Set<MappingObjectStr> repairedMappings = logmap2_repair.getCleanMappings();
			
			System.out.println("Size of repaired mappings "+repairedMappings.size());
			/*
			 * Accessing mapping objects
			 *  
			for (MappingObjectStr mapping: logmap2_mappings){
				System.out.println("Mapping: ");
				System.out.println("\t"+ mapping.getIRIStrEnt1());
				System.out.println("\t"+ mapping.getIRIStrEnt2());
				System.out.println("\t"+ mapping.getConfidence());
				
				//MappingObjectStr.EQ or MappingObjectStr.SUB or MappingObjectStr.SUP
				System.out.println("\t"+ mapping.getMappingDirection()); //Utilities.EQ;
				
				//MappingObjectStr.CLASSES or MappingObjectStr.OBJECTPROPERTIES or MappingObjectStr.DATAPROPERTIES or MappingObjectStr.INSTANCES
				System.out.println("\t"+ mapping.getTypeOfMapping());
				
			}*/
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//new LogMapTest();
		testMarriageMapping();

	}

}