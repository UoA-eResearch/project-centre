package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.repositories.KpiCategoryRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class KpiCategoryService extends BaseService<KpiCategory> {

  @Autowired
  private KpiCategoryRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public KpiCategory findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<KpiCategory> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public KpiCategory create(KpiCategory entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public KpiCategory update(KpiCategory entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }
}

