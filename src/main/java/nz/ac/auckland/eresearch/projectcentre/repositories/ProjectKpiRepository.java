package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectKpi;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectKpiRepository extends CrudRepository<ProjectKpi, Integer> {

  List<ProjectKpi> findByProjectId(Integer projectId);
}
