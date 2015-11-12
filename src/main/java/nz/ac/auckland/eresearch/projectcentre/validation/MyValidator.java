package nz.ac.auckland.eresearch.projectcentre.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MyValidator {

  private Validator[] validators;

  public MyValidator(Validator... validators) {
    this.validators = validators;
  }

  public void validate(Object o) throws MethodArgumentNotValidException {
    DataBinder dataBinder = new DataBinder(o);
    dataBinder.addValidators(this.validators);
    dataBinder.validate();
    BindingResult bindingResult = dataBinder.getBindingResult();
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
  }
}
