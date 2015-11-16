package nz.ac.auckland.eresearch.projectcentre.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by markus on 16/11/15.
 */
public class JsonEntityNotFoundException extends JsonProcessingException {
  public JsonEntityNotFoundException(String msg) {
    super(msg);
  }
}
