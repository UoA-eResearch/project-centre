package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectTypeRepository extends CrudRepository<ProjectType, Integer> {
  ProjectType findByName(String s);
}
