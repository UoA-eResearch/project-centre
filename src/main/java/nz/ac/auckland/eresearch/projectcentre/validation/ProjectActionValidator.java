package nz.ac.auckland.eresearch.projectcentre.validation;

import java.time.LocalDate;

import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectActionValidator implements Validator {

  @Autowired
  ProjectActionService projectActionService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectAction.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object in, Errors errors) {
    ProjectAction tmp = (ProjectAction) in;
    validationUtil.checkNotEmpty(errors, new String[]{"notes", "personId"});
    validationUtil.checkNotEmpty(errors, "actionTypeId", "actionType");
    if (!errors.hasFieldErrors()) {
      validationUtil.validatePersonId(tmp.getPersonId(), errors);
      validationUtil.validateProjectId(tmp.getProjectId(), errors);
    }
    if (tmp.getDate() == null) {
      tmp.setDate(LocalDate.now());      
    }
  }

}
