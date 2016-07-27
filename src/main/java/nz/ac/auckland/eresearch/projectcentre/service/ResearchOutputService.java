package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectResearchOutputRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ResearchOutputService implements BaseService<ResearchOutput> {

  @Autowired
  private ProjectResearchOutputRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ResearchOutput findOne(Integer id, Map<String, Integer> idMap) {
    return this.findByIdAndProjectId(id, idMap.get("projectId"));
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ResearchOutput findByIdAndProjectId(Integer id, Integer projectId) {
    return repo.findByIdAndProjectId(id, projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<ResearchOutput> findByProjectId(Integer projectId) {
    return repo.findByProjectId(projectId);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ResearchOutput> findAll(Map<String, Integer> idMap) {
    return this.findByProjectId(idMap.get("projectId"));
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ResearchOutput create(ResearchOutput entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.EDIT_PROJECT)
  public ResearchOutput update(@P("entity") ResearchOutput entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

