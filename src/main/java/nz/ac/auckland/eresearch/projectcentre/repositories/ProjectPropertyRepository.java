package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectProperty;

import org.springframework.data.repository.CrudRepository;

public interface ProjectPropertyRepository extends CrudRepository<ProjectProperty, Integer> {

  ProjectProperty findByIdAndProjectId(Integer id, Integer projectId);
  List<ProjectProperty> findByProjectId(Integer projectId);
  List<ProjectProperty> findByPropnameAndPropvalue(String propname, String propvalue);
}
