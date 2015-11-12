package nz.ac.auckland.eresearch.projectcentre.util.permissions;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.auth.UserInfo;

import org.springframework.security.core.Authentication;

public class EditPersonPermission implements Permission {

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    boolean isAllowed = false;
    try {
      Integer entityId = ((HasId) targetDomainObject).getId();
      Integer personId = ((UserInfo) authentication.getPrincipal()).getId();
      if (entityId == personId) {
        isAllowed = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return isAllowed;
  }

}
