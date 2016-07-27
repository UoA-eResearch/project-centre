package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.PersonPropertyRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonPropertyService implements BaseService<PersonProperty> {

  @Autowired
  private PersonPropertyRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonProperty findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findByIdAndPersonId(id, idMap.get("personId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProperty> findByPersonId(Integer personId) {
    return repo.findByPersonId(personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProperty> findAll(Map<String, Integer> idMap) {
    return this.findByPersonId(idMap.get("personId"));
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonProperty create(PersonProperty entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  public PersonProperty update(PersonProperty entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

