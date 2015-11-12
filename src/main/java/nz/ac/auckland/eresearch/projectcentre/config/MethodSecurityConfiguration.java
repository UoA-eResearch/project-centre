package nz.ac.auckland.eresearch.projectcentre.config;

import nz.ac.auckland.eresearch.projectcentre.util.permissions.EditPersonPermission;
import nz.ac.auckland.eresearch.projectcentre.util.permissions.EditProjectPermission;
import nz.ac.auckland.eresearch.projectcentre.util.permissions.MyPermissionEvaluator;
import nz.ac.auckland.eresearch.projectcentre.util.permissions.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.parameters.DefaultSecurityParameterNameDiscoverer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  PermissionEvaluator permissionEvaluator() {
    Map<String, Permission> m = new HashMap<String, Permission>();
    EditProjectPermission epro = new EditProjectPermission();
    EditPersonPermission eper = new EditPersonPermission();
    epro.setJdbcTemplate(this.jdbcTemplate);
    m.put("editProject", epro);
    m.put("editPerson", eper);
    return new MyPermissionEvaluator(m);
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setPermissionEvaluator(permissionEvaluator());
    handler.setParameterNameDiscoverer(new DefaultSecurityParameterNameDiscoverer());
    return handler;
  }

}