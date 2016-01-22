package nz.ac.auckland.eresearch.projectcentre.aspect;

import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProjectPropertyAugmentor {

  @Autowired
  private FacilityService facilityService;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService.findByProjectId(..)) && args(projectId)")
  public List<ProjectProperty> augmentProjectPropertyFindByPersonId(ProceedingJoinPoint method, int projectId) throws Throwable {
    List<ProjectProperty> pps = (List<ProjectProperty>) method.proceed(new Object[]{projectId});
    if (pps != null) {
      for (ProjectProperty pp : pps) {
        Integer id = pp.getFacilityId();
        if (id != null) {
          Facility f = this.facilityService.findOne(id);
          pp.setFacilityName((f == null) ? null : f.getName());
        }
      }
    }
    return pps;
  }

  public void setFacilityService(FacilityService facilityService) {
    this.facilityService = facilityService;
  }

}
