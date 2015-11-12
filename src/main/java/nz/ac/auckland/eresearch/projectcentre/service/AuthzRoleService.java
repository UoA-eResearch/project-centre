package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class AuthzRoleService extends BaseService<AuthzRole> {

  @Autowired
  private AuthzRoleRepository repo;

  @PreAuthorize(Authz.ADMIN)
  public AuthzRole findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.ADMIN)
  public Iterable<AuthzRole> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public AuthzRole create(AuthzRole entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public AuthzRole update(AuthzRole entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

