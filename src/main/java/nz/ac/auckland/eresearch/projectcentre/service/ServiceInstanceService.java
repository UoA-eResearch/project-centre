package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ServiceInstanceRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstance;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ServiceInstanceService implements BaseService<ServiceInstance> {

  @Autowired
  private ServiceInstanceRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceInstanceCache", key = "#id")
  public ServiceInstance findOne(Integer id, Map<String, Integer> idMap) {
    return this.findByIdAndProjectId(id, idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ServiceInstance findByIdAndProjectId(Integer id, Integer projectId) {
    return repo.findByIdAndProjectId(id, projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ServiceInstance> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ServiceInstance> findAll(Map<String, Integer> idMap) {
    return this.findByProjectId(idMap.get("projectId"));
  }

  @PreAuthorize(Authz.ADMIN)
  public ServiceInstance create(ServiceInstance entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceInstanceCache", key = "#entity.getId()")
  public ServiceInstance update(ServiceInstance entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceInstanceCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

