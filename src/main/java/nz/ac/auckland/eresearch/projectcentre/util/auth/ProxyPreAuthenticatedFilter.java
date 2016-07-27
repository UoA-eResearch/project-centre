package nz.ac.auckland.eresearch.projectcentre.util.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ProxyPreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {

  private Logger log = LoggerFactory.getLogger(getClass());
  // TODO: Fix hard-coding
  private String proxyIp = "130.216.161.204";

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    String clientAddress = request.getRemoteAddr();
    log.debug("proxy auth filter: remote machine: " + clientAddress);
    if (clientAddress.equals(this.proxyIp)) {
      String userId = request.getHeader("X-REMOTE-USER");
      log.debug("proxy auth filter: user " + userId);
      return userId;
    }
    return null;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }

}
