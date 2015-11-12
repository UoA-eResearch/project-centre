package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectFacility;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectFacilityValidator implements Validator {

  @Autowired
  FacilityService facilityService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectFacility.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object projectFacility, Errors errors) {
    ProjectFacility pf = (ProjectFacility) projectFacility;
    String[] notEmpty = {"projectId", "facilityId"};
    new RejectEmptyValidator(ProjectFacility.class, notEmpty).validate(projectFacility, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(pf.getProjectId(), errors);
      this.validateFacilityId(pf.getFacilityId(), errors);
    }
  }

  public void validateFacilityId(Integer facilityId, Errors errors) {
    Facility f = facilityService.findOne(facilityId);
    if (f == null) {
      errors.rejectValue("facilityId", "facility.id.invalid");
    }
  }

}
