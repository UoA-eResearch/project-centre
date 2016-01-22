package nz.ac.auckland.eresearch.projectcentre.aspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
  @Autowired
  private ProjectService projectService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService.findByProjectId(..)) && args(projectId)")
  public List<ResearchOutput> augmentResearchOutputFindByProjectId(ProceedingJoinPoint method, int projectId) throws Throwable {
    List<ResearchOutput> ros = (List<ResearchOutput>) method.proceed(new Object[]{projectId});
    this.augmentResearchOutput(ros);
    return ros;
  }

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService.findAll())")
  public List<ResearchOutput> augmentResearchOutputFindAll(ProceedingJoinPoint method) throws Throwable {
    List<ResearchOutput> ros = (List<ResearchOutput>) method.proceed();
    this.augmentResearchOutput(ros);
    return ros;
  }

	private void augmentResearchOutput(List<ResearchOutput> ros) {
		if (ros != null) {
			Iterable<Project> projects = this.projectService.findAll();
			Map<Integer, String> tmpMap = new HashMap<Integer,String>();
			if (projects != null) {
				for (Project pr: projects) {
					tmpMap.put(pr.getId(), pr.getCode());
				}				
			}
			
			for (ResearchOutput ro : ros) {
				Integer id = ro.getTypeId();
				if (id != null) {
					ResearchOutputType rot = this.researchOutputTypeService.findOne(id);
					ro.setTypeName((rot == null) ? null : rot.getName());
				}

				id = ro.getPersonId();
				if (id != null) {
					Person p = this.personService.findOne(id);
					ro.setPersonFullName((p == null) ? null : p.getFullName());
				}

				id = ro.getProjectId();
				if (id != null) {
					String projectCode = tmpMap.get(id);
					ro.setProjectCode((projectCode == null) ? null : projectCode);
				}
			}
		}
	}
}
