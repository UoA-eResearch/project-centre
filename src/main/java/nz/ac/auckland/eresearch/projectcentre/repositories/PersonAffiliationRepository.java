package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;

import org.springframework.data.repository.CrudRepository;

public interface PersonAffiliationRepository extends CrudRepository<PersonAffiliation, Integer> {

  List<PersonAffiliation> findByPersonId(Integer personId);

  PersonAffiliation findByIdAndPersonId(Integer id, Integer personId);

  List<PersonAffiliation> findByDivisionId(Integer divisionId);

  List<PersonAffiliation> findByDivisionalRoleId(Integer roleId);

}
