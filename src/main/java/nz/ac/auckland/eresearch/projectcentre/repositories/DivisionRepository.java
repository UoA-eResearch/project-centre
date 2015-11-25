package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface DivisionRepository extends JpaRepository<Division, Integer> {

  Division findByCode(String divCode);

  List<Division> findByParentId(Integer id);

  @Query("select d from Division d where d.code in :codes")
  List<Division> filterOutNonExistingDivisions(@Param("codes") Collection<String> codes);

}
