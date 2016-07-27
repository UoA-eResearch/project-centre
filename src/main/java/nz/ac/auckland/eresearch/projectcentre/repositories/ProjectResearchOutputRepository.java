package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutput;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectResearchOutputRepository extends CrudRepository<ResearchOutput, Integer> {

  List<ResearchOutput> findByProjectId(Integer projectId);
  ResearchOutput findByIdAndProjectId(Integer id, Integer projectId);

}
