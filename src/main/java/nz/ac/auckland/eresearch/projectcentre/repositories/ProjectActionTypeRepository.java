package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectActionType;

import org.springframework.data.repository.CrudRepository;

public interface ProjectActionTypeRepository extends CrudRepository<ProjectActionType, Integer> {

  ProjectActionType findByName(String name);

}
