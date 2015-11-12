package nz.ac.auckland.eresearch.projectcentre.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(IdException.class)
  public ResponseEntity<Object> handleIdException(IdException ex, WebRequest request) {
    Map<String, Object> m = this.prepareMessageMap(ex.getMessage(), request);
    return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex, HttpHeaders headers,
          HttpStatus status, WebRequest request) {

    Map<String, Object> m = this.prepareMessageMap(
            this.createErrorMessageFromBindingResult(ex.getBindingResult()), request);
    return new ResponseEntity(m, headers, status);
  }

  private String createErrorMessageFromBindingResult(BindingResult bindingResult) {
    StringBuilder messageBuilder = new StringBuilder();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    if (fieldErrors != null) {
      for (FieldError fieldError : fieldErrors) {
        String tmp = this.messageSource.getMessage(fieldError.getCode(),
                fieldError.getArguments(), Locale.US);
        messageBuilder.append(tmp).append(". ");
      }
    }
    return messageBuilder.toString().trim();
  }

  private Map<String, Object> prepareMessageMap(String message, WebRequest request) {
    Map<String, Object> m = new HashMap<String, Object>();
    m.put("message", message);
    m.put("timestamp", System.currentTimeMillis());
    m.put("status", HttpStatus.BAD_REQUEST.value());
    m.put("error", "Bad Request");
    m.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
    return m;
  }
}
