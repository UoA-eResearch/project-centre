package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectMemberRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberService implements BaseService<ProjectMember> {

  @Autowired
  private ProjectMemberRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectMember findOne(Integer id, Map<String, Integer> idMap) {
    return this.findByIdAndProjectId(id, idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectMember> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ProjectMember findByIdAndProjectId(Integer id, Integer projectId) {
    return repo.findByIdAndProjectId(id, projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ProjectMember> findByPersonId(Integer personId) {
    return repo.findByPersonId(personId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ProjectMember> findAll(Map<String, Integer> idMap) {
    return this.findByProjectId(idMap.get("projectId"));
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectMember create(ProjectMember entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ProjectMember update(ProjectMember entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

