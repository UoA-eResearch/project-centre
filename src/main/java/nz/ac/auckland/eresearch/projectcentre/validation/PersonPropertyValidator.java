package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonPropertyValidator implements Validator {

  @Autowired
  PersonPropertyService personPropertyService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return PersonProperty.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object personProperty, Errors errors) {
    PersonProperty pp = (PersonProperty) personProperty;
    String[] notEmpty = {"personId", "propname", "propvalue"};
    new RejectEmptyValidator(PersonProperty.class, notEmpty).validate(personProperty, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validatePersonId(pp.getPersonId(), errors);
    }
  }

}
