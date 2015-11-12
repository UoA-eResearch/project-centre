package nz.ac.auckland.eresearch.projectcentre.service;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.repositories.FacilityRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by markus on 9/11/15.
 */
@Component
public class FacilityService extends BaseService<Facility> {


  @Autowired
  private FacilityRepository repo;

  @PreAuthorize(Authz.AUTHENTICATED)
  public Facility findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Facility> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Facility> findAll(List<Integer> facilityIds) {
    return repo.findAll(facilityIds);
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
