package nz.ac.auckland.eresearch.projectcentre.config;

import nz.ac.auckland.eresearch.projectcentre.util.auth.ShibbolethAuthenticationFilter;
import nz.ac.auckland.eresearch.projectcentre.util.auth.ShibbolethUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

@Configuration
// NOTE: @EnableWebSecurity commented out because of https://github.com/spring-projects/spring-boot/issues/1548
// @EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  ShibbolethUserDetailsService shibUserDetailsService;

  @Autowired
  UserDetailsService userDetailsService;

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

    // configure Shibboleth authentication filter
    AbstractPreAuthenticatedProcessingFilter shibbolethFilter = new ShibbolethAuthenticationFilter();
    PreAuthenticatedAuthenticationProvider shibbolethAuthProvider = new PreAuthenticatedAuthenticationProvider();
    shibbolethAuthProvider.setPreAuthenticatedUserDetailsService(shibUserDetailsService);
    List<AuthenticationProvider> authProviders = new LinkedList<AuthenticationProvider>();
    authProviders.add(shibbolethAuthProvider);
    shibbolethFilter.setAuthenticationManager(new ProviderManager(authProviders));
    http.addFilterBefore(shibbolethFilter, UsernamePasswordAuthenticationFilter.class);

    http.httpBasic().and().authorizeRequests()
            .antMatchers("/authenticate/api").permitAll()
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

