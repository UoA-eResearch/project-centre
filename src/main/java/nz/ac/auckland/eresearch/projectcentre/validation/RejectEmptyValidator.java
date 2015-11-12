package nz.ac.auckland.eresearch.projectcentre.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RejectEmptyValidator implements Validator {

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
      for (String field : fields) {
        Object[] args = new Object[]{field};
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "mandatory.field.missing", args);
      }
    }
  }

}
