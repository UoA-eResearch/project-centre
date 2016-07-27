package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceInstanceStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceSchemaService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstanceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstancePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstancePut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Service;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstance;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstanceStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServiceInstanceConverter implements TypeConverter<ServiceInstance, ServiceInstanceGet, ServiceInstancePut, ServiceInstancePost> {

  @Autowired
  private ServiceService serviceService;
  @Autowired
  private ServiceInstanceStatusService serviceInstanceStatusService;
  @Autowired
  private ServiceSchemaService serviceSchemaService;
  
  @Override
  public ServiceInstanceGet entity2Get(ServiceInstance in, Map<String, Integer> idMap) throws Exception {
    ServiceInstanceGet out = new ServiceInstanceGet();
    out.setId(in.getId());
    out.setInstance(new ObjectMapper().readTree(in.getInstance()));
    out.setServiceName(serviceService.findOne(in.getServiceId(), idMap).getName());
    out.setSchemaVersion(serviceSchemaService.findOne(in.getSchemaId(), idMap).getSchemaVersion());
    out.setStatus(serviceInstanceStatusService.findOne(in.getStatusId(), idMap).getName());
    return out;
  }

  @Override
  public ServiceInstancePut entity2Put(ServiceInstance in, Map<String, Integer> idMap) throws Exception {
    ServiceInstancePut out = new ServiceInstancePut();
    out.setInstance(new ObjectMapper().readTree(in.getInstance()));
    out.setStatus(serviceInstanceStatusService.findOne(in.getStatusId(), idMap).getName());
    out.setServiceName(serviceService.findOne(in.getServiceId(), idMap).getName());
    out.setSchemaVersion(serviceSchemaService.findOne(in.getSchemaId(), idMap).getSchemaVersion());
    return out;
  }

  @Override
  public ServiceInstance put2Entity(ServiceInstancePut in, Map<String, Integer> idMap) throws Exception {
    ServiceInstance out = new ServiceInstance();
    Service s = serviceService.findByName(in.getServiceName());
    if (s == null) {
      throw new IllegalArgumentException("Invalid service name");
    }
    out.setServiceId(s.getId());
    out.setProjectId(idMap.get("projectId"));
    out.setInstance(in.getInstance().toString());
    ServiceInstanceStatus sis = serviceInstanceStatusService.findByName(in.getStatus());
    if (sis == null) {
      throw new IllegalArgumentException("Invalid instance status");
    }
    out.setStatusId(sis.getId());
    ServiceSchema ss = serviceSchemaService.findByServiceIdAndSchemaVersion(s.getId(), in.getSchemaVersion());
    if (ss == null) {
      throw new IllegalArgumentException(
          "No service schema found for service name '" + s.getName() +
          "' and schema version '" + in.getSchemaVersion() + "'");
    }
    out.setSchemaId(ss.getId());
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ServiceInstance post2Entity(ServiceInstancePost in, Map<String, Integer> idMap) throws Exception {
    ServiceInstance out = new ServiceInstance();
    Service s = serviceService.findByName(in.getServiceName());
    if (s == null) {
      throw new IllegalArgumentException("Invalid service name");
    }
    out.setServiceId(s.getId());
    out.setProjectId(idMap.get("projectId"));
    out.setInstance(in.getInstance().toString());
    ServiceInstanceStatus sis = serviceInstanceStatusService.findByName(in.getStatus());
    if (sis == null) {
      throw new IllegalArgumentException("Invalid instance status");
    }
    out.setStatusId(sis.getId());
    ServiceSchema ss = serviceSchemaService.findByServiceIdAndSchemaVersion(s.getId(), in.getSchemaVersion());
    if (ss == null) {
      throw new IllegalArgumentException(
          "No service schema found for service name '" + s.getName() +
          "' and schema version '" + in.getSchemaVersion() + "'");
    }
    out.setSchemaId(ss.getId());
    return out;
  }

}
