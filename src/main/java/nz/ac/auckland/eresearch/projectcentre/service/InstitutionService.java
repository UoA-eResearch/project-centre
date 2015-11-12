package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class InstitutionService extends BaseService<Institution> {

  @Autowired
  private InstitutionRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "InstitutionCache", key = "#id")
  public Institution findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Institution> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public Institution create(Institution entity) {
    Institution inst = repo.save(entity);
    return inst;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionCache", key = "#entity.getId()")
  public Institution update(Institution entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "InstitutionCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

