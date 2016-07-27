package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectDivisionValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectDivision.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ProjectDivision tmp = (ProjectDivision) o;
    validationUtil.checkNotEmpty(errors, new String[]{"divisionId", "projectId"});
    if (!errors.hasErrors()) {
      validationUtil.validateDivisionId(tmp.getDivisionId(), "divisionId", errors);
      validationUtil.validateProjectId(tmp.getProjectId(), errors);
    }
  }
    
}
