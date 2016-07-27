package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceOwnerService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceOwner;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceOwnerConverter implements TypeConverter<ServiceOwner, ServiceOwnerGet, ServiceOwnerPut, ServiceOwnerPost> {

  @Autowired
  private ServiceService serviceService;
  @Autowired
  private ServiceOwnerService serviceOwnerService;
  
  @Override
  public ServiceOwnerGet entity2Get(ServiceOwner in, Map<String, Integer> idMap) throws Exception {
    ServiceOwnerGet out = new ServiceOwnerGet();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public ServiceOwnerPut entity2Put(ServiceOwner in, Map<String, Integer> idMap) throws Exception {
    ServiceOwnerPut out = new ServiceOwnerPut();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public ServiceOwner put2Entity(ServiceOwnerPut in, Map<String, Integer> idMap) throws Exception {
    ServiceOwner out = new ServiceOwner();
    BeanUtils.copyProperties(out, in);
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ServiceOwner post2Entity(ServiceOwnerPost in, Map<String, Integer> idMap) throws Exception {
    ServiceOwner out = new ServiceOwner();
    BeanUtils.copyProperties(out, in);
    return out;
  }

}
