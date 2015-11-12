package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectPropertyValidator implements Validator {

  @Autowired
  FacilityService facilityService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectProperty.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object personProject, Errors errors) {
    ProjectProperty pp = (ProjectProperty) personProject;
    String[] notEmpty = {"facilityId", "projectId", "propname", "propvalue"};
    new RejectEmptyValidator(ProjectProperty.class, notEmpty).validate(personProject, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(pp.getProjectId(), errors);
      this.validateFacilityId(pp.getFacilityId(), errors);
    }
  }

  public void validateFacilityId(Integer id, Errors errors) {
    Facility f = facilityService.findOne(id);
    if (f == null) {
      errors.rejectValue("facilityId", "facility.id.invalid");
    }
  }

}
