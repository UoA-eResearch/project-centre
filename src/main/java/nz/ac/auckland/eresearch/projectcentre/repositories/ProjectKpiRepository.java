package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectKpiRepository extends CrudRepository<ProjectKpi, Integer> {

  List<ProjectKpi> findByProjectId(Integer projectId);
}
