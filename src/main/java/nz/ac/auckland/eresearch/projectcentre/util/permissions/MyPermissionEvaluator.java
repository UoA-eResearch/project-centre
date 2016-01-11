package nz.ac.auckland.eresearch.projectcentre.util.permissions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MyPermissionEvaluator implements PermissionEvaluator {

  private Logger log = LoggerFactory.getLogger(getClass());
  private Map<String, Permission> permissionNameToPermissionMap = new HashMap<String, Permission>();

  public MyPermissionEvaluator(Map<String, Permission> permissionNameToPermissionMap) {
    this.permissionNameToPermissionMap = permissionNameToPermissionMap;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    boolean hasPermission = false;
    if (canHandle(authentication, targetDomainObject, permission)) {
      hasPermission = checkPermission(authentication, targetDomainObject, (String) permission);
    }
    return hasPermission;
  }

  private boolean canHandle(Authentication authentication, Object targetDomainObject, Object permission) {
    return targetDomainObject != null && authentication != null && permission instanceof String;
  }

  private boolean checkPermission(Authentication authentication, Object targetDomainObject, String permissionKey) {
    verifyPermissionIsDefined(permissionKey);
    Permission permission = permissionNameToPermissionMap.get(permissionKey);
    return permission.isAllowed(authentication, targetDomainObject);
  }

  private void verifyPermissionIsDefined(String permissionKey) {
    if (!permissionNameToPermissionMap.containsKey(permissionKey)) {
      throw new RuntimeException("No permission with key " + permissionKey + " is defined in " + this.getClass().toString());
    }
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    throw new RuntimeException("Id and Class permissions are not supported by " + this.getClass().toString());
  }
}
