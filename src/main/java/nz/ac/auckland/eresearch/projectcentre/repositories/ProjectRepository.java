package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface ProjectRepository extends CrudRepository<Project, Integer> {

  List<Project> findByCode(String code);

  List<Project> findByCodeAndIdNot(String code, Integer id);

  @Query("SELECT p from Project p where :id member of p.divisionIds")
  List<Project> findByDivisionId(Integer id);
}
