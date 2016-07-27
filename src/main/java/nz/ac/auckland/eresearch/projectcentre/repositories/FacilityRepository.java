package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Facility;

import org.springframework.data.repository.CrudRepository;

public interface FacilityRepository extends CrudRepository<Facility, Integer> {
  
  Facility findByName(String name);
}
