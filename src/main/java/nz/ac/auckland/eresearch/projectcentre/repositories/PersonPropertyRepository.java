package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by markus on 9/11/15.
 */
public interface PersonPropertyRepository extends CrudRepository<PersonProperty, Integer> {

	  List<PersonProperty> findByPropnameAndPropvalue(String propname, String propvalue);

}
