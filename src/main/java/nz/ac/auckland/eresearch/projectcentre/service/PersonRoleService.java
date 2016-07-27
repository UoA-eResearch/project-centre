package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
// TODO: add caching
public class PersonRoleService implements IdNameTypeService<PersonRole> {

  @Autowired
  private PersonRoleRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonRole findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonRole> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonRole findByName(String name) {
    return repo.findByName(name);
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonRole create(PersonRole entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonRole update(PersonRole entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

