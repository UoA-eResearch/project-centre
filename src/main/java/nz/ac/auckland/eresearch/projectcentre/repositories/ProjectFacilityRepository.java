package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectFacility;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectFacilityRepository extends CrudRepository<ProjectFacility, Integer> {

  List<ProjectFacility> findByProjectId(Integer projectId);
}
