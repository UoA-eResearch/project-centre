package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProject;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonProjectRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PersonProjectService extends BaseService<PersonProject> {

  @Autowired
  private PersonProjectRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public PersonProject findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProject> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProject> findByPersonId(Integer personId) {
    return repo.findByPersonId(personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<PersonProject> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public PersonProject create(PersonProject entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public PersonProject update(PersonProject entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

