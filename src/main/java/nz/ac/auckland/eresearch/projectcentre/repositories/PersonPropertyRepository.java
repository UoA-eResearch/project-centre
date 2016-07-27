package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonProperty;

import org.springframework.data.repository.CrudRepository;

public interface PersonPropertyRepository extends CrudRepository<PersonProperty, Integer> {

  PersonProperty findByIdAndPersonId(Integer id, Integer personId);
  List<PersonProperty> findByPersonId(Integer personId);
  List<PersonProperty> findByPropnameAndPropvalue(String propname, String propvalue);

}
