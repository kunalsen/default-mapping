import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;


public class OntologyMappingEntity {
	private OWLOntology mappedOntology;
	List<Set<Object>>  defaultMappings;
	public OntologyMappingEntity(OWLOntology mappedOntology,
			List<Set<Object>> defaultMappings) {
		super();
		this.mappedOntology = mappedOntology;
		this.defaultMappings = defaultMappings;
	}
	public OWLOntology getMappedOntology() {
		return mappedOntology;
	}
	public void setMappedOntology(OWLOntology mappedOntology) {
		this.mappedOntology = mappedOntology;
	}
	public List<Set<Object>> getDefaultMappings() {
		return defaultMappings;
	}
	public void setDefaultMappings(List<Set<Object>> defaultMappings) {
		this.defaultMappings = defaultMappings;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		OntologyMappingEntity entity = (OntologyMappingEntity)obj;
		
		return defaultMappings.equals(entity.getDefaultMappings());
	}
	
}
