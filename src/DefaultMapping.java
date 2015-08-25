import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.LogMap2_RepairFacility;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;
import uk.ac.ox.krr.logmap2.owlapi.SynchronizedOWLManager;
import uk.ac.ox.krr.logmap2.reasoning.HermiTAccess;

import com.google.common.collect.Sets;


public class DefaultMapping {
	
	
	public void processMappingsForMarriage(OWLOntology ontology1, OWLOntology ontology2)throws Exception{
		// obtain the mappings
		Set<MappingObjectStr> mappings = getLogMapMappings(ontology1, ontology2);
		
		mappings = convertMappingsToSub(mappings);
		
		//System.out.println(mappings);
		Set<MappingObjectStr> defaultMappings = new HashSet<MappingObjectStr>();
		MappingObjectStr m = null;
		for(MappingObjectStr mapping: mappings){
			//if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				if(mapping.toString().contains("mike")){
					m = mapping;
					continue;
				}
				defaultMappings.add(mapping);
			//}
		}
		mappings.remove(m);
		saveMergedOntology("/media/kunal/kunal/logmap/marriage.owl", ontology1, ontology2,mappings);
		/*for(MappingObjectStr mapping: mappings){
			
		}*/
		
		//mergeTboxOnly(ontology1, ontology2, mappings, );
		List<OWLOntology> mappedOntologies = defaultMappingReasoning(ontology1, ontology2, mappings, defaultMappings);
		findMappedInstances(mappedOntologies, defaultMappings);
		
	}
	public void processMappingsForConference(OWLOntology ontology1, OWLOntology ontology2)throws Exception{
		// obtain the mappings
		Set<MappingObjectStr> mappings = getLogMapMappings(ontology1, ontology2);
		
		
		mappings = convertMappingsToSub(mappings);
		/*LogMap2_RepairFacility repair = new LogMap2_RepairFacility(ontology1, ontology2, mappings, false, true);
		Set<MappingObjectStr> repaired= repair.getCleanMappings();
		repair.checkSatisfiabilityInputMappings();
		repair.checkSatisfiabilityCleanMappings();
		System.out.println(repaired.size());
		for(MappingObjectStr m : mappings){
			if(!repaired.contains(m)){
				System.out.println(m);
			}
		}*/
		/*MappingObjectStr remove = null;
		for(MappingObjectStr map: mappings){
			if(map.toString().contains("Thoracic_Cavity")){
				remove =map;
			}
		}
		mappings.remove(remove);*/
		//saveMergedOntology("/media/kunal/kunal/logmap/bio3.owl", ontology1, ontology2,mappings);
		/*for(MappingObjectStr map: mappings){
			logOutput(map.toString());
		}*/
		System.out.println(mappings.size());
		Set<MappingObjectStr> defaultMappings = new HashSet<MappingObjectStr>();
		for(MappingObjectStr mapping: mappings){
			logOutput(mapping.toString());
			//System.out.println(mapping.getIRIStrEnt1());
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES && (mapping.getIRIStrEnt1().contains("Visceral_Pleura")||
					mapping.getIRIStrEnt1().contains("Thoracic_Cavity")||
					mapping.getIRIStrEnt1().contains("Pleural_Tissue") )){
				defaultMappings.add(mapping);
			}
			if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES && (mapping.getIRIStrEnt1().contains("Anatomic_Structure_Has_Location"))){
				defaultMappings.add(mapping);
			}
		}
		for(MappingObjectStr mapping: mappings){
			
		}
		System.out.println(defaultMappings);
		//mergeTboxOnly(ontology1, ontology2, mappings, );
		List<OWLOntology> mappedOntologies = defaultMappingReasoningBio(ontology1, ontology2, mappings, defaultMappings);
		findMappedInstances(mappedOntologies, defaultMappings, mappings, ontology2);
		
	}
	public void processMappingsForFood(OWLOntology ontology1, OWLOntology ontology2)throws Exception{
		// obtain the mappings
		Set<MappingObjectStr> mappings = getLogMapMappings(ontology1, ontology2);
		
		mappings = convertMappingsToSub(mappings);
		saveMergedOntology("/media/kunal/kunal/logmap/food.owl", ontology1, ontology2,mappings);
		/*System.out.println(mappings);
		Set<MappingObjectStr> defaultMappings = new HashSet<MappingObjectStr>();
		for(MappingObjectStr mapping: mappings){
			//if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				defaultMappings.add(mapping);
			//}
		}
		for(MappingObjectStr mapping: mappings){
			
		}
		
		//mergeTboxOnly(ontology1, ontology2, mappings, );
		List<OWLOntology> mappedOntologies = defaultMappingReasoning(ontology1, ontology2, mappings, defaultMappings);
		findMappedInstances(mappedOntologies, defaultMappings);*/
		
	}
	
	public void saveMergedOntology(String filepath, OWLOntology ont1, OWLOntology ont2, Set<MappingObjectStr> mappings)throws Exception{
		File file = new File(filepath);
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology output = man.createOntology();
		man.addAxioms(output,  ont1.getAxioms());
		man.addAxioms(output,  ont2.getAxioms());
		man.addAxioms(output, getTboxFromMappings(mappings));
		OWLOntologyFormat format = new OWLXMLOntologyFormat();
		HermiTAccess hermitAccess = new HermiTAccess(man, output, true);
	     
		 OWLReasoner reasonerHermit =hermitAccess.getReasoner();
	    // reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.OBJECT_PROPERTY_HIERARCHY);
	     //System.out.println(reasonerHermit.getUnsatisfiableClasses());
	     
		man.saveOntology(output, format,IRI.create(file));
	}
	public void findMappedInstances(List<OWLOntology> ontologies, Set<MappingObjectStr> defaultMappings) throws Exception{
		findMappedInstances(ontologies, defaultMappings, null, null);
	}
	public void findMappedInstances(List<OWLOntology> ontologies, Set<MappingObjectStr> defaultMappings, Set<MappingObjectStr> mappings, OWLOntology o2) throws Exception{
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLDataFactory dataFactory = man.getOWLDataFactory();
		Map<OWLClass, Set<ClassCandidate>> classesMap = new HashMap<OWLClass, Set<ClassCandidate>>();
		Map<OWLObjectProperty, Set<RoleCandidate>> rolesMap= new HashMap<OWLObjectProperty, Set<RoleCandidate>>();
		List<OWLClass> classes = new ArrayList<OWLClass>();
		List<OWLObjectProperty> props = new ArrayList<OWLObjectProperty>();				
		for(MappingObjectStr mapping: defaultMappings){
		
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				OWLClass cl = dataFactory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				classes.add(cl);
			}else if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				OWLObjectProperty prop = dataFactory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				props.add(prop);
			}
		}
		for(MappingObjectStr mapping: mappings){
			
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				OWLClass cl = dataFactory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				classes.add(cl);
			}else if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				OWLObjectProperty prop = dataFactory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				props.add(prop);
			}
		}
		if(o2 != null){
			Set<OWLClass> cls= o2.getClassesInSignature();
			classes = new ArrayList<OWLClass>(cls);
		}
		
		for(OWLOntology ont: ontologies){
			OWLOntology ontology = man.createOntology();
			man.addAxioms(ontology, ont.getAxioms());
			 HermiTAccess hermitAccess = new HermiTAccess(man, ontology, true);
		     OWLReasoner reasonerHermit =hermitAccess.getReasoner();
		     reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.OBJECT_PROPERTY_HIERARCHY);
		     System.out.println("Unsatisfiable CLasses :: "+reasonerHermit.getUnsatisfiableClasses());
		     for(OWLClass cl: classes){
		    	 NodeSet<OWLNamedIndividual> instances= reasonerHermit.getInstances(cl, false);
		    	 Set<ClassCandidate> cand = new HashSet<ClassCandidate>();
		    	 for(Node<OWLNamedIndividual> ind: instances){
		    		 cand.add(new ClassCandidate(ind.getRepresentativeElement()));
		    	 }
		    	
		    	 
		    	 if(!classesMap.containsKey(cl)){
		    		 classesMap.put(cl, cand);
		    	 }else{
		    		 
		    		 classesMap.put(cl, findIntersection(classesMap.get(cl), cand));
		    	 }
		     }
		   
		     for(OWLObjectProperty prop: props){
		    	 NodeSet<OWLNamedIndividual> instances= reasonerHermit.getInstances(reasonerHermit.getTopClassNode().getRepresentativeElement(), false);
		    	 Set<RoleCandidate> cand = new HashSet<RoleCandidate>();
		    	 for(Node<OWLNamedIndividual> ind: instances){
		    		 NodeSet<OWLNamedIndividual> individuals =reasonerHermit.getObjectPropertyValues(ind.getRepresentativeElement(), prop);
		    		 
		    		 for(Node<OWLNamedIndividual> rightInd: individuals){
		    			 cand.add(new RoleCandidate(ind.getRepresentativeElement(), rightInd.getRepresentativeElement()));
		    		 }
		    		
		    	 }
		    	 
		    	 if(!rolesMap.containsKey(prop)){
		    		 rolesMap.put(prop, cand);
		    	 }else{
		    		 
		    		 rolesMap.put(prop, findIntersectionRole(rolesMap.get(prop), cand));
		    	 }
		     }
		}
		System.out.println(classesMap);
		System.out.println(rolesMap);
		System.out.println("Printing all instances of mapped classes");
		for(OWLClass cl: classesMap.keySet()){
			List<ClassCandidate> list = new ArrayList<>();
			for(ClassCandidate cand: classesMap.get(cl)){
				//System.out.println("Printing all instances of class "+ cl);
				//System.out.println(cand);
				list.add(cand);
			}
			if(list.size()>0){
				System.out.println("Printing all instances of class "+ cl.toString().substring(cl.toString().indexOf("#")+1));
				for(ClassCandidate cand: list){
					System.out.println(cand);
				}
			}
		}
		
		System.out.println("Printing all instances of mapped properties");
		for(OWLObjectProperty prop: rolesMap.keySet()){
			
			for(RoleCandidate cand: rolesMap.get(prop)){
				System.out.println("Printing pairs that satisfy role "+ prop);
				System.out.println(cand);
			}
		}
	}
	
	private Set<ClassCandidate> findIntersection(Set<ClassCandidate> set1, Set<ClassCandidate> set2){
		Set<ClassCandidate> output = new HashSet<ClassCandidate>();
		/*System.out.println("Set 1: "+set1);
		System.out.println("Set 2: "+set2);*/
		for(ClassCandidate e: set1){
			for(ClassCandidate s: set2){
				if(e.equals(s)){
					output.add(e);
					break;
				}
			}
		}
		return output;
		
	}
	private Set<RoleCandidate> findIntersectionRole(Set<RoleCandidate> set1, Set<RoleCandidate> set2){
		Set<RoleCandidate> output = new HashSet<RoleCandidate>();
		/*System.out.println("Set 1: "+set1);
		System.out.println("Set 2: "+set2);*/
		for(RoleCandidate e: set1){
			for(RoleCandidate s: set2){
				if(e.equals(s)){
					output.add(e);
					break;
				}
			}
		}
		return output;
		
	}
	private void mergeTboxOnly(OWLOntology ont1, OWLOntology ont2, Set<MappingObjectStr> mappings, Set<MappingObjectStr> defaultMappings) throws Exception{
		Set<OWLAxiom> tboxAxioms = ont1.getTBoxAxioms(false);
		tboxAxioms.addAll(ont2.getTBoxAxioms(false));
		//System.out.println(ont2.getABoxAxioms(false));
		//convertMappings to subclass
		Set<MappingObjectStr> subMappings = convertMappingsToSub(mappings);
		Set<OWLAxiom> tboxMappingAxioms = getTboxFromMappings(subMappings);
		LogMap2_RepairFacility logmap2_repair = new LogMap2_RepairFacility(ont1, ont2, subMappings, false, false);
		//System.out.println(logmap2_repair.getOWLOntology4CleanMappings(mappings));
		OWLOntology mappingsOntology = logmap2_repair.getOWLOntology4CleanMappings(subMappings);
		
		
		tboxAxioms.addAll(tboxMappingAxioms);
		
		System.out.println(tboxMappingAxioms);
		OWLOntologyManager managerMerged = SynchronizedOWLManager.createOWLOntologyManager();
		OWLOntology mergedOntology = managerMerged.createOntology(tboxAxioms, IRI.create("http://daselab.org/integration.owl"));
		
		OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(mergedOntology);
        
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        for(OWLClass cl: ont1.getClassesInSignature()){
        	System.out.println("Superclasses of "+cl);
        	System.out.println(reasoner.getSuperClasses(cl, false));
        }
       /* for(OWLObjectProperty p: ont2.getObjectPropertiesInSignature()){
        	System.out.println("Super roles of "+p);
        	System.out.println(reasoner.getSubObjectProperties(p, false));
        	
        }*/
        
        HermiTAccess hermitAccess = new HermiTAccess(managerMerged, mergedOntology, true);
       OWLReasoner reasonerHermit =hermitAccess.getReasoner();
       reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.CLASS_ASSERTIONS);
       System.out.println(mergedOntology.getAxioms());
       for(OWLObjectProperty p: mergedOntology.getObjectPropertiesInSignature()){
       	System.out.println("Super roles of "+p);
       	System.out.println(reasonerHermit.getSubObjectProperties(p, true));
       	
       	System.out.println(reasonerHermit.getEquivalentObjectProperties(p));
       }
       defaultReasoning(ont1, ont2, mappings, mappings);
		
	}
	
	private Set<OWLAxiom> getTboxFromMappings(Set<MappingObjectStr> mappings)throws Exception{
		OWLOntologyManager managerMerged = SynchronizedOWLManager.createOWLOntologyManager();
		OWLDataFactory factory = managerMerged.getOWLDataFactory();
		OWLOntology ont = managerMerged.createOntology();
		for(MappingObjectStr mapping: mappings){
			OWLAxiom axiom = null;
			//System.out.println("================================ Type of mapping "+mapping.getTypeOfMapping());
			//System.out.println("================================ Type of relation "+mapping.getMappingDirection());
			if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				System.out.println("Inside");
				OWLObjectProperty prop1= factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
				OWLObjectProperty prop2= factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				
				if(mapping.getMappingDirection() == MappingObjectStr.SUB){
					System.out.println("Inside1");
					axiom = factory.getOWLSubObjectPropertyOfAxiom(prop1, prop2);
				}else if(mapping.getMappingDirection() == MappingObjectStr.SUP){
					axiom = factory.getOWLSubObjectPropertyOfAxiom(prop2, prop1);
				}else{
					System.out.println("Inside2");
					axiom = factory.getOWLEquivalentObjectPropertiesAxiom(prop1, prop2);
				}
			}
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				
				OWLClass class1= factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
				OWLClass class2= factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				
				if(mapping.getMappingDirection() == MappingObjectStr.SUB){
					axiom = factory.getOWLSubClassOfAxiom(class1, class2);
				}else if(mapping.getMappingDirection() == MappingObjectStr.SUP){
					axiom = factory.getOWLSubClassOfAxiom(class2, class1);
				}else{
					axiom = factory.getOWLEquivalentClassesAxiom(class1, class2);
				}
			}
			if(axiom != null){
				managerMerged.addAxiom(ont, axiom);
			}
		}
		return ont.getAxioms();
	}
	private Set<MappingObjectStr> convertMappingsToSub(Set<MappingObjectStr> inputMappings){
		Set<MappingObjectStr> outputMappings= new HashSet<MappingObjectStr>();
		
		for(MappingObjectStr inputMapping: inputMappings){
			MappingObjectStr mapping = null;
			//System.out.println(inputMapping);
			if(inputMapping.getMappingDirection() == MappingObjectStr.EQ){
				mapping = new MappingObjectStr(inputMapping.getIRIStrEnt1(), inputMapping.getIRIStrEnt2(), inputMapping.getConfidence(), MappingObjectStr.SUB, inputMapping.getTypeOfMapping());
			}else{
				mapping = inputMapping;
			}
			outputMappings.add(mapping);
		}
		return outputMappings;
	}
	private void defaultReasoning(OWLOntology o1, OWLOntology o2, Set<MappingObjectStr> mappings, Set<MappingObjectStr> defaultMapping)throws Exception{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology();
		manager.addAxioms(ontology, o2.getAxioms());
		
		OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
		
		HermiTAccess hermitAccess = new HermiTAccess(manager1, o1, true);
	       OWLReasoner reasonerHermit =hermitAccess.getReasoner();
	       reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.CLASS_ASSERTIONS);
	    OWLDataFactory factory = manager1.getOWLDataFactory();   
	    
		for(MappingObjectStr mapping: mappings){
			if(!defaultMapping.contains(mapping)){
				if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
					OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
					OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getInstances(left, false);
					for(Node<OWLNamedIndividual> ind : instances){
						OWLClassAssertionAxiom axiom = factory.getOWLClassAssertionAxiom(right, ind.getRepresentativeElement());
						manager.addAxiom(ontology, axiom);
					}
				}
				if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
					OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
					OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
					Node<OWLClass> top = reasonerHermit.getTopClassNode();
					NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
					for(Node<OWLNamedIndividual> individual : allIndividuals){
						NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
						for(Node<OWLNamedIndividual> ind : instances){
							OWLObjectPropertyAssertionAxiom axiom = factory.getOWLObjectPropertyAssertionAxiom(right, individual.getRepresentativeElement(), ind.getRepresentativeElement());
							manager.addAxiom(ontology, axiom);
						}
					}
					
					
				}
			}
		}
		
		
		//process default mappings
		Map<String, Set<RoleCandidate>> roleCandidates = new HashMap<String, Set<RoleCandidate>> ();
		Map<String, Set<Set<RoleCandidate>>> roleCandidatesPSet = new HashMap<String, Set<Set<RoleCandidate>>> ();
		
		for(MappingObjectStr mapping: defaultMapping){
		
			if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
				OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				Node<OWLClass> top = reasonerHermit.getTopClassNode();
				NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
				for(Node<OWLNamedIndividual> individual : allIndividuals){
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
					for(Node<OWLNamedIndividual> ind : instances){
						if(roleCandidates.get(mapping.toString()) == null){
							roleCandidates.put(mapping.toString(), new HashSet<RoleCandidate>());							
						}
						roleCandidates.get(mapping.toString()).add(new RoleCandidate(individual.getRepresentativeElement(), ind.getRepresentativeElement()));
					}
				}

			}
			
			Set<RoleCandidate> roleAssert = roleCandidates.get(mapping.toString());
			roleCandidatesPSet.put(mapping.toString(), Sets.powerSet(roleAssert));
			
			System.out.println(roleCandidates.get(mapping.toString()));
		}
		Set<Set<RoleCandidate>> successfulMappings = new HashSet<Set<RoleCandidate>>();
		for(MappingObjectStr mapping: defaultMapping){
			for(Set<RoleCandidate> roleCand : roleCandidatesPSet.get(mapping.toString())){
				OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				OWLOntologyManager man = OWLManager.createOWLOntologyManager();
				OWLOntology test = man.createOntology();
				for(RoleCandidate cand: roleCand){
					
					man.addAxioms(test, ontology.getAxioms());
					OWLObjectPropertyAssertionAxiom axiom = factory.getOWLObjectPropertyAssertionAxiom(right, cand.getA1(), cand.getA2());
					manager.addAxiom(test, axiom);
					
				}
				HermiTAccess hermitAccessTest = new HermiTAccess(man, test, true);
				OWLReasoner testReasoner = hermitAccessTest.getReasoner();
				if(testReasoner.isConsistent()){
					boolean add = true;
					
					if(add){
						
						successfulMappings.add(roleCand);
					}
				}
				
			}
		}
		
		
		Set<Set<RoleCandidate>> filteredMappings = new HashSet<Set<RoleCandidate>>();
		for(Set<RoleCandidate> oneMapping: successfulMappings){
			boolean add = true;
			System.out.println("One " +oneMapping);
			for(Set<RoleCandidate> existingMapping: filteredMappings){
				System.out.println("Existing :"+existingMapping);
				
				System.out.println("oneMapping.containsAll(existingMapping) "+oneMapping.containsAll(existingMapping));
				System.out.println("existingMapping.containsAll(oneMapping) "+existingMapping.containsAll(oneMapping));
				
				if(oneMapping.containsAll(existingMapping) && oneMapping.size() > existingMapping.size()){
					filteredMappings.remove(existingMapping);
					add = true;
					break;
				}
				if(existingMapping.containsAll(oneMapping)){
					add = false;
				}
			}
			if(add){
				filteredMappings.add(oneMapping);
			}
		}
		
		Set <Integer> a = Sets.newHashSet();
		Set <Integer> b = Sets.newHashSet();
		a.addAll(Arrays.asList(1,2,3));
		b.addAll(Arrays.asList(1,2));
		System.out.println("a contains b "+a.containsAll(b) );
		System.out.println("Successful Mappings "+successfulMappings);
		System.out.println("Filtered Mappings "+filteredMappings.size());
		
		
	}
	private List<OWLOntology> defaultMappingReasoning(OWLOntology o1, OWLOntology o2, Set<MappingObjectStr> mappings, Set<MappingObjectStr> defaultMapping)throws Exception{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology();
		manager.addAxioms(ontology, o2.getAxioms());
		
		List<OWLOntology> returnList = new ArrayList<OWLOntology>();
		List<MappingObjectStr> orderedMappings = new ArrayList<MappingObjectStr>();
		for(MappingObjectStr obj:defaultMapping){
			orderedMappings.add(obj);
		}
		
		OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
		
		HermiTAccess hermitAccess = new HermiTAccess(manager1, o1, true);
	       OWLReasoner reasonerHermit =hermitAccess.getReasoner();
	       reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.CLASS_ASSERTIONS);
	    OWLDataFactory factory = manager1.getOWLDataFactory();   
	    
		for(MappingObjectStr mapping: mappings){
			if(!orderedMappings.contains(mapping)){
				if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
					OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
					OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getInstances(left, false);
					for(Node<OWLNamedIndividual> ind : instances){
						OWLClassAssertionAxiom axiom = factory.getOWLClassAssertionAxiom(right, ind.getRepresentativeElement());
						manager.addAxiom(ontology, axiom);
					}
				}
				if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
					OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
					OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
					Node<OWLClass> top = reasonerHermit.getTopClassNode();
					NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
					for(Node<OWLNamedIndividual> individual : allIndividuals){
						NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
						for(Node<OWLNamedIndividual> ind : instances){
							OWLObjectPropertyAssertionAxiom axiom = factory.getOWLObjectPropertyAssertionAxiom(right, individual.getRepresentativeElement(), ind.getRepresentativeElement());
							manager.addAxiom(ontology, axiom);
						}
					}
					
					
				}
			}
		}
		
		System.out.println(orderedMappings);
		//process default mappings
		Map<String, Set<RoleCandidate>> roleCandidates = new HashMap<String, Set<RoleCandidate>> ();
		//Map<String, Set<Object>> classCandidates = new HashMap<String, Set<Object>> ();
		List<Set<Object>> classCandidates = new ArrayList<Set<Object>> ();
		Map<String, Set<Set<RoleCandidate>>> roleCandidatesPSet = new HashMap<String, Set<Set<RoleCandidate>>> ();
		List<Set<Set<Object>>> classCandidatesPSet = new ArrayList<Set<Set<Object>>> ();
		
		for(MappingObjectStr mapping: orderedMappings){
			int index = orderedMappings.indexOf(mapping);
			if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
				OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				Node<OWLClass> top = reasonerHermit.getTopClassNode();
				NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
				for(Node<OWLNamedIndividual> individual : allIndividuals){
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
					for(Node<OWLNamedIndividual> ind : instances){
						if(classCandidates.size() < index+1){
							classCandidates.add(index, new HashSet<Object>());							
						}
						classCandidates.get(index).add(new RoleCandidate(individual.getRepresentativeElement(), ind.getRepresentativeElement()));
					}
				}
				Set<Object> roleAssert = classCandidates.get(index);
				classCandidatesPSet.add(index, Sets.powerSet(roleAssert));
			}
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
				OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				
				NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(left, false);
				System.out.println(left);
				System.out.println(allIndividuals);
				for(Node<OWLNamedIndividual> individual : allIndividuals){
						if(classCandidates.size() < index +1){
							classCandidates.add(index, new HashSet<Object>());							
						}
						classCandidates.get(index).add(new ClassCandidate(individual.getRepresentativeElement()));
					
				}
				Set<Object> classAssert = classCandidates.get(index);
				
				//System.out.println(classAssert);
				classCandidatesPSet.add(index, Sets.powerSet(classAssert));
			}
			
		}
		Set<List<Set<Object>>> cart = null;
		List<Set<Set<Object>>> values = new ArrayList<Set<Set<Object>>>();
		values.addAll(classCandidatesPSet);
		cart = Sets.cartesianProduct(values);
		Set<OntologyMappingEntity> defaultSuccessMappings = new HashSet<OntologyMappingEntity>();
		//Set<List<Set<Object>>> defaultSuccessMappings = new HashSet<List<Set<Object>>>();
		System.out.println(cart.size());
		
		
		int consistent =0;
		int inconsistent = 0;
		for(List<Set<Object>> candidateMapping: cart){
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = man.getOWLDataFactory();
			OWLOntology onto = manager.createOntology();
			man.addAxioms(onto, ontology.getAxioms());
			
			Iterator<MappingObjectStr> itr = orderedMappings.iterator();
			/*System.out.println("yeah "+o.size());
			System.out.println("mappings "+orderedMappings.size());*/
			for(Set<Object> s: candidateMapping){
				MappingObjectStr mapping = itr.next();
				if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
					for(Object map:s){
						ClassCandidate cand = (ClassCandidate)map;
						OWLClass cl = dataFactory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
						OWLClassAssertionAxiom axiom = dataFactory.getOWLClassAssertionAxiom(cl, cand.getA());
						cand.getA();
						man.addAxiom(onto, axiom);
					}
				}
				if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
					for(Object map:s){
						RoleCandidate cand = (RoleCandidate)map;
						OWLObjectProperty prop = dataFactory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
						OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(prop, cand.getA1(), cand.getA2());
						
						man.addAxiom(onto, axiom);
					}
				}
			}
			//System.out.println(onto.getAxioms());
			hermitAccess = new HermiTAccess(man, onto, true);
		    reasonerHermit =hermitAccess.getReasoner();
		    //reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		    if(reasonerHermit.isConsistent()){
		    	OntologyMappingEntity owlEntity = new OntologyMappingEntity(onto, candidateMapping);
		    	addMappingtoSet(owlEntity, defaultSuccessMappings);
		    	consistent++;
		    }else{
		    	inconsistent++;
		    }
			reasonerHermit.dispose();
			
			
		}
		for(OntologyMappingEntity ent: defaultSuccessMappings){
			returnList.add(ent.getMappedOntology());
		}
		System.out.println("Num consistent "+ consistent+" \n Num inconsistent"+inconsistent);
		System.out.println(defaultSuccessMappings.size());
		System.out.println(defaultSuccessMappings);
		return returnList;
	}
	
	private List<OWLOntology> defaultMappingReasoningBio(OWLOntology o1, OWLOntology o2, Set<MappingObjectStr> mappings, Set<MappingObjectStr> defaultMapping)throws Exception{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology();
		manager.addAxioms(ontology, o2.getAxioms());
		
		List<OWLOntology> returnList = new ArrayList<OWLOntology>();
		List<MappingObjectStr> orderedMappings = new ArrayList<MappingObjectStr>();
		for(MappingObjectStr obj:defaultMapping){
			orderedMappings.add(obj);
		}
		
		OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager1.getOWLDataFactory();   
		OWLClass clz = factory.getOWLClass(IRI.create("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Lung"));
		OWLNamedIndividual indi = factory.getOWLNamedIndividual(IRI.create("ind1000"));
		OWLClassAssertionAxiom ax = factory.getOWLClassAssertionAxiom(clz, indi);
		manager1.addAxiom(o1, ax);
		
		clz = factory.getOWLClass(IRI.create("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Thoracic_Cavity"));
		OWLNamedIndividual indi1 = factory.getOWLNamedIndividual(IRI.create("ind1100"));
		ax = factory.getOWLClassAssertionAxiom(clz, indi1);
		manager1.addAxiom(o1, ax);
		
		clz = factory.getOWLClass(IRI.create("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Thoracic_Cavity"));
		OWLNamedIndividual indi2 = factory.getOWLNamedIndividual(IRI.create("ind1101"));
		ax = factory.getOWLClassAssertionAxiom(clz, indi2);
		manager1.addAxiom(o1, ax);
		
		clz = factory.getOWLClass(IRI.create("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Visceral_Pleura"));
		OWLNamedIndividual indi3 = factory.getOWLNamedIndividual(IRI.create("ind1102"));
		ax = factory.getOWLClassAssertionAxiom(clz, indi3);
		manager1.addAxiom(o1, ax);
		
		OWLNamedIndividual indi4 = factory.getOWLNamedIndividual(IRI.create("ind1103"));
		ax = factory.getOWLClassAssertionAxiom(clz, indi4);
		manager1.addAxiom(o1, ax);
		
		OWLObjectProperty prop1 = factory.getOWLObjectProperty(IRI.create("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Anatomic_Structure_Has_Location"));
		OWLObjectPropertyAssertionAxiom owlPropAxiom = factory.getOWLObjectPropertyAssertionAxiom(prop1, indi3, indi);
		manager1.addAxiom(o1, owlPropAxiom);
		
		owlPropAxiom = factory.getOWLObjectPropertyAssertionAxiom(prop1, indi3, indi2);
		manager1.addAxiom(o1, owlPropAxiom);
		
		owlPropAxiom = factory.getOWLObjectPropertyAssertionAxiom(prop1, indi4, indi1);
		manager1.addAxiom(o1, owlPropAxiom);
		
		
		saveMergedOntology("/media/kunal/kunal/logmap/bio5.owl", o1, o2,mappings);
		HermiTAccess hermitAccess = new HermiTAccess(manager1, o1, true);
	       OWLReasoner reasonerHermit =hermitAccess.getReasoner();
	       reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.CLASS_ASSERTIONS);
	    
	    
		for(MappingObjectStr mapping: mappings){
			if(!orderedMappings.contains(mapping)){
				if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
					OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
					OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getInstances(left, false);
					for(Node<OWLNamedIndividual> ind : instances){
						//System.out.println(left);
						OWLClassAssertionAxiom axiom = factory.getOWLClassAssertionAxiom(right, ind.getRepresentativeElement());
						manager.addAxiom(ontology, axiom);
					}
					
				}
				if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
					OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
					OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
					Node<OWLClass> top = reasonerHermit.getTopClassNode();
					NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
					for(Node<OWLNamedIndividual> individual : allIndividuals){
						NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
						for(Node<OWLNamedIndividual> ind : instances){
							OWLObjectPropertyAssertionAxiom axiom = factory.getOWLObjectPropertyAssertionAxiom(right, individual.getRepresentativeElement(), ind.getRepresentativeElement());
							manager.addAxiom(ontology, axiom);
						}
					}
					
					
				}
			}
		}
		int counter =1001;
		
		/*for(MappingObjectStr mapping: orderedMappings){
			int index = orderedMappings.indexOf(mapping);
			
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
				OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				
				if(left.getIRI().toString().contains("Visceral_Pleura")||left.getIRI().toString().contains("Pleura")||
						left.getIRI().toString().contains("Thoracic_Cavity")||
						left.getIRI().toString().contains("Lung")||
						left.getIRI().toString().contains("Organ")
						
						){
					OWLNamedIndividual ind = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
					
					manager.addAxiom(o1, factory.getOWLClassAssertionAxiom(left, ind));
					counter++;
					
					
				}
				
				
				
			}
			
		}*/
		reasonerHermit.dispose();
		hermitAccess = new HermiTAccess(manager1, o1, true);
	    reasonerHermit =hermitAccess.getReasoner();
		 reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY, InferenceType.OBJECT_PROPERTY_ASSERTIONS, InferenceType.CLASS_ASSERTIONS);
		    
		//process default mappings
		Map<String, Set<RoleCandidate>> roleCandidates = new HashMap<String, Set<RoleCandidate>> ();
		//Map<String, Set<Object>> classCandidates = new HashMap<String, Set<Object>> ();
		List<Set<Object>> classCandidates = new ArrayList<Set<Object>> ();
		Map<String, Set<Set<RoleCandidate>>> roleCandidatesPSet = new HashMap<String, Set<Set<RoleCandidate>>> ();
		List<Set<Set<Object>>> classCandidatesPSet = new ArrayList<Set<Set<Object>>> ();
		
		for(MappingObjectStr mapping: orderedMappings){
			int index = orderedMappings.indexOf(mapping);
			if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
				OWLObjectProperty left =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt1()));
				OWLObjectProperty right =factory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
				Node<OWLClass> top = reasonerHermit.getTopClassNode();
				NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(top.getRepresentativeElement(), false);
				for(Node<OWLNamedIndividual> individual : allIndividuals){
					NodeSet<OWLNamedIndividual>  instances= reasonerHermit.getObjectPropertyValues(individual.getRepresentativeElement(), left);
					for(Node<OWLNamedIndividual> ind : instances){
						if(classCandidates.size() < index+1){
							classCandidates.add(index, new HashSet<Object>());							
						}
						classCandidates.get(index).add(new RoleCandidate(individual.getRepresentativeElement(), ind.getRepresentativeElement()));
					}
				}
				/*System.out.println(left.getIRI().toString());
				if(left.getIRI().toString().equals("<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Organ>")
						){
					OWLNamedIndividual ind1 = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
					counter++;
					OWLNamedIndividual ind2 = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
					counter++;
					classCandidates.add(index, Sets.newHashSet((Object)new RoleCandidate(ind1, ind2)));
					
				}*/
				/*OWLNamedIndividual ind1 = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
				counter++;
				OWLNamedIndividual ind2 = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
				counter++;
				classCandidates.add(index, Sets.newHashSet(((Object) new RoleCandidate(ind1,  ind2))));*/
				//Set<Object> roleAssert = classCandidates.get(index);
				//classCandidatesPSet.add(index, Sets.powerSet(roleAssert));
			}
			if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
				OWLClass left =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt1()));
				OWLClass right =factory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
				
				NodeSet<OWLNamedIndividual> allIndividuals = reasonerHermit.getInstances(left, false);
				//System.out.println(left);
				//System.out.println(allIndividuals);
				classCandidates.add(index, new HashSet<Object>());	
				for(Node<OWLNamedIndividual> individual : allIndividuals){
						
						classCandidates.get(index).add(new ClassCandidate(individual.getRepresentativeElement()));
					
				}
				/*if(left.getIRI().toString().contains("Visceral_Pleura")||left.getIRI().toString().contains("Pleura")||
						left.getIRI().toString().contains("Thoracic_Cavity")||
						left.getIRI().toString().contains("Lung")||
						left.getIRI().toString().contains("Organ")
						
						){
					OWLNamedIndividual ind = factory.getOWLNamedIndividual(IRI.create("ind"+counter));
					counter++;
					classCandidates.add(index, Sets.newHashSet((Object)new ClassCandidate(ind)));
					
				}*/
				
				
				Set<Object> classAssert = classCandidates.get(index);
				
				//System.out.println(classAssert);
				classCandidatesPSet.add(index, Sets.powerSet(classAssert));
			}
			
		}
		Set<List<Set<Object>>> cart = null;
		List<Set<Set<Object>>> values = new ArrayList<Set<Set<Object>>>();
		values.addAll(classCandidatesPSet);
		cart = Sets.cartesianProduct(values);
		Set<OntologyMappingEntity> defaultSuccessMappings = new HashSet<OntologyMappingEntity>();
		//Set<List<Set<Object>>> defaultSuccessMappings = new HashSet<List<Set<Object>>>();
		System.out.println(cart.size());
		
		
		int consistent =0;
		int inconsistent = 0;
		int count =1;
		for(List<Set<Object>> candidateMapping: cart){
			long startTime = System.currentTimeMillis();
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = man.getOWLDataFactory();
			OWLOntology onto = manager.createOntology();
			man.addAxioms(onto, ontology.getAxioms());
			
			Iterator<MappingObjectStr> itr = orderedMappings.iterator();
			/*System.out.println("yeah "+o.size());
			System.out.println("mappings "+orderedMappings.size());*/
			for(Set<Object> s: candidateMapping){
				MappingObjectStr mapping = itr.next();
				if(mapping.getTypeOfMapping() == MappingObjectStr.CLASSES){
					for(Object map:s){
						ClassCandidate cand = (ClassCandidate)map;
						OWLClass cl = dataFactory.getOWLClass(IRI.create(mapping.getIRIStrEnt2()));
						OWLClassAssertionAxiom axiom = dataFactory.getOWLClassAssertionAxiom(cl, cand.getA());
						cand.getA();
						man.addAxiom(onto, axiom);
					}
				}
				if(mapping.getTypeOfMapping() == MappingObjectStr.OBJECTPROPERTIES){
					for(Object map:s){
						RoleCandidate cand = (RoleCandidate)map;
						OWLObjectProperty prop = dataFactory.getOWLObjectProperty(IRI.create(mapping.getIRIStrEnt2()));
						OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(prop, cand.getA1(), cand.getA2());
						
						man.addAxiom(onto, axiom);
					}
				}
			}
			//System.out.println(onto.getAxioms());
			hermitAccess = new HermiTAccess(man, onto, true);
		    reasonerHermit =hermitAccess.getReasoner();
		    //reasonerHermit.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		    boolean flag = false;
		    if(reasonerHermit.isConsistent()){
		    	flag = true;
		    	OntologyMappingEntity owlEntity = new OntologyMappingEntity(onto, candidateMapping);
		    	addMappingtoSet(owlEntity, defaultSuccessMappings);
		    	consistent++;
		    }else{
		    	inconsistent++;
		    }
			reasonerHermit.dispose();
			
			System.out.println("Time taken to process candidate :: "+count +" :: "+(System.currentTimeMillis() -startTime)+" :: consistent "+ flag);
			count++;
		}
		for(OntologyMappingEntity ent: defaultSuccessMappings){
			returnList.add(ent.getMappedOntology());
		}
		System.out.println("Num consistent "+ consistent+" \n Num inconsistent"+inconsistent);
		System.out.println(defaultSuccessMappings.size());
		System.out.println(defaultSuccessMappings);
		return returnList;
	}
	public boolean addMappingtoSet(OntologyMappingEntity candidate, Set<OntologyMappingEntity> successfulMappings)throws Exception{
		
		if(successfulMappings.contains(candidate)){
			return false;
		}
		List<OntologyMappingEntity> objectsToRemove = new ArrayList<OntologyMappingEntity>();
		for(OntologyMappingEntity one: successfulMappings){
			
			if(checkSubSet(candidate.getDefaultMappings(), one.getDefaultMappings())){
				return false;
			}else if (checkSubSet(one.getDefaultMappings(), candidate.getDefaultMappings())){
						objectsToRemove.add(one);		
			}
		}
		
		successfulMappings.removeAll(objectsToRemove);
		successfulMappings.add(candidate);
		return true;
	}
	//returns true of every 
	public boolean checkSubSet(List<Set<Object>> s1, List<Set<Object>> s2)throws Exception{
		logOutput("Comparing lists :: \n"+s1+" \n"+s2);
		for(Set<Object> sub1: s1){
			int index = s1.indexOf(sub1);
			logOutput(s2.get(index)+".containsAll("+sub1+"): "+(s2.get(index).containsAll(sub1) || sub1.isEmpty()));
			if(s2.get(index).containsAll(sub1) || sub1.isEmpty()){
				
				continue;
			}
			logOutput("return value false");
			return false;
		}
		logOutput("return value true");
		return true;
	}
	private void getCandidateAxioms(){
		
	}
	private void logOutput(String text)throws Exception{
		File file = new File("/media/kunal/kunal/logger.txt");
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter w = new BufferedWriter(writer);
		w.write("\n"+text);
		w.flush();
		w.close();
		
	}
	private Set<MappingObjectStr> getLogMapMappings(OWLOntology ontology1, OWLOntology ontology2){
		LogMap2_Matcher matcher = new LogMap2_Matcher(ontology1, ontology2);
		
		return matcher.getLogmap2_Mappings();
		
	}
	private static void insertNameIndividuals()throws Exception{
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology onto = man.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/ontologies/oaei2014_FMA_small_overlapping_nci.owl"));
		OWLDataFactory fact = man.getOWLDataFactory();
		int counter = 1000;
		/*for(OWLClass cl: onto.getClassesInSignature()){
			if(cl.getIRI().toString().contains("Visceral_Pleura")){
				fact.getOWLNamedIndividual(IRI.create("visceral_Pleura"+counter));
				counter++;
			}
			else if(cl.getIRI().toString().contains("Pleura")){
				fact.getOWLNamedIndividual(IRI.create("Pleura"+counter));
				counter++;
			}else if(cl.getIRI().toString().contains("Pleura")){
				fact.getOWLNamedIndividual(IRI.create("Pleura"+counter));
				counter++;
			}
		}*/
		for(OWLClass cl: onto.getClassesInSignature()){
			OWLNamedIndividual ind = fact.getOWLNamedIndividual(IRI.create("ind"+counter));
			OWLClassAssertionAxiom axiom = fact.getOWLClassAssertionAxiom(cl, ind);
			man.addAxiom(onto, axiom);
			
			counter++;
		}
		man.saveOntology(onto, IRI.create(new File("/media/kunal/kunal/NCI_indi")));
	}
	private static void testFood()throws Exception{
		OWLOntologyManager ontologyManager1 = OWLManager.createOWLOntologyManager();
		OWLOntologyManager ontologyManager2 = OWLManager.createOWLOntologyManager();
		
		OWLOntology ont1  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/logmap/food1.owl"));
		OWLOntology ont2  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/logmap/food2.owl"));
		
		DefaultMapping mapping = new DefaultMapping();
		mapping.processMappingsForFood(ont1, ont2);
	}
	
	private static void testMarriage()throws Exception{
		OWLOntologyManager ontologyManager1 = OWLManager.createOWLOntologyManager();
		OWLOntologyManager ontologyManager2 = OWLManager.createOWLOntologyManager();
		
		OWLOntology ont1  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/ontologies/marriage2.owl"));
		OWLOntology ont2  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/ontologies/marriage1.owl"));
		
		DefaultMapping mapping = new DefaultMapping();
		mapping.processMappingsForMarriage(ont1, ont2);
	}
	private static void testConference()throws Exception{
		OWLOntologyManager ontologyManager1 = OWLManager.createOWLOntologyManager();
		OWLOntologyManager ontologyManager2 = OWLManager.createOWLOntologyManager();
		
		OWLOntology ont1  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/ontologies/ekaw.owl"));
		OWLOntology ont2  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/ontologies/cmt.owl"));
		
		DefaultMapping mapping = new DefaultMapping();
		mapping.processMappingsForConference(ont1, ont2);
	}
	private static void testBio()throws Exception{
		OWLOntologyManager ontologyManager1 = OWLManager.createOWLOntologyManager();
		OWLOntologyManager ontologyManager2 = OWLManager.createOWLOntologyManager();
		
		OWLOntology ont2  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/onto1/oaei2014_FMA_small_overlapping_nci.owl"));
		OWLOntology ont1  = ontologyManager1.loadOntologyFromOntologyDocument(new File("/media/kunal/kunal/onto1/oaei2014_NCI_small_overlapping_fma.owl"));
		
		DefaultMapping mapping = new DefaultMapping();
		mapping.processMappingsForConference(ont1, ont2);
	}
	public static void test(){
		Set<Integer> i = Sets.newHashSet(1,2,3);
		Set<String> j = Sets.newHashSet("a", "b", "c");
		System.out.println(Sets.cartesianProduct(i,j));
	}
	public static void main(String args[])throws Exception{
		//testMarriage();
		//testFood();
		testBio();
		//test();
		//insertNameIndividuals();
	}

}
