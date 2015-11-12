package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.entity.Institution;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by markus on 9/11/15.
 */
public interface InstitutionRepository extends CrudRepository<Institution, Integer> {
  Institution findByCode(String instCode);
}
