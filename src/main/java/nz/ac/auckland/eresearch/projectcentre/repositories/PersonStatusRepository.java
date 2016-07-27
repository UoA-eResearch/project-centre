package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonStatus;

import org.springframework.data.repository.CrudRepository;

public interface PersonStatusRepository extends CrudRepository<PersonStatus, Integer> {

  PersonStatus findByName(String name);
}
