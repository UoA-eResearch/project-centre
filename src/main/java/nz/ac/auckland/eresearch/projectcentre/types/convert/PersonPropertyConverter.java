package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonPropertyConverter implements TypeConverter<PersonProperty, PersonPropertyGet, PersonPropertyPut, PersonPropertyPost> {

  @Autowired
  private PersonService personService;
  @Autowired
  private PersonPropertyService personPropertyService;
  
  @Override
  public PersonPropertyGet entity2Get(PersonProperty in, Map<String, Integer> idMap) throws Exception {
    PersonPropertyGet out = new PersonPropertyGet();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public PersonPropertyPut entity2Put(PersonProperty in, Map<String, Integer> idMap) throws Exception {
    PersonPropertyPut out = new PersonPropertyPut();
    BeanUtils.copyProperties(out, in);
    return out;
  }

  @Override
  public PersonProperty put2Entity(PersonPropertyPut in, Map<String, Integer> idMap) throws Exception {
    PersonProperty out = new PersonProperty();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public PersonProperty post2Entity(PersonPropertyPost in, Map<String, Integer> idMap) throws Exception {
    PersonProperty out = this.post2Entity(in, idMap);    
    return out;
  }

}
