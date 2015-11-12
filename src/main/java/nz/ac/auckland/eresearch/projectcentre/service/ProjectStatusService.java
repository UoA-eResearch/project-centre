package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectStatusService extends BaseService<ProjectStatus> {

  @Autowired
  private ProjectStatusRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectStatusCache", key = "#id")
  public ProjectStatus findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectStatus> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectStatus create(ProjectStatus entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectStatusCache", key = "#entity.getId()")
  public ProjectStatus update(ProjectStatus entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectStatusCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

