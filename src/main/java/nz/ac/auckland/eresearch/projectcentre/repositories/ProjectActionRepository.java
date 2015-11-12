package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectActionRepository extends CrudRepository<ProjectAction, Integer> {

  List<ProjectAction> findByProjectId(Integer projectId);
}
