package nz.ac.auckland.eresearch.projectcentre.util.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonPropertyRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

// TODO: is this annotation required?
@Repository
public class UserDao {

  public static final String PROJECT_DB_SERVICE_NAME = "cer_project_db";
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private IdentityRepository identityRepo;
  @Autowired
  private PersonRepository personRepo;
  @Autowired
  private PersonPropertyRepository personPropertyRepo;
  @Autowired
  private AuthzRoleRepository authzroleRepo;

  public UserInfo getUserInfoForApiLogin(String username) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + username + " (API login)");
    try {
      List<Identity> ids = identityRepo.findByUsernameAndService(username, PROJECT_DB_SERVICE_NAME);
  	  this.checkNumberUsers(ids.size(), username);
      //TODO: do we really need expiring tokens?
      Boolean credentialsNonExpired = false;
      Identity identity = ids.get(0);
      LocalDateTime expires = identity.getExpires();
      if (expires != null && expires.isAfter(LocalDateTime.now())) {
        credentialsNonExpired = true;
      }
      log.debug("Returning user info for user " + username);
      return this.createUserInfo(identity.getPersonId(), username, identity.getToken(), credentialsNonExpired);
    } catch (Exception e) {
      String message = "Unable to load user details for name " + username + " (API login)";
      log.error(message, e);
      throw new UsernameNotFoundException(message);
    }
  }

  public UserInfo getUserInfoForSamlLogin(String eppn) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + eppn + " (SAML login)");
    try {
  	  List<PersonProperty> props = personPropertyRepo.findByPropnameAndPropvalue("eppn", eppn);
  	  this.checkNumberUsers(props.size(), eppn);
      log.debug("Returning user info for user " + eppn);
      return this.createUserInfo(props.get(0).getPersonId(), eppn, "n/a", true);  	  
    } catch (Exception e) {
      String message = "Unable to load user details for name " + eppn + " (SAML login)";
      log.error(message, e);
      throw new UsernameNotFoundException(message);
    }
  }

  private void checkNumberUsers(Integer numUsersFound, String username) throws Exception {
	  if (numUsersFound < 0) {
          throw new Exception("Unexpected error. size = " + numUsersFound);
      } else if (numUsersFound == 0) {
    	  throw new UsernameNotFoundException("Could not find user: " + username);
  	  } else if (numUsersFound > 1) {
  	      throw new RuntimeException("More than one user with username: " + username);    		  
  	  }  
  }
  
  private UserInfo createUserInfo(Integer personId, String username, String password, 
		  Boolean credentialsNonExpired) {
	  
      Person p = personRepo.findOne(personId);
      Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
      List<AuthzRole> aRoles = authzroleRepo.findByPersonId(personId);
      for (AuthzRole a : aRoles) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(a.getRoleName());
        roles.add(auth);
      }

      UserInfo userInfo = new UserInfo(username, password, true, true, credentialsNonExpired, true, roles);
      userInfo.setFullName(p.getFullName());
      userInfo.setEmail(p.getEmail());
      userInfo.setId(p.getId());
	  return userInfo;
  }

} 
