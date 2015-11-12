package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProject;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonProjectValidator implements Validator {

  @Autowired
  PersonRoleService personRoleService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return PersonProject.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object personProject, Errors errors) {
    PersonProject pp = (PersonProject) personProject;
    String[] notEmpty = {"personId", "projectId", "personRoleId"};
    new RejectEmptyValidator(PersonProject.class, notEmpty).validate(personProject, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(pp.getProjectId(), errors);
      this.validationUtil.validatePersonId(pp.getPersonId(), errors);
      this.validatePersonRoleId(pp.getPersonRoleId(), errors);
    }
  }

  public void validatePersonRoleId(Integer personRoleId, Errors errors) {
    PersonRole pr = personRoleService.findOne(personRoleId);
    if (pr == null) {
      errors.rejectValue("personRoleId", "person.role.id.invalid");
    }
  }

}
