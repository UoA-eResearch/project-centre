package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;

import org.springframework.data.repository.CrudRepository;

public interface ServiceSchemaRepository extends CrudRepository<ServiceSchema, Integer> {

  ServiceSchema findByServiceIdAndSchemaVersion(Integer serviceId, String schemaVersion);

}
