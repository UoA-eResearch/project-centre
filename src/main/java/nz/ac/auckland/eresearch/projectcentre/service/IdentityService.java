package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class IdentityService implements BaseService<Identity> {

  @Autowired
  private IdentityRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public Identity findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Identity> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public Identity create(Identity entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public Identity update(Identity entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Identity findByUsername(String username) {
    return repo.findByUsername(username);
  }

}

