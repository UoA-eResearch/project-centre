package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ExternalReferenceRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectExternalReference;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ExternalReferenceService implements BaseService<ProjectExternalReference> {

  @Autowired
  private ExternalReferenceRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectExternalReference findOne(Integer id, Map<String, Integer> idMap) {
    return this.findByIdAndProjectId(id, idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectExternalReference> findAll(Map<String, Integer> idMap) {
    return this.findByProjectId(idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectExternalReference> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }
  

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectExternalReference findByIdAndProjectId(Integer id, Integer projectId) {
    return repo.findByIdAndProjectId(id, projectId);
  }
  
  @PreAuthorize(Authz.ADMIN)
  public ProjectExternalReference create(ProjectExternalReference entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectExternalReference update(ProjectExternalReference entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

