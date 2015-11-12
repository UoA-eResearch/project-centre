package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectFacility;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectFacilityRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectFacilityService extends BaseService<ProjectFacility> {

  @Autowired
  private ProjectFacilityRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectFacility findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectFacility> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectFacility> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectFacility create(ProjectFacility entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectFacility update(ProjectFacility entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

