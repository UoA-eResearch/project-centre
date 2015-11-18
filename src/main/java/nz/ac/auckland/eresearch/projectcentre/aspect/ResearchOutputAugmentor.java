package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResearchOutputAugmentor {

  @Autowired
  private ResearchOutputTypeService researchOutputTypeService;
  @Autowired
  private PersonService personService;

//  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService.findByProjectId(..)) && args(projectId)")
//  public List<ResearchOutput> augmentResearchOutputFindByPersonId(ProceedingJoinPoint method, int projectId) throws Throwable {
//    List<ResearchOutput> ros = (List<ResearchOutput>) method.proceed(new Object[]{projectId});
//    if (ros != null) {
//      for (ResearchOutput ro : ros) {
//        Integer id = ro.getTypeId();
//        if (id != null) {
//          ResearchOutputType rot = this.researchOutputTypeService.findOne(id);
//          ro.setType((rot == null) ? null : rot.getName());
//        }
//
//        id = ro.getPersonId();
//        if (id != null) {
//          Person p = this.personService.findOne(id);
//          ro.setPersonFullName((p == null) ? null : p.getFullName());
//        }
//      }
//    }
//    return ros;
//  }

}
