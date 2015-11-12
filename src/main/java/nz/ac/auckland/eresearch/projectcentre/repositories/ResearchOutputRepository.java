package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ResearchOutputRepository extends CrudRepository<ResearchOutput, Integer> {

  List<ResearchOutput> findByProjectId(Integer projectId);
}
