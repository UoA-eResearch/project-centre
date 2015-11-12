package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectPropertyRepository extends CrudRepository<ProjectProperty, Integer> {

  List<ProjectProperty> findByProjectId(Integer projectId);
}
