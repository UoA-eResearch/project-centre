package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DivisionValidator implements Validator {
  
  @Autowired
  private ValidationUtil validationUtil;
  
  @Override
  public boolean supports(Class<?> clazz) {
    return Division.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object division, Errors errors) {
    Division d = (Division) division;
    this.validationUtil.checkNotEmpty(errors, new String[]{"name", "code"});
    // TODO: validate code doesn't already exist

  }

}
