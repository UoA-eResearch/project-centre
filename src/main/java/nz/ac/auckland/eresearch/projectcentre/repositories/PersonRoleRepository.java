package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonRole;

import org.springframework.data.repository.CrudRepository;

public interface PersonRoleRepository extends CrudRepository<PersonRole, Integer> {
  
  public PersonRole findByName(String name);
}
