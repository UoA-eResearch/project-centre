package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Identity;

import org.springframework.data.repository.CrudRepository;

public interface IdentityRepository extends CrudRepository<Identity, Integer> {

  Identity findByUsername(String username);

}
