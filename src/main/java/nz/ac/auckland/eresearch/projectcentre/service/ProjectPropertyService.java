package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectPropertyRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectPropertyService implements BaseService<ProjectProperty> {

  @Autowired
  private ProjectPropertyRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectProperty findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findByIdAndProjectId(id, idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectProperty> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectProperty> findAll(Map<String, Integer> idMap) {
    return this.findByProjectId(idMap.get("projectId"));
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

