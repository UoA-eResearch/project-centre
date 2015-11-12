package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService extends BaseService<Project> {

  @Autowired
  private ProjectRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectCache", key = "#id")
  public Project findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Project> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Project> findByCode(String code) {
    return repo.findByCode(code);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Project> findByCodeAndIdNot(String code, Integer id) {
    return repo.findByCodeAndIdNot(code, id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Project> findByDivisionId(Integer id) {
    return repo.findByDivisionId(id);
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

