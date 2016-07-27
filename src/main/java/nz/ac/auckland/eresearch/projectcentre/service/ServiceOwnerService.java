package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ServiceOwnerRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceOwner;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ServiceOwnerService implements BaseService<ServiceOwner> {

  @Autowired
  private ServiceOwnerRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceOwnerCache", key = "#id")
  public ServiceOwner findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ServiceOwner> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ServiceOwner create(ServiceOwner entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceOwnerCache", key = "#entity.getId()")
  public ServiceOwner update(ServiceOwner entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceOwnerCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

