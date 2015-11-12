package nz.ac.auckland.eresearch.projectcentre.aspect;

import com.google.common.collect.Lists;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.InstitutionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class ProjectAugmentor {

  @Autowired
  private InstitutionService institutionService;
  @Autowired
  private DivisionService divisionService;
  @Autowired
  private ProjectStatusService projectStatusService;
  @Autowired
  private ProjectTypeService projectTypeService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.repositories.ProjectRepository.findOne(*)) && args(id)")
  public Project augmentProjectFindOne(ProceedingJoinPoint method, Integer id) throws Throwable {
    Project p = (Project) method.proceed(new Object[]{id});
    this.augmentProject(p);
    return p;
  }

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.repositories.ProjectRepository.findByCode(*)) && args(code)")
  public List<Project> augmentProjectFindByTitle(ProceedingJoinPoint method, String code) throws Throwable {
    List<Project> projects = (List<Project>) method.proceed(new Object[]{code});
    this.augmentProjects(projects);
    return projects;
  }

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.repositories.ProjectRepository.findAll())")
  public List<Project> augmentProjectFindAll(ProceedingJoinPoint method) throws Throwable {
    List<Project> projects = (List<Project>) method.proceed();
    this.augmentProjects(projects);
    return projects;
  }

  private void augmentProjects(List<Project> projects) {
    if (projects != null) {
      for (Project p : projects) {
        this.augmentProject(p);
      }
    }
  }

  private void augmentProject(Project p) {
    if (p != null) {


      List<Integer> divIds = p.getDivisionIds();
      if (divIds != null && divIds.size() > 0) {
        List<String> divStrings = Lists.newArrayList();
        for (Integer divId : divIds) {
          Division div = this.divisionService.findOne(divId);
          divStrings.add(div.getName());
        }
        p.setDivisions(divStrings);
      }


      Integer statusId = p.getStatusId();
      if (statusId != null) {
        ProjectStatus stat = this.projectStatusService.findOne(statusId);
        p.setStatus((stat == null) ? null : stat.getName());
      }

      Integer typeId = p.getTypeId();
      if (typeId != null) {
        ProjectType type = this.projectTypeService.findOne(typeId);
        p.setType((type == null) ? null : type.getName());
      }
    }
  }

}
