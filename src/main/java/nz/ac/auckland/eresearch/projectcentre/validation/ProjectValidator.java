package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProjectValidator implements Validator {

  @Autowired
  ProjectService projectService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return Project.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object project, Errors errors) {
    Project p = (Project) project;
    String[] notEmpty = {"institutionId", "description", "title", "code"};
    new RejectEmptyValidator(Project.class, notEmpty).validate(project, errors);
    if (!errors.hasErrors()) {
      //TODO fix that for lists
      //this.validateAffiliation(p.getDivisionId(), errors);
      this.validateDescription(p.getDescription(), errors);
      this.validateCode(p, errors);
    }
  }

  private void validateAffiliation(Integer divId, Errors errors) {

    this.validationUtil.validateDivisionId(divId, errors);

  }

  private void validateDescription(String description, Errors errors) {
    int length = description.trim().length();
    if (length < 500 || length > 2500) {
      errors.rejectValue("description", "project.description.invalid");
    }
  }

  private void validateCode(Project project, Errors errors) {
    List<Project> tmp = null;
    if (project.getId() == null) { // new project
      tmp = this.projectService.findByCode(project.getCode());
    } else { // existing project
      tmp = this.projectService.findByCodeAndIdNot(project.getCode(), project.getId());
    }
    if (tmp != null && tmp.size() > 0) {
      errors.rejectValue("code", "project.code.in.use");
    }
  }

}
