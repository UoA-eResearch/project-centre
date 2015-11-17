package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by markus on 9/11/15.
 */
public interface DivisionalRoleRepository extends CrudRepository<DivisionalRole, Integer> {

  public DivisionalRole findByName(String name);
}
