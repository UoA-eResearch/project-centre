package nz.ac.auckland.eresearch.projectcentre.util.auth;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ShibbolethAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    // TODO: make this nicer...
    String eppn = (String) request.getAttribute("eppn");
    //if (eppn != null) {
    //  eppn = eppn.split("@")[0];
    //}
    return eppn;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }

}
