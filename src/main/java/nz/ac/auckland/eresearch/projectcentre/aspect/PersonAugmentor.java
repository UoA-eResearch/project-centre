package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class PersonAugmentor {

  @Autowired
  private DivisionService divisionService;
  @Autowired
  private PersonStatusService personStatusService;
  @Autowired
  private DivisionalRoleService divisionalRoleService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.PersonService.findOne(*)) && args(id)")
  public Person augmentPersonFindOne(ProceedingJoinPoint method, Integer id) throws Throwable {
    Person p = (Person) method.proceed(new Object[]{id});
    this.augmentPerson(p);
    return p;
  }

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.PersonService.findAll())")
  public List<Person> augmentPersonFindAll(ProceedingJoinPoint method) throws Throwable {
    List<Person> persons = (List<Person>) method.proceed();
    this.augmentPersons(persons);
    return persons;
  }

  private void augmentPersons(List<Person> persons) {
    if (persons != null) {
      for (Person p : persons) {
        this.augmentPerson(p);
      }
    }
  }

  private void augmentPerson(Person p) {
    if (p != null) {

      Map<Integer, Integer> affiliations = p.getAffiliations();
//			Integer instId = p.getInstitutionId();
//			if (instId != null) {
//				Institution inst = this.institutionService.findOne(instId);
//				p.setInstitution((inst == null) ? null : inst.getName());
//			}

//			Integer divId = p.getDivisionId();
//			if (divId != null) {
//				Division div = this.divisionService.findOne(divId);
//				p.setDivisions((div == null) ? null : div.getName());
//			}
//
//			Integer depId = p.getDepartmentId();
//			if (depId != null) {
//				Department dep = this.departmentService.findOne(depId);
//				p.setDepartment((dep == null) ? null : dep.getName());
//			}

//      Integer statusId = p.getStatusId();
//      if (statusId != null) {
//        PersonStatus status = this.personStatusService.findOne(statusId);
//        p.setStatus((status == null) ? null : status.getName());
//      }

//			Integer instRoleId = p.getInstitutionalRoleId();
//			if (instRoleId != null) {
//				DivisionalRole instRole = this.institutionalRoleService.findOne(instRoleId);
//				p.setInstitutionalRole((instRole == null) ? null : instRole.getName());
//			}
    }
  }

}
