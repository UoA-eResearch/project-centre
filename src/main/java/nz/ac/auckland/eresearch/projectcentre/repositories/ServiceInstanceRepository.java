package nz.ac.auckland.eresearch.projectcentre.repositories;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstance;

import org.springframework.data.repository.CrudRepository;

public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, Integer> {

  List<ServiceInstance> findByProjectId(Integer projectId);
  ServiceInstance findByIdAndProjectId(Integer id, Integer projectId);

}
