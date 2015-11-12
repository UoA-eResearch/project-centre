package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonRoleService extends BaseService<PersonRole> {

  @Autowired
  private PersonRoleRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonRole findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonRole> findAll() {
    return repo.findAll();
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

