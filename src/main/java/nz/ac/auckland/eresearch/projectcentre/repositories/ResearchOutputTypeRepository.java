package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutputType;

import org.springframework.data.repository.CrudRepository;

public interface ResearchOutputTypeRepository extends CrudRepository<ResearchOutputType, Integer> {

  ResearchOutputType findByName(String name);

}
