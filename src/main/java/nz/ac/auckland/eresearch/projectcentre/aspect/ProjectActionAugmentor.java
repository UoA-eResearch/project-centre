package nz.ac.auckland.eresearch.projectcentre.aspect;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionTypeService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProjectActionAugmentor {

  @Autowired
  private ProjectActionTypeService projectActionTypeService;
  @Autowired
  private PersonService personService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService.findByProjectId(*)) && args(projectId)")
  public List<ProjectAction> augmentProjectActionFindByProjectId(ProceedingJoinPoint method, Integer projectId) throws Throwable {
    List<ProjectAction> pas = (List<ProjectAction>) method.proceed(new Object[]{projectId});
    this.augmentProjectActions(pas);
    return pas;
  }

  private void augmentProjectActions(List<ProjectAction> projectActions) {
    if (projectActions != null) {
      for (ProjectAction pa : projectActions) {
        this.augmentProjectAction(pa);
      }
    }
  }

  private void augmentProjectAction(ProjectAction pa) {
    if (pa != null) {
      Integer id = pa.getActionTypeId();
      if (id != null) {
        ProjectActionType pat = this.projectActionTypeService.findOne(id);
        pa.setActionTypeName((pat == null) ? null : pat.getName());
      }

      id = pa.getPersonId();
      if (id != null) {
        Person p = this.personService.findOne(id);
        pa.setPersonFullName((p == null) ? null : p.getFullName());
      }

    }
  }

  public void setProjectActionTypeService(ProjectActionTypeService projectActionTypeService) {
    this.projectActionTypeService = projectActionTypeService;
  }

  public void setPersonService(PersonService personService) {
    this.personService = personService;
  }

}
