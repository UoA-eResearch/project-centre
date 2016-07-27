package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonService implements BaseService<Person> {

  @Autowired
  private PersonRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonCache", key = "#id")
  public Person findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Person> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Person> findByEmail(String email) {
    return repo.findByEmail(email);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Person> findByEmailAndIdNot(String email, Integer id) {
    return repo.findByEmailAndIdNot(email, id);
  }

  @PreAuthorize(Authz.ADMIN)
  public Person create(Person entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  @CacheEvict(value = "PersonCache", key = "#entity.getId()")
  public Person update(Person entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "PersonCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

