package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectActionConverter implements TypeConverter<ProjectAction, ProjectActionGet, ProjectActionPut, ProjectActionPost> {

  @Autowired
  private PersonService personService;
  @Autowired
  private ProjectActionTypeService projectActionTypeService;
  
  @Override
  public ProjectActionGet entity2Get(ProjectAction in, Map<String, Integer> idMap) throws Exception {
    ProjectActionGet out = new ProjectActionGet();
    BeanUtils.copyProperties(out, in);
    ProjectActionType pat = projectActionTypeService.findOne(in.getActionTypeId(), idMap);
    if (pat != null) {
      out.setActionType(pat.getName());
    } else {
      throw new Exception("No project action type with id " + in.getId());
    }
    out.setPersonName(personService.findOne(in.getPersonId(), null).getFullName());
    return out;
  }

  @Override
  public ProjectActionPut entity2Put(ProjectAction in, Map<String, Integer> idMap) throws Exception {
    ProjectActionPut out = new ProjectActionPut();
    BeanUtils.copyProperties(out, in);
    out.setActionType(projectActionTypeService.findOne(in.getActionTypeId(), null).getName());
    return out;
  }

  @Override
  public ProjectAction put2Entity(ProjectActionPut in, Map<String, Integer> idMap) throws Exception {
    ProjectAction out = this.post2Entity(in, idMap);
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ProjectAction post2Entity(ProjectActionPost in, Map<String, Integer> idMap) throws Exception {
    ProjectAction out = new ProjectAction();
    BeanUtils.copyProperties(out, in);
    ProjectActionType pat = this.projectActionTypeService.findByName(in.getActionType());
    if (pat != null) {
      out.setActionTypeId(pat.getId());      
    }
    out.setProjectId(idMap.get("projectId"));
    return out;
  }

}
