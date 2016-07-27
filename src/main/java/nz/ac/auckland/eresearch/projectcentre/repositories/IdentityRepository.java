package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Identity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IdentityRepository extends CrudRepository<Identity, Integer> {

  List<Identity> findByUsername(String username);

}
