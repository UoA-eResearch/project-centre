package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectTypeRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectTypeService extends BaseService<ProjectType> {

  @Autowired
  private ProjectTypeRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectTypeCache", key = "#id")
  public ProjectType findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectType> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectType create(ProjectType entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectTypeCache", key = "#entity.getId()")
  public ProjectType update(ProjectType entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectTypeCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectTypeCache", key = "#name")
  public ProjectType findByName(String name) {
    return repo.findByName(name);
  }


}

