package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.types.IdNameType;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypeGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypePut;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;

public class IdNameTypeConverter<S extends IdNameType> implements TypeConverter<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> {

  private Class<S> clazz;
  
  public IdNameTypeConverter(Class<S> clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public IdNameTypeGet entity2Get(IdNameType in, Map<String, Integer> idMap) throws Exception {
    IdNameTypeGet out = new IdNameTypeGet();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public IdNameTypePut entity2Put(IdNameType in, Map<String, Integer> idMap) throws Exception {
    IdNameTypePut out = new IdNameTypePut();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public IdNameType put2Entity(IdNameTypePut in, Map<String, Integer> idMap) throws Exception {
    S instance = clazz.newInstance();
    instance.setId(idMap.get("id"));
    instance.setName(in.getName());
    return instance;
  }
  
  @Override
  public IdNameType post2Entity(IdNameTypePost in, Map<String, Integer> idMap) throws Exception {
    S instance = clazz.newInstance();
    instance.setName(in.getName());
    return instance;
  }
  
}
