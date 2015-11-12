package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonPropertyRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonPropertyService extends BaseService<PersonProperty> {

  @Autowired
  private PersonPropertyRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonProperty findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProperty> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonProperty create(PersonProperty entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public PersonProperty update(PersonProperty entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

