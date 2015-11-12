package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonStatusService extends BaseService<PersonStatus> {

  @Autowired
  private PersonStatusRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonStatusCache", key = "#id")
  public PersonStatus findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonStatus> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonStatus create(PersonStatus entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DepartmentCache", key = "#entity.getId()")
  public PersonStatus update(PersonStatus entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DepartmentCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

