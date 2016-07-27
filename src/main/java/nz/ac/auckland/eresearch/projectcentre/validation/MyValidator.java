package nz.ac.auckland.eresearch.projectcentre.validation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MyValidator {

  private Logger log = LoggerFactory.getLogger(getClass());

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
      StringBuffer sb = new StringBuffer();
      List<FieldError> l = bindingResult.getFieldErrors();
      for (FieldError fe: l) {
        sb.append(fe.getCode()).append(". ");
      }
      log.warn(sb.toString().trim());
      throw new IllegalArgumentException(sb.toString().trim());
    }
  }
}
