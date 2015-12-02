package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonService extends BaseService<Person> {

  @Autowired
  private PersonRepository person_repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonCache", key = "#id")
  public Person findOne(Integer id) {
    return person_repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "PersonCache")
  public Iterable<Person> findAll() {

    return person_repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Person findByEmail(String email) {
    return person_repo.findByEmail(email);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Person> findByEmailAndIdNot(String email, Integer id) {
    return person_repo.findByEmailAndIdNot(email, id);
  }

  @PreAuthorize(Authz.ADMIN)
  public Person create(Person entity) {
    return person_repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  @CacheEvict(value = "PersonCache", key = "#entity.getId()")
  public Person update(@P("entity") Person entity) throws Exception {
    return person_repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "PersonCache", key = "#id")
  public void delete(Integer id) {
    person_repo.delete(id);
  }
}

