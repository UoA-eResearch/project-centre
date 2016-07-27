package nz.ac.auckland.eresearch.projectcentre.util.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class ProxyUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  @Autowired
  private UserDao userDao;

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken user) throws UsernameNotFoundException {
    return this.userDao.getUserInfoForProxyLogin((String) user.getPrincipal());
  }

}