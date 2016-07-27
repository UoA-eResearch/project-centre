package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceSchemaService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServiceSchemaConverter implements TypeConverter<ServiceSchema, ServiceSchemaGet, ServiceSchemaPut, ServiceSchemaPost> {

  @Autowired
  private ServiceService serviceService;
  @Autowired
  private ServiceSchemaService serviceOwnerService;
  
  @Override
  public ServiceSchemaGet entity2Get(ServiceSchema in, Map<String, Integer> idMap) throws Exception {
    ServiceSchemaGet out = new ServiceSchemaGet();
    BeanUtils.copyProperties(out, in);
    out.setSchema(new ObjectMapper().readTree(in.getSchemaString()));
    return out;
  }

  @Override
  public ServiceSchemaPut entity2Put(ServiceSchema in, Map<String, Integer> idMap) throws Exception {
    ServiceSchemaPut out = new ServiceSchemaPut();
    BeanUtils.copyProperties(out, in);
    out.setSchema(new ObjectMapper().readTree(in.getSchemaString()));
    return out;
  }

  @Override
  public ServiceSchema put2Entity(ServiceSchemaPut in, Map<String, Integer> idMap) throws Exception {
    ServiceSchema out = new ServiceSchema();
    BeanUtils.copyProperties(out, in);
    out.setSchemaString(in.getSchema().toString());
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ServiceSchema post2Entity(ServiceSchemaPost in, Map<String, Integer> idMap) throws Exception {
    ServiceSchema out = new ServiceSchema();
    BeanUtils.copyProperties(out, in);
    out.setSchemaString(in.getSchema().toString());
    return out;
  }

}
