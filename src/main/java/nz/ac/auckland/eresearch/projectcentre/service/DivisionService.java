package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DivisionService extends BaseService<Division> {

  @Autowired
  private DivisionRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "DivisionCache", key = "#id")
  public Division findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Division> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Division> findByInstitutionId(Integer id) {
    return repo.findByInstitutionId(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Division findById(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.ADMIN)
  public Division create(Division entity) {
    Division newDiv = repo.save(entity);
    return newDiv;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#entity.getId()")
  public Division update(Division entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }
}

