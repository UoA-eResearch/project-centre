package nz.ac.auckland.eresearch.projectcentre.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RejectEmptyValidator implements Validator {

  @Autowired
  private ValidationUtil validationUtil;
  private Class<?> clazz;
  private String[] fields;

  public RejectEmptyValidator(Class<?> clazz, String... fields) {
    this.clazz = clazz;
    this.fields = fields;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return this.clazz.equals(clazz);
  }

  @Override
  public void validate(Object domainObject, Errors errors) {
    if (fields != null) {
      validationUtil.checkNotEmpty(errors, fields);
    }
  }

}
