package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectPropertyConverter implements TypeConverter<ProjectProperty, ProjectPropertyGet, ProjectPropertyPut, ProjectPropertyPost> {

  @Autowired
  private ProjectService projectService;
  @Autowired
  private ProjectPropertyService projectPropertyService;
  @Autowired
  private FacilityService facilityService;
  
  @Override
  public ProjectPropertyGet entity2Get(ProjectProperty in, Map<String, Integer> idMap) throws Exception {
    ProjectPropertyGet out = new ProjectPropertyGet();
    BeanUtils.copyProperties(out, in);
    Facility tmp = facilityService.findOne(in.getFacilityId(), null);
    if (tmp != null) {
      out.setFacility(tmp.getName());
    }
    return out;
  }

  @Override
  public ProjectPropertyPut entity2Put(ProjectProperty in, Map<String, Integer> idMap) throws Exception {
    ProjectPropertyPut out = new ProjectPropertyPut();
    BeanUtils.copyProperties(out, in);
    Facility tmp = facilityService.findOne(in.getFacilityId(), null);
    if (tmp != null) {
      out.setFacility(tmp.getName());
    }
    return out;
  }

  @Override
  public ProjectProperty put2Entity(ProjectPropertyPut in, Map<String, Integer> idMap) throws Exception {
    ProjectProperty out = new ProjectProperty();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    Facility tmp = facilityService.findByName(in.getFacility());
    if (tmp != null) {
      out.setFacilityId(tmp.getId());
    }
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ProjectProperty post2Entity(ProjectPropertyPost in, Map<String, Integer> idMap) throws Exception {
    ProjectProperty out = this.post2Entity(in, idMap);    
    return out;
  }

}
