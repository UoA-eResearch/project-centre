package nz.ac.auckland.eresearch.projectcentre.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerExceptionHandler {

  /*
   *  Create BadRequest error when IllegalArgumentExceptions are thrown during validation
   */
  @ExceptionHandler
  public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.BAD_REQUEST.value());
  }

}
