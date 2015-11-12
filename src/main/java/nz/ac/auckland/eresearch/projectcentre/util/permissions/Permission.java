package nz.ac.auckland.eresearch.projectcentre.util.permissions;

import org.springframework.security.core.Authentication;

public interface Permission {

  boolean isAllowed(Authentication authentication, Object targetDomainObject);

}