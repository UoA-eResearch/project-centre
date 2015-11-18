package nz.ac.auckland.eresearch.projectcentre.util.auth;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: is this annotation required?
@Repository
public class UserDao {

  public static final String PROJECT_DB_SERVICE_NAME = "cer_project_db";

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private IdentityRepository identityrepo;
  @Autowired
  private PersonRepository personrepo;
  @Autowired
  private AuthzRoleRepository authzrolerepo;

  // FIXME: Set credentialsNonExpired to true if user logs in via Shibboleth!!!!
  public UserInfo getUserInfo(String username) throws UsernameNotFoundException {

    UserInfo userInfo = null;

    try {
      List<Identity> ids = identityrepo.findByUsernameAndService(username, PROJECT_DB_SERVICE_NAME);

      if (ids.size() == 0) {
        throw new UsernameNotFoundException("Could not find user: " + username);
      } else if (ids.size() > 1) {
        throw new RuntimeException("More than one users with username: " + username + ". Please contact an administrator.");
      }

      int idid = ids.get(0).getPersonId();

      Person p = personrepo.findOne(idid);

      Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
      List<AuthzRole> aRoles = authzrolerepo.findByPersonId(idid);
      for (AuthzRole a : aRoles) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(a.getRoleName());
        roles.add(auth);
      }

      //TODO: do we really need expiring tokens?
      boolean credentialsNonExpired = false;

      LocalDateTime expires = ids.get(0).getExpires();
      if (expires != null && expires.isAfter(LocalDateTime.now())) {
        credentialsNonExpired = true;
      }

      userInfo = new UserInfo(username, ids.get(0).getToken(), true, true, credentialsNonExpired, true, roles);
      userInfo.setFullName(p.getFullName());
      userInfo.setEmail(p.getEmail());
      userInfo.setId(p.getId());

    } catch (Exception e) {
      String message = "Unable to load user details for upi " + username;
      log.error(message, e);
      throw new UsernameNotFoundException(message);
    }

    return userInfo;
  }


} 
