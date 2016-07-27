package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstanceStatus;

import org.springframework.data.repository.CrudRepository;

public interface ServiceInstanceStatusRepository extends CrudRepository<ServiceInstanceStatus, Integer> {

  ServiceInstanceStatus findByName(String name);
}
