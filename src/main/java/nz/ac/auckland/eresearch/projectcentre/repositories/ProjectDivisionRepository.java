package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;

import org.springframework.data.repository.CrudRepository;

public interface ProjectDivisionRepository extends CrudRepository<ProjectDivision, Integer> {

  List<ProjectDivision> findByProjectId(Integer projectId);
  List<ProjectDivision> findByDivisionId(Integer divisionId);

}
