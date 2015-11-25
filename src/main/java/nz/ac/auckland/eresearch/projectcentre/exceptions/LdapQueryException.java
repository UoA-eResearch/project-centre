package nz.ac.auckland.eresearch.projectcentre.exceptions;

/**
 * Created by markus on 23/11/15.
 */
public class LdapQueryException extends Exception {
  public LdapQueryException(String s) {
    super(s);
  }

  public LdapQueryException(String s, Exception e) {
    super(s,e);
  }
}
