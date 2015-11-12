package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.InstitutionalRole;
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionalRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class InstitutionalRoleService extends BaseService<InstitutionalRole> {

  @Autowired
  private InstitutionalRoleRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "InstitutionalRoleCache", key = "#id")
  public InstitutionalRole findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<InstitutionalRole> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public InstitutionalRole create(InstitutionalRole entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionalRoleCache", key = "#entity.getId()")
  public InstitutionalRole update(InstitutionalRole entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionalRoleCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

