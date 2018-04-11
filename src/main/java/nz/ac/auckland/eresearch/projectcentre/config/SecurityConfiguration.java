package nz.ac.auckland.eresearch.projectcentre.config;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import nz.ac.auckland.eresearch.projectcentre.util.auth.ProxyPreAuthenticatedFilter;
import nz.ac.auckland.eresearch.projectcentre.util.auth.ProxyUserDetailsService;
import nz.ac.auckland.eresearch.projectcentre.util.auth.ShibbolethPreAuthenticatedFilter;
import nz.ac.auckland.eresearch.projectcentre.util.auth.ShibbolethUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
// NOTE: @EnableWebSecurity commented out because of https://github.com/spring-projects/spring-boot/issues/1548
// @EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  ShibbolethUserDetailsService shibUserDetailsService;
  @Autowired
  ProxyUserDetailsService proxyUserDetailsService;
  @Autowired
  UserDetailsService userDetailsService;

  private static final String[] AUTH_WHITELIST = {
    "/authenticate/api",
    // -- swagger ui
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/v2/api-docs",
    "/webjars/**"
  };

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // configure proxy authentication filter
    ProxyPreAuthenticatedFilter proxyFilter = new ProxyPreAuthenticatedFilter();
    PreAuthenticatedAuthenticationProvider proxyAuthProvider = new PreAuthenticatedAuthenticationProvider();
    proxyAuthProvider.setPreAuthenticatedUserDetailsService(proxyUserDetailsService);
    List<AuthenticationProvider> proxyAuthProviders = new LinkedList<AuthenticationProvider>();
    proxyAuthProviders.add(proxyAuthProvider);
    proxyFilter.setAuthenticationManager(new ProviderManager(proxyAuthProviders));
    
    // configure Shibboleth authentication filter
    ShibbolethPreAuthenticatedFilter shibbolethFilter = new ShibbolethPreAuthenticatedFilter();
    PreAuthenticatedAuthenticationProvider shibbolethAuthProvider = new PreAuthenticatedAuthenticationProvider();
    shibbolethAuthProvider.setPreAuthenticatedUserDetailsService(shibUserDetailsService);    
    List<AuthenticationProvider> shibAuthProviders = new LinkedList<AuthenticationProvider>();
    shibAuthProviders.add(shibbolethAuthProvider);
    shibbolethFilter.setAuthenticationManager(new ProviderManager(shibAuthProviders));
    
    http.addFilter(shibbolethFilter);
    http.addFilter(proxyFilter);
    
    http.httpBasic().and().authorizeRequests()
            .antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().fullyAuthenticated()
            .and().csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @PostConstruct
  public void initTokenDatabase() {
//        String sqlDelete = "DELETE FROM apitoken";
//        String sqlInsert = "INSERT INTO apitoken (personId, token, validUntil) VALUES (?, ?, ?)";
//        Timestamp ts = new Timestamp(System.currentTimeMillis() + (86400 * 1000));
//        jdbcTemplate.update(sqlDelete);
//        jdbcTemplate.update(sqlInsert, new Object[] {6, passwordEncoder().encode("test1"), ts});
  }

}

