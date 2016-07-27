package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.DivisionalRole;

import org.springframework.data.repository.CrudRepository;

public interface DivisionalRoleRepository extends CrudRepository<DivisionalRole, Integer> {

  public DivisionalRole findByName(String name);
}
