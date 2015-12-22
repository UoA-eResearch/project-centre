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

  // FIXME: Set credentialsNonExpired to true if user logs in via Shibboleth!!!!
  public UserInfo getUserInfo(String username) throws UsernameNotFoundException {

    log.debug("Getting user info for user " + username);
    UserInfo userInfo = null;
    
    try {
      //TODO: do we really need expiring tokens?
      boolean credentialsNonExpired = true;
      String password = "n/a";
      Integer personId = null;
      List<Identity> ids = identityRepo.findByUsernameAndService(username, PROJECT_DB_SERVICE_NAME);

      if (ids.size() == 1) {
          Identity identity = ids.get(0);
          password = identity.getToken();
          personId = identity.getPersonId();
          LocalDateTime expires = identity.getExpires();
          if (expires != null && expires.isBefore(LocalDateTime.now())) {
            credentialsNonExpired = false;
          }    	  
      } else if (ids.size() > 1) {
        throw new RuntimeException("More than one user with username: " + username);
      } else if (ids.size() == 0) {
    	  List<PersonProperty> props = personPropertyRepo.findByPropnameAndPropvalue("eppn", username);
    	  if (props.size() == 0) {
              throw new UsernameNotFoundException("Could not find user: " + username);    		  
    	  } else if (props.size() > 1) {
    	        throw new RuntimeException("More than one user with username: " + username);    		  
    	  }
    	  personId = props.get(0).getPersonId();
      } else {
    	  throw new RuntimeException("Unexpected error. size = " + ids.size());
      }

      Person p = personRepo.findOne(personId);
      Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
      List<AuthzRole> aRoles = authzroleRepo.findByPersonId(personId);
      for (AuthzRole a : aRoles) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(a.getRoleName());
        roles.add(auth);
      }

      userInfo = new UserInfo(username, password, true, true, credentialsNonExpired, true, roles);
      userInfo.setFullName(p.getFullName());
      userInfo.setEmail(p.getEmail());
      userInfo.setId(p.getId());
    } catch (Exception e) {
      String message = "Unable to load user details for upi " + username;
      log.error(message, e);
      throw new UsernameNotFoundException(message);
    }

    log.debug("Returning user info for user " + username);
    return userInfo;
  }


} 
