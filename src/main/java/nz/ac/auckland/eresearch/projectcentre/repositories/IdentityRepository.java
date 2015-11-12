package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Identity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface IdentityRepository extends CrudRepository<Identity, Integer> {

  List<Identity> findByUsernameAndService(String username, String service);

}
