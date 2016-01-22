package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.service.KpiCategoryService;
import nz.ac.auckland.eresearch.projectcentre.service.KpiService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ProjectKpiAugmentor {

	@Autowired
	private KpiService kpiService;
	@Autowired
	private KpiCategoryService kpiCatService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ProjectService projectService;

	@Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ProjectKpiService.findByProjectId(*)) && args(projectId)")
	public List<ProjectKpi> augmentProjectKpiFindByProjectId(
			ProceedingJoinPoint method, Integer projectId) throws Throwable {
		List<ProjectKpi> pks = (List<ProjectKpi>) method.proceed(new Object[] { projectId });
		this.augmentProjectKpis(pks);
		return pks;
	}

	@Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ProjectKpiService.findAll())")
	public List<ProjectKpi> augmentProjectKpiFindAll(ProceedingJoinPoint method)
			throws Throwable {
		List<ProjectKpi> pks = (List<ProjectKpi>) method.proceed();
		this.augmentProjectKpis(pks);
		return pks;
	}

	private void augmentProjectKpis(List<ProjectKpi> projectKpis) {
		if (projectKpis != null) {
			Iterable<Project> projects = this.projectService.findAll();
			Map<Integer, String> projectMap = new HashMap<Integer, String>();
			if (projects != null) {
				for (Project pr : projects) {
					projectMap.put(pr.getId(), pr.getCode());
				}
			}
			for (ProjectKpi pk : projectKpis) {
				this.augmentProjectKpi(pk, projectMap);
			}
		}
	}

	private void augmentProjectKpi(ProjectKpi pk,
			Map<Integer, String> projectMap) {
		if (pk != null) {
			Integer id = pk.getKpiId();
			if (id != null) {
				Kpi kpi = this.kpiService.findOne(id);
				pk.setKpi((kpi == null) ? null : kpi);
			}

			id = pk.getKpiCategoryId();
			if (id != null) {
				KpiCategory kpiCat = this.kpiCatService.findOne(id);
				pk.setKpiCategory((kpiCat == null) ? null : kpiCat);
			}

			id = pk.getPersonId();
			if (id != null) {
				Person p = this.personService.findOne(id);
				pk.setPersonFullName((p == null) ? null : p.getFullName());
			}

			id = pk.getProjectId();
			if (id != null) {
				String projectCode = projectMap.get(id);
				pk.setProjectCode((projectCode == null) ? null : projectCode);
			}
		}
	}

	public void setKpiService(KpiService kpiService) {
		this.kpiService = kpiService;
	}

	public void setKpiCatService(KpiCategoryService kpiCatService) {
		this.kpiCatService = kpiCatService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
