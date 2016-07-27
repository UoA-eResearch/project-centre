package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class DivisionService implements BaseService<Division> {

  @Autowired
  private DivisionRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "DivisionCache", key = "#id")
  public Division findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Division> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.ADMIN)
  public Division create(Division entity) {
    Division newDiv = repo.save(entity);
    return newDiv;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#entity.getId()")
  public Division update(Division entity) throws Exception {
    Division div = repo.saveAndFlush(entity);
//    shadow.updateDivisionChildren(div);
    return div;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Division findByCode(String code) {
    return repo.findByCode(code);
  }
}

