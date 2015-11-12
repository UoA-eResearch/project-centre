package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectFacility;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectFacilityRepository extends CrudRepository<ProjectFacility, Integer> {

  List<ProjectFacility> findByProjectId(Integer projectId);
}
