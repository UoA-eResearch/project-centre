package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.entity.ExternalReference;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by markus on 9/11/15.
 */
public interface ExternalReferenceRepository extends CrudRepository<ExternalReference, Integer> {

	  List<ExternalReference> findByProjectId(Integer projectId);

}
