package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResearchOutputConverter implements TypeConverter<ResearchOutput, ResearchOutputGet, ResearchOutputPut, ResearchOutputPost> {

  @Autowired
  private ResearchOutputTypeService researchOutputTypeService;
  
  @Override
  public ResearchOutputGet entity2Get(ResearchOutput in, Map<String, Integer> idMap) throws Exception {
    ResearchOutputGet out = new ResearchOutputGet();
    BeanUtils.copyProperties(out, in);
    ResearchOutputType rot = researchOutputTypeService.findOne(in.getTypeId(), idMap);
    if (rot != null) {
      out.setType(rot.getName());
    } else {
      throw new IllegalArgumentException("No research output type with with id " + in.getTypeId());
    }
    return out;
  }

  @Override
  public ResearchOutputPut entity2Put(ResearchOutput in, Map<String, Integer> idMap) throws Exception {
    ResearchOutputPut out = new ResearchOutputPut();
    BeanUtils.copyProperties(out, in);
    out.setType(researchOutputTypeService.findOne(in.getTypeId(), null).getName());
    return out;
  }

  @Override
  public ResearchOutput put2Entity(ResearchOutputPut in, Map<String, Integer> idMap) throws Exception {
    ResearchOutput out = this.post2Entity(in, idMap);
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ResearchOutput post2Entity(ResearchOutputPost in, Map<String, Integer> idMap) throws Exception {
    ResearchOutput out = new ResearchOutput();
    BeanUtils.copyProperties(out, in);
    ResearchOutputType rot = this.researchOutputTypeService.findByName(in.getType());
    if (rot != null) {
      out.setTypeId(rot.getId());      
    }
    out.setProjectId(idMap.get("projectId"));
    return out;
  }

}
