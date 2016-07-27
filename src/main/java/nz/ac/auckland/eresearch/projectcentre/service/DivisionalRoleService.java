package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionalRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class DivisionalRoleService implements IdNameTypeService<DivisionalRole> {

  @Autowired
  private DivisionalRoleRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "DivisionalRoleCache", key = "#id")
  public DivisionalRole findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<DivisionalRole> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public DivisionalRole create(DivisionalRole entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionalRoleCache", key = "#entity.getId()")
  public DivisionalRole update(DivisionalRole entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionalRoleCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "DivisionalRoleCache", key = "#roleName")
  public DivisionalRole findByName(String roleName) {
    return repo.findByName(roleName);
  }

}

