package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectService implements BaseService<Project> {

  @Autowired
  private ProjectRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectCache", key = "#id")
  public Project findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Project> findList(List<Integer> idList) {
    return repo.findAll(idList);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Project> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Project findByCode(String code) {
    return repo.findByCode(code);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Project findByCodeAndIdNot(String code, Integer id) {
    return repo.findByCodeAndIdNot(code, id);
  }

  @PreAuthorize(Authz.ADMIN)
  public Project create(Project entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectCache", key = "#entity.getId()")
  public Project update(Project entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "ProjectCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

