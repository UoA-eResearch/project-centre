package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectType;

import org.springframework.data.repository.CrudRepository;

public interface ProjectTypeRepository extends CrudRepository<ProjectType, Integer> {
  ProjectType findByName(String s);
}
