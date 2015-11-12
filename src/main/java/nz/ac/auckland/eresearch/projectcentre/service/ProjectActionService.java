package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectActionRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectActionService extends BaseService<ProjectAction> {

  @Autowired
  private ProjectActionRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectAction findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectAction> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectAction> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectAction create(ProjectAction entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectAction update(ProjectAction entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

