package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProject;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface PersonProjectRepository extends CrudRepository<PersonProject, Integer> {

  List<PersonProject> findByPersonId(Integer personId);

  List<PersonProject> findByProjectId(Integer projectId);
}
