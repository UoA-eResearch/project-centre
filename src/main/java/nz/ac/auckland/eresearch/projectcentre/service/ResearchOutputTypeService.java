package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.repositories.ResearchOutputTypeRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ResearchOutputTypeService extends BaseService<ResearchOutputType> {

  @Autowired
  private ResearchOutputTypeRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public ResearchOutputType findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<ResearchOutputType> findAll() {
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

