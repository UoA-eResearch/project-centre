package nz.ac.auckland.eresearch.projectcentre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories(basePackages = "nz.ac.auckland.eresearch.projectcentre.repositories")
public class Application extends SpringBootServletInitializer {


  public static void main(String[] args) throws Exception {

    SpringApplication.run(Application.class, args);

    // CommandlineRunners might be run here. Check out config as well as the util.clRunners package for annotations.
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
