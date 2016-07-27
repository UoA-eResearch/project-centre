package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectAction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectActionRepository extends CrudRepository<ProjectAction, Integer> {

  List<ProjectAction> findByProjectId(Integer projectId);
  ProjectAction findByIdAndProjectId(Integer id, Integer projectId);
}
