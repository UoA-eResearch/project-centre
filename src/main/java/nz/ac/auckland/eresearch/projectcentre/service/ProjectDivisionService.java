package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectDivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectDivisionService implements BaseService<ProjectDivision> {

  @Autowired
  private ProjectDivisionRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "ProjectDivisionCache", key = "#id")
  public ProjectDivision findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectDivision> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectDivision> findByDivisionId(Integer divisionId) {
    return repo.findByDivisionId(divisionId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectDivision> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectDivision create(ProjectDivision entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  @CacheEvict(value = "ProjectDivisionCache", key = "#entity.getId()")
  public ProjectDivision update(@P("entitity") ProjectDivision entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  @CacheEvict(value = "ProjectDivisionCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

}

