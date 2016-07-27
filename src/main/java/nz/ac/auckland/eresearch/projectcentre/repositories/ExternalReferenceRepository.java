package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectExternalReference;

import org.springframework.data.repository.CrudRepository;

public interface ExternalReferenceRepository extends CrudRepository<ProjectExternalReference, Integer> {

	  List<ProjectExternalReference> findByProjectId(Integer projectId);
      ProjectExternalReference findByIdAndProjectId(Integer id, Integer projectId);

}
