package nz.ac.auckland.eresearch.projectcentre.util.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ShibbolethAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private Logger log = LoggerFactory.getLogger(getClass());
  
  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    String eppn = (String) request.getAttribute("eppn");
    log.debug("Request with eppn: " + eppn);
    return eppn;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }

}
