package nz.ac.auckland.eresearch.projectcentre.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    

    @Bean
    public Docket api() { 
        ResponseMessage r200 = new ResponseMessageBuilder().code(200).message("OK").build();
        ResponseMessage r201 = new ResponseMessageBuilder().code(201).message("Created").build();
        ResponseMessage r400 = new ResponseMessageBuilder().code(400).message("Bad Request").build();
        ResponseMessage r401 = new ResponseMessageBuilder().code(401).message("No or invalid authentication").build();
        ResponseMessage r403 = new ResponseMessageBuilder().code(403).message("Not permitted to access for users role").build();
        ResponseMessage r404 = new ResponseMessageBuilder().code(404).message("Not Found").build();
        ResponseMessage r500 = new ResponseMessageBuilder().code(500).message("Server Error").build();
        
        return new Docket(DocumentationType.SWAGGER_2)
          .consumes(Sets.newHashSet("application/json"))
          .produces(Sets.newHashSet("application/json"))
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude Spring error controllers
          .paths(Predicates.not(PathSelectors.regex("/authenticate/api"))) // Exclude authentication controller
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false)
          .globalResponseMessage(RequestMethod.GET, Arrays.asList(r200, r401, r403, r404, r500))
          .globalResponseMessage(RequestMethod.PUT, Arrays.asList(r200, r400, r401, r403, r404, r500))
          .globalResponseMessage(RequestMethod.PATCH, Arrays.asList(r200, r400, r401, r403, r404, r500))
          .globalResponseMessage(RequestMethod.POST, Arrays.asList(r201, r400, r401, r403, r404, r500))
          .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(r200, r401, r403, r404, r500))
          .directModelSubstitute(LocalDate.class, String.class);
    }
    
    private ApiInfo apiInfo() {
      StringBuffer description = new StringBuffer();
      description.append("Create, view and manage e-Research ")
          .append("projects and services used by projects.");
      ApiInfo apiInfo = new ApiInfo(
        "Research Project Management API",
        description.toString(),
        "0.1",
        "",
        new Contact("Centre for eResearch", null, "m.feller@auckland.ac.nz"),
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0.html",
        new LinkedList<VendorExtension>());
      return apiInfo;
  }
}
