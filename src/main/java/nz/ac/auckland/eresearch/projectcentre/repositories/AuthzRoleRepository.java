package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
public interface AuthzRoleRepository extends CrudRepository<AuthzRole, Integer> {
  List<AuthzRole> findByPersonId(int personId);
}
