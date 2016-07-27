package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServiceValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return Service.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object service, Errors errors) {
    validationUtil.checkNotEmpty(errors, new String[]{"drupalId", "name"});
    // TODO: more validation required?
  }

}
