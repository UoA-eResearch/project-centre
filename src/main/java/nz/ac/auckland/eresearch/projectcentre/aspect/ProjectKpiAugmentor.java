package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.service.KpiCategoryService;
import nz.ac.auckland.eresearch.projectcentre.service.KpiService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class ProjectKpiAugmentor {

  @Autowired
  private KpiService kpiService;
  @Autowired
  private KpiCategoryService kpiCatService;
  @Autowired
  private PersonService personService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ProjectKpiService.findByProjectId(*)) && args(projectId)")
  public List<ProjectKpi> augmentProjectKpiFindByProjectId(ProceedingJoinPoint method, Integer projectId) throws Throwable {
    List<ProjectKpi> pks = (List<ProjectKpi>) method.proceed(new Object[]{projectId});
    this.augmentProjectKpis(pks);
    return pks;
  }

  private void augmentProjectKpis(List<ProjectKpi> projectKpis) {
    if (projectKpis != null) {
      for (ProjectKpi pk : projectKpis) {
        this.augmentProjectKpi(pk);
      }
    }
  }

  private void augmentProjectKpi(ProjectKpi pk) {
    if (pk != null) {
      Integer id = pk.getKpiId();
      if (id != null) {
        Kpi kpi = this.kpiService.findOne(id);
        pk.setKpiType((kpi == null) ? null : kpi.getType());
        pk.setKpiNumber((kpi == null) ? null : kpi.getNumber());
      }

      id = pk.getKpiCategoryId();
      if (id != null) {
        KpiCategory kpiCat = this.kpiCatService.findOne(id);
        pk.setKpiCategory((kpiCat == null) ? null : kpiCat.getName());
      }

      id = pk.getPersonId();
      if (id != null) {
        Person p = this.personService.findOne(id);
        pk.setPersonFullName((p == null) ? null : p.getFullName());
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
