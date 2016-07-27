package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ServiceSchemaRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ServiceSchemaService implements BaseService<ServiceSchema> {

  @Autowired
  private ServiceSchemaRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ServiceSchemaCache", key = "#id")
  public ServiceSchema findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ServiceSchema> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ServiceSchema findByServiceIdAndSchemaVersion(Integer serviceId, String schemaVersion) {
    return repo.findByServiceIdAndSchemaVersion(serviceId, schemaVersion);
  }
  
  @PreAuthorize(Authz.ADMIN)
  public ServiceSchema create(ServiceSchema entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceSchemaCache", key = "#entity.getId()")
  public ServiceSchema update(ServiceSchema entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ServiceSchemaCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

