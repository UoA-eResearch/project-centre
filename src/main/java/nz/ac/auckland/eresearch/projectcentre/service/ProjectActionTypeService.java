package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectActionTypeRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectActionTypeService implements IdNameTypeService<ProjectActionType> {

  @Autowired
  private ProjectActionTypeRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectActionTypeCache", key = "#id")
  public ProjectActionType findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectActionType> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectActionType findByName(String name) {
    return repo.findByName(name);
  }

  @PreAuthorize(Authz.ADMIN)
  public ProjectActionType create(ProjectActionType entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectActionTypeCache", key = "#entity.getId()")
  public ProjectActionType update(ProjectActionType entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectActionTypeCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

