package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ServiceInstanceStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstanceStatus;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ServiceInstanceStatusService implements IdNameTypeService<ServiceInstanceStatus> {

  @Autowired
  private ServiceInstanceStatusRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceInstanceStatusCache", key = "#id")
  public ServiceInstanceStatus findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ServiceInstanceStatus> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ServiceInstanceStatus create(ServiceInstanceStatus entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceInstanceStatusCache", key = "#entity.getId()")
  public ServiceInstanceStatus update(ServiceInstanceStatus entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceInstanceStatusCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceInstanceStatusCache", key = "#name")
  public ServiceInstanceStatus findByName(String name) {
    return repo.findByName(name);
  }

}

