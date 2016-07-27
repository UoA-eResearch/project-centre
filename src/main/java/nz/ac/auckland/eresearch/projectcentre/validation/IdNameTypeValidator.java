package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.util.IdNameTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.IdNameType;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IdNameTypeValidator<T> implements Validator {

  private ValidationUtil validationUtil;
  private IdNameTypeService<T> service;
  
  public IdNameTypeValidator(ValidationUtil validationUtil,
      IdNameTypeService<T> service) {
    this.validationUtil = validationUtil;
    this.service = service;
  }
  
  @Override
  public boolean supports(Class<?> clazz) {
    return IdNameType.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    IdNameType tmp = (IdNameType) o;
    this.validationUtil.checkNotEmpty(errors, new String[]{"name"});
    if (!errors.hasErrors()) {
      if (null != this.service.findByName(tmp.getName())) {
        errors.rejectValue("name", "Specified name already taken: " + tmp.getName());
      }
    }
  }

}
