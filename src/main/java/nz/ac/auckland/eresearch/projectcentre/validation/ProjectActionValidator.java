package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

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
  public void validate(Object personProject, Errors errors) {
    ProjectAction pa = (ProjectAction) personProject;
    String[] notEmpty = {"actionTypeId", "notes", "personId", "projectId"};
    new RejectEmptyValidator(ProjectAction.class, notEmpty).validate(personProject, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(pa.getProjectId(), errors);
      this.validationUtil.validatePersonId(pa.getPersonId(), errors);
      this.validateActionTypeId(pa.getActionTypeId(), errors);
    }
    pa.setDate(LocalDate.now());
  }

  public void validateActionTypeId(Integer actionTypeId, Errors errors) {
    ProjectAction pa = projectActionService.findOne(actionTypeId);
    if (pa == null) {
      errors.rejectValue("actionTypeId", "action.type.id.invalid");
    }
  }

}
