package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectStatus;

import org.springframework.data.repository.CrudRepository;

public interface ProjectStatusRepository extends CrudRepository<ProjectStatus, Integer> {
  ProjectStatus findByName(String s);
}
