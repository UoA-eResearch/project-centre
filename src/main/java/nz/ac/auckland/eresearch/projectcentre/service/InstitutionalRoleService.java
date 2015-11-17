package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionalRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class InstitutionalRoleService extends BaseService<DivisionalRole> {

  @Autowired
  private DivisionalRoleRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "InstitutionalRoleCache", key = "#id")
  public DivisionalRole findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<DivisionalRole> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public DivisionalRole create(DivisionalRole entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionalRoleCache", key = "#entity.getId()")
  public DivisionalRole update(DivisionalRole entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionalRoleCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

