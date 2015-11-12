package nz.ac.auckland.eresearch.projectcentre.util.auth;

public class Authz {

  public final static String AUTHENTICATED = "isFullyAuthenticated()";
  public final static String ADMIN = AUTHENTICATED + " and hasRole('ROLE_ADMIN')";
  public final static String EDIT_PROJECT = AUTHENTICATED + " and (hasRole('ROLE_ADMIN') or hasPermission(#entity, 'editProject'))";
  public final static String EDIT_PERSON = AUTHENTICATED + " and (hasRole('ROLE_ADMIN') or hasPermission(#entity, 'editPerson'))";
}
