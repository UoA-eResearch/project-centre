package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServicePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServicePut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Service;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceConverter implements TypeConverter<Service, ServiceGet, ServicePut, ServicePost> {

  @Autowired
  ServiceService serviceService;
  
  @Override
  public ServiceGet entity2Get(Service in, Map<String, Integer> idMap) throws Exception {
    if (in == null) {
      return null;
    } else {
      ServiceGet out = new ServiceGet();
      BeanUtils.copyProperties(out, in);
      return out;      
    }
  }

  @Override
  public ServicePut entity2Put(Service in, Map<String, Integer> idMap) throws Exception {
    ServicePut out = new ServicePut();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public Service put2Entity(ServicePut in, Map<String, Integer> idMap) throws Exception {
    Service out = new Service();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out,  idMap);
    return out;
  }

  @Override
  public Service post2Entity(ServicePost in, Map<String, Integer> idMap) throws Exception {
    Service out = new Service();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out,  idMap);
    return out;
  }

  
}
