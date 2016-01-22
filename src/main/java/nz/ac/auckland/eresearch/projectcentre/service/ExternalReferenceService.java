package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ExternalReference;
import nz.ac.auckland.eresearch.projectcentre.repositories.ExternalReferenceRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ExternalReferenceService extends BaseService<ExternalReference> {

  @Autowired
  private ExternalReferenceRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ExternalReference findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ExternalReference> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ExternalReference> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }
  
  @PreAuthorize(Authz.ADMIN)
  public ExternalReference create(ExternalReference entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public ExternalReference update(ExternalReference entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

