package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

  List<Person> findByEmail(String email);

  List<Person> findByEmailAndIdNot(String email, Integer id);
}
