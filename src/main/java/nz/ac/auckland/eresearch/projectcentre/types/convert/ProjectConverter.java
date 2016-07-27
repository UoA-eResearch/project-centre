package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectDivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements TypeConverter<Project, ProjectGet, ProjectPut, ProjectPost> {

  @Autowired
  private ProjectStatusService projectStatusService;
  @Autowired
  private ProjectTypeService projectTypeService;
  @Autowired
  private DivisionService divisionService;
  @Autowired
  private ProjectDivisionService projectDivisionService;
  @Autowired
  private ProjectDivisionConverter projectDivisionConverter;

  @Override
  public ProjectGet entity2Get(Project in, Map<String, Integer> idMap) throws Exception {
    ProjectGet out = new ProjectGet();
    BeanUtils.copyProperties(out, in);
    out.setStatus(projectStatusService.findOne(in.getStatusId(), null).getName());
    out.setType(projectTypeService.findOne(in.getTypeId(), null).getName());
    List<ProjectDivision> pds = projectDivisionService.findByProjectId(in.getId());
    if (pds != null) {
      List<ProjectDivisionGet> pdGets = new LinkedList<ProjectDivisionGet>();
      for (ProjectDivision pd: pds) {
        pdGets.add(projectDivisionConverter.entity2Get(pd, idMap));
      }
      out.setDivisions(pdGets);
    }
    return out;
  }

  @Override
  public ProjectPut entity2Put(Project in, Map<String, Integer> idMap) throws Exception {
    ProjectPut out = new ProjectPut();
    BeanUtils.copyProperties(out, in);
    out.setStatus(projectStatusService.findOne(in.getStatusId(), null).getName());
    out.setType(projectTypeService.findOne(in.getTypeId(), null).getName());
    return out;
  }

  @Override
  public Project put2Entity(ProjectPut in, Map<String, Integer> idMap) throws Exception {
    Project out = new Project();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    if (in.getStatus() != null) {
      ProjectStatus ps = projectStatusService.findByName(in.getStatus());
      if (ps != null) {
        out.setStatusId(ps.getId());        
      } else {
        out.setStatusId(0);
      }
    }
    if (in.getType() != null) {
      ProjectType pt = projectTypeService.findByName(in.getType());
      if (pt != null) {
        out.setTypeId(pt.getId());      
      } else {
        out.setTypeId(0);
      }
    }
    return out;  
  }
  
  @Override
  public Project post2Entity(ProjectPost in, Map<String, Integer> idMap) throws Exception {
    Project out = new Project();
    BeanUtils.copyProperties(out, in);
    if (in.getStatus() != null) {
      ProjectStatus ps = projectStatusService.findByName(in.getStatus());
      if (ps != null) {
        out.setStatusId(ps.getId());        
      }
    }
    if (in.getType() != null) {
      ProjectType pt = projectTypeService.findByName(in.getType());
      if (pt != null) {
        out.setTypeId(pt.getId());      
      }
    }
    return out;
  }

}
