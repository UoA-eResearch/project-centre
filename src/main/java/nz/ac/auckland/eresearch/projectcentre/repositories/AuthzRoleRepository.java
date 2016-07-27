package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.AuthzRole;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthzRoleRepository extends CrudRepository<AuthzRole, Integer> {
  List<AuthzRole> findByIdentityId(int identityId);
}
