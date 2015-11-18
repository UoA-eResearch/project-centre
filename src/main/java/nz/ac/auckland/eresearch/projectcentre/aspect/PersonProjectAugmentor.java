package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersonProjectAugmentor {

  @Autowired
  private PersonService personService;
  @Autowired
  private ProjectService projectService;
  @Autowired
  private PersonRoleService personRoleService;

//  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.PersonProjectService.findByPersonId(..)) && args(personId)")
//  public List<PersonProject> augmentProjectFindByPersonId(ProceedingJoinPoint method, int personId) throws Throwable {
//    List<PersonProject> pps = (List<PersonProject>) method.proceed(new Object[]{personId});
//    if (pps != null) {
//      for (PersonProject pp : pps) {
//        Integer projectId = pp.getProjectId();
//        if (projectId != null) {
//          Project p = this.projectService.findOne(projectId);
//          pp.setProject(p);
//        }
//
//        Integer personRoleId = pp.getPersonRoleId();
//        if (personRoleId != null) {
//          PersonRole personRole = this.personRoleService.findOne(personRoleId);
//          pp.setPersonRole((personRole == null) ? null : personRole.getName());
//        }
//      }
//    }
//    return pps;
//  }
//
//  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.PersonProjectService.findByProjectId(..)) && args(projectId)")
//  public List<PersonProject> augmentProjectFindByProjectId(ProceedingJoinPoint method, int projectId) throws Throwable {
//    List<PersonProject> pps = (List<PersonProject>) method.proceed(new Object[]{projectId});
//    if (pps != null) {
//      for (PersonProject pp : pps) {
//        Integer personId = pp.getPersonId();
//        if (personId != null) {
//          Person p = this.personService.findOne(personId);
//          pp.setPerson(p);
//        }
//
//        Integer personRoleId = pp.getPersonRoleId();
//        if (personRoleId != null) {
//          PersonRole personRole = this.personRoleService.findOne(personRoleId);
//          pp.setPersonRole((personRole == null) ? null : personRole.getName());
//        }
//      }
//    }
//    return pps;
//  }
//
//  public void setPersonService(PersonService personService) {
//    this.personService = personService;
//  }
//
//  public void setProjectService(ProjectService projectService) {
//    this.projectService = projectService;
//  }

  public void setPersonRoleService(PersonRoleService personRoleService) {
    this.personRoleService = personRoleService;
  }

}
