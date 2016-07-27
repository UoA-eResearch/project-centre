package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectPropertyValidator implements Validator {

  @Autowired
  ProjectPropertyService projectPropertyService;
  @Autowired
  FacilityService facilityService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectProperty.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object in, Errors errors) {
    ProjectProperty tmp = (ProjectProperty) in;
    validationUtil.checkNotEmpty(errors, new String[]{"propname", "propvalue", "projectId"});
    validationUtil.checkNotEmpty(errors, "facilityId", "facility");
    if (!errors.hasFieldErrors()) {
      validationUtil.validateProjectId(tmp.getProjectId(), errors);
      if (null == facilityService.findOne(tmp.getFacilityId(), null)){
        errors.rejectValue("facility", "Specified facility does not exist");
      }
    }
  }

}
