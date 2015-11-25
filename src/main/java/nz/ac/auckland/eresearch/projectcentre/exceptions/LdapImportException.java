package nz.ac.auckland.eresearch.projectcentre.exceptions;

/**
 * Created by markus on 24/11/15.
 */
public class LdapImportException extends LdapQueryException {


  public LdapImportException(String message) {
    super(message);
  }

  public LdapImportException(String message, Exception cause) {
    super(message, cause);
  }
}
