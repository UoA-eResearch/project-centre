package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface DivisionRepository extends JpaRepository<Division, Integer> {

  Division findByCode(String divCode);
  List<Division> findByParentId(Integer id);

}
