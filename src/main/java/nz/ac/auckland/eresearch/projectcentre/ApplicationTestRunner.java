package nz.ac.auckland.eresearch.projectcentre;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.uoa.PersonCollector;
import nz.ac.auckland.eresearch.projectcentre.uoa.UoALdap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import javax.sql.DataSource;

// TODO: On migrate: handle the few people who have 2 person entries
//       because they have had a researcher and an adviser profile
@EnableCaching
@SpringBootApplication
@EnableJpaRepositories(basePackages = "nz.ac.auckland.eresearch.projectcentre.repositories")
public class ApplicationTestRunner extends SpringBootServletInitializer {


  public static void main(String[] args) throws Exception {

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", "abcdefg"));


    SpringApplication app = new SpringApplication(ApplicationTestRunner.class);
    ConfigurableApplicationContext ctx = app.run(args);


    // code to test
    try {

      Map<String, DivisionService> services = ctx.getBeansOfType(DivisionService.class);
      DivisionService divServ = services.values().iterator().next();
      Map<String, DivisionalRoleService> servicesRole = ctx.getBeansOfType(DivisionalRoleService.class);
      DivisionalRoleService divRoleServ = servicesRole.values().iterator().next();

      Map<String, UoALdap> servicesLdap = ctx.getBeansOfType(UoALdap.class);
      UoALdap ldap = servicesLdap.values().iterator().next();
      PersonCollector col = new PersonCollector(divServ, divRoleServ);


      ldap.searchUpi("mbin029", col);



    } finally {


      SpringApplication.exit(ctx);
      System.out.println("FINISHED");
    }


  }

  @Bean
  public CacheManager cacheManager() {
    return new EhCacheCacheManager(myEhCacheCacheManager().getObject());
  }

  @Bean
  public EhCacheManagerFactoryBean myEhCacheCacheManager() {
    EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
    cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
    cmfb.setShared(true);
    return cmfb;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
            new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setCacheSeconds(60);
    return messageSource;
  }


}
