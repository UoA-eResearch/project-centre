package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectMemberValidator implements Validator {

  @Autowired
  protected PersonRoleService roleService;
  @Autowired
  protected ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectMember.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ProjectMember tmp = (ProjectMember) o;
    validationUtil.checkNotEmpty(errors, new String[]{"personId", "projectId"});
    validationUtil.checkNotEmpty(errors, "personRoleId", "role");
    if (!errors.hasFieldErrors()) {
      validationUtil.validatePersonId(tmp.getPersonId(), errors);
      validationUtil.validateProjectId(tmp.getProjectId(), errors);
    }
  }

}
