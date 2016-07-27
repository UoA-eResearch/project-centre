package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ServiceRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Service;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ServiceService implements BaseService<Service> {

  @Autowired
  private ServiceRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceCache", key = "#id")
  public Service findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Service> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public Service create(Service entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceCache", key = "#entity.getId()")
  public Service update(Service entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceCache", key = "#name")
  public Service findByName(String name) {
    return repo.findByName(name);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Service findByDrupalId(Integer drupalId) {
    return repo.findByDrupalId(drupalId);
  }

}

