package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthzRoleValidator implements Validator {

  @Autowired
  PersonPropertyService authzRoleService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return PersonProperty.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object authzRole, Errors errors) {
    AuthzRole ar = (AuthzRole) authzRole;
    String[] notEmpty = {"personId", "roleName"};
    new RejectEmptyValidator(AuthzRole.class, notEmpty).validate(authzRole, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validatePersonId(ar.getPersonId(), errors);
    }
  }

}
