package nz.ac.auckland.eresearch.projectcentre.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by markus on 16/11/15.
 */
public class JsonEntityInvalidException extends JsonProcessingException {
  public JsonEntityInvalidException(String s) {
    super(s);
  }
}
