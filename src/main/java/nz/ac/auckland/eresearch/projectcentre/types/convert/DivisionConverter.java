package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DivisionConverter implements TypeConverter<Division, DivisionGet, DivisionPut, DivisionPost> {

  @Autowired
  DivisionService divisionService;
  
  @Override
  public DivisionGet entity2Get(Division in, Map<String, Integer> idMap) throws Exception {
    if (in == null) {
      return null;
    } else {
      DivisionGet out = new DivisionGet();
      BeanUtils.copyProperties(out, in);
      if (in.getParentId() != null) {
        out.setParent(entity2Get(divisionService.findOne(in.getParentId(), idMap), idMap));
      }
      return out;      
    }
  }

  @Override
  public DivisionPut entity2Put(Division in, Map<String, Integer> idMap) throws Exception {
    DivisionPut out = new DivisionPut();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public Division put2Entity(DivisionPut in, Map<String, Integer> idMap) throws Exception {
    Division out = new Division();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out,  idMap);
    return out;
  }

  @Override
  public Division post2Entity(DivisionPost in, Map<String, Integer> idMap) throws Exception {
    Division out = new Division();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out,  idMap);
    return out;
  }

  
}
