package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.ResearchOutputTypeRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

// FIXME: Add caching
// FIXME: Do we need to find by project???
@Component
public class ResearchOutputTypeService implements IdNameTypeService<ResearchOutputType> {

  @Autowired
  private ResearchOutputTypeRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ResearchOutputType findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public ResearchOutputType findByName(String name) {
    return repo.findByName(name);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ResearchOutputType> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public ResearchOutputType create(ResearchOutputType entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public ResearchOutputType update(ResearchOutputType entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

