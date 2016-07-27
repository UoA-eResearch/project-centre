package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.PersonAffiliationRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonAffiliationService implements BaseService<PersonAffiliation> {

  @Autowired
  private PersonAffiliationRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonAffiliation findOne(Integer id, Map<String, Integer> idMap) {
    return this.findByIdAndPersonId(id, idMap.get("personId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonAffiliation> findAll(Map<String, Integer> idMap) {
    return this.findByPersonId(idMap.get("personId"));
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  public PersonAffiliation create(PersonAffiliation entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  public PersonAffiliation update(PersonAffiliation entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PERSON)
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonAffiliation findByIdAndPersonId(Integer id, Integer personId) {
    return repo.findByIdAndPersonId(id, personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonAffiliation> findByPersonId(Integer personId) {
    return repo.findByPersonId(personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonAffiliation> findByDivisionId(Integer personId) {
    return repo.findByDivisionId(personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonAffiliation> findByDivisionalRoleId(Integer roleId) {
    return repo.findByDivisionalRoleId(roleId);
  }

}

