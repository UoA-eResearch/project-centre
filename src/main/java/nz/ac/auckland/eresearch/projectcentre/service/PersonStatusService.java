package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.PersonStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonStatusService implements IdNameTypeService<PersonStatus> {

  @Autowired
  private PersonStatusRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonStatusCache", key = "#id")
  public PersonStatus findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonStatus> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonStatus create(PersonStatus entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "PersonStatusCache", key = "#entity.getId()")
  public PersonStatus update(PersonStatus entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "PersonStatusCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonStatusCache", key = "#name")
  public PersonStatus findByName(String name) {
    return repo.findByName(name);
  }

}

