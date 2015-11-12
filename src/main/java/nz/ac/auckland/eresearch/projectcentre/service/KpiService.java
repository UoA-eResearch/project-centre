package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.repositories.KpiRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class KpiService extends BaseService<Kpi> {

  @Autowired
  private KpiRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public Kpi findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Kpi> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public Kpi create(Kpi entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public Kpi update(Kpi entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }

}

