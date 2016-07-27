package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectDivisionService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDivisionConverter implements TypeConverter<ProjectDivision, ProjectDivisionGet, ProjectDivisionPut, ProjectDivisionPost> {

  @Autowired
  private ProjectDivisionService projectMemberService;
  @Autowired
  private DivisionService divisionService;
  @Autowired
  private DivisionConverter divisionConverter;

  @Override
  public ProjectDivisionGet entity2Get(ProjectDivision in, Map<String, Integer> idMap) throws Exception {
    ProjectDivisionGet out = new ProjectDivisionGet();
    out.setId(in.getId());
    Division tmp = divisionService.findOne(in.getDivisionId(), idMap);
    out.setDivision(divisionConverter.entity2Get(tmp, new MapUtil("id", tmp.getId()).create()));
    return out;
  }

  @Override
  public ProjectDivisionPut entity2Put(ProjectDivision in, Map<String, Integer> idMap) throws Exception {
    ProjectDivisionPut out = new ProjectDivisionPut();
    out.setDivisionId(in.getDivisionId());
    return out;
  }

  @Override
  public ProjectDivision put2Entity(ProjectDivisionPut in, Map<String, Integer> idMap) throws Exception {
    ProjectDivision out = new ProjectDivision();
    out.setDivisionId(in.getDivisionId());
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public ProjectDivision post2Entity(ProjectDivisionPost in, Map<String, Integer> idMap) throws Exception {
    return this.put2Entity(in, idMap);
  }

}
