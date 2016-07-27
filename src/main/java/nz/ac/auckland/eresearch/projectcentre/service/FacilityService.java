package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.repositories.FacilityRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class FacilityService implements IdNameTypeService<Facility> {

  @Autowired
  private FacilityRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public Facility findOne(Integer id, Map<String, Integer> idMap) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Facility> findAll(Map<String, Integer> idMap) {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Facility> findAll(List<Integer> facilityIds) {
    return repo.findAll(facilityIds);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Facility findByName(String name) {
    return repo.findByName(name);
  }

  @PreAuthorize(Authz.ADMIN)
  public Facility create(Facility entity) {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public Facility update(Facility entity) throws Exception {
    return repo.save(entity);
  }

  @PreAuthorize(Authz.ADMIN)
  public void delete(Integer id) {
    repo.delete(id);
  }
}
