package nz.ac.auckland.eresearch.projectcentre.util;

public class IdException extends Exception {

  private String message;

  public IdException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
