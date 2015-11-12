package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface DivisionRepository extends CrudRepository<Division, Integer> {

  List<Division> findByInstitutionId(int institutionId);

  Division findByCode(String divCode);
//    List<Division> findByIdAndInstitutionId(int id, int institutionId);
}
