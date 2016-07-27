package nz.ac.auckland.eresearch.projectcentre.util.auth;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.types.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// TODO: is this annotation required?
@Repository
public class UserDao {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private IdentityRepository identityRepo;
  @Autowired
  private PersonRepository personRepo;
  @Autowired
  private AuthzRoleRepository authzroleRepo;

  public UserInfo getUserInfoForApiLogin(String username) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + username + " (API login)");
    return this.getUserInfoForUser(username, false);
  }

  public UserInfo getUserInfoForProxyLogin(String upi) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + upi + " (proxy login)");
    return this.getUserInfoForUser(upi, true);
  }

  public UserInfo getUserInfoForSamlLogin(String eppn) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + eppn + " (SAML login)");
    return this.getUserInfoForUser(eppn, true);
  }

  private UserInfo getUserInfoForUser(String username, Boolean createDummy)
      throws UsernameNotFoundException {

    try {
      List<Identity> ids = identityRepo.findByUsername(username);
      if (ids == null || ids.size() == 0) {
        log.debug("username not found. creating dummy user for username " + username);
        return this.createDummyUserInfo(null);
      } else if (ids.size() == 1 && ids.get(0).getPersonId() == null) {
        log.debug("no personId configured for username " + username + ". creating dummy user");
        return this.createDummyUserInfo(ids.get(0).getToken());
      } else if (ids.size() > 1) {
        throw new RuntimeException("more than one user with username: " + username);
      }
      // TODO: do we really need expiring tokens?
      Boolean credentialsNonExpired = false;
      Identity identity = ids.get(0);
      LocalDateTime expires = identity.getExpires();
      if (expires != null && expires.isAfter(LocalDateTime.now())) {
        credentialsNonExpired = true;
      }
      log.debug("returning user info for user " + username);
      return this.createUserInfo(identity.getId(), identity.getPersonId(), username,
          identity.getToken(), credentialsNonExpired);
    } catch (Exception e) {
      String message = "exception for username " + username;
      log.error(message, e);
      throw new UsernameNotFoundException(message);
    }
  }

  private UserInfo createUserInfo(Integer identityId, Integer personId, String username,
      String password, Boolean credentialsNonExpired) {

    Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    List<AuthzRole> aRoles = authzroleRepo.findByIdentityId(identityId);
    for (AuthzRole a : aRoles) {
      SimpleGrantedAuthority auth = new SimpleGrantedAuthority(a.getRole());
      log.debug("Adding role " + a.getRole() + " for username " + username);
      roles.add(auth);
    }
    Person p = personRepo.findOne(personId);
    UserInfo userInfo =
        new UserInfo(username, password, true, true, credentialsNonExpired, true, roles);
    userInfo.setFullName(p.getFullName());
    userInfo.setEmail(p.getEmail());
    userInfo.setId(p.getId());
    return userInfo;
  }

  private UserInfo createDummyUserInfo(String password) {
    if (password == null || password.trim().isEmpty()) {
      password = new BCryptPasswordEncoder(8).encode("__dummy__");
    }
    Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    UserInfo userInfo = new UserInfo("dummy", password, true, true, true, true, roles);
    userInfo.setFullName("Dummy");
    userInfo.setEmail("dummy@cer.auckland.ac.nz");
    userInfo.setId(-1);
    return userInfo;
  }

}
