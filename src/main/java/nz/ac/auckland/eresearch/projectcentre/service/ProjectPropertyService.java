package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectPropertyRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectPropertyService extends BaseService<ProjectProperty> {

  @Autowired
  private ProjectPropertyRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectProperty findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectProperty> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectProperty> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectProperty create(ProjectProperty entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectProperty update(ProjectProperty entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }
}

