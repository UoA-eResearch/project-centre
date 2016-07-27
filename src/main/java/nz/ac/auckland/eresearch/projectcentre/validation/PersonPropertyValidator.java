package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonProperty;

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
  public void validate(Object in, Errors errors) {
    PersonProperty tmp = (PersonProperty) in;
    validationUtil.checkNotEmpty(errors, new String[]{"propname", "propvalue", "personId"});
    if (!errors.hasFieldErrors()) {
      validationUtil.validatePersonId(tmp.getPersonId(), errors);
    }
  }

}
