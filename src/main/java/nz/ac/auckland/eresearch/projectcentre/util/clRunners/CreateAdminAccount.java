package nz.ac.auckland.eresearch.projectcentre.util.clRunners;

import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by markus on 12/11/15.
 */
@Component
@Profile({"dev", "test"})
public class CreateAdminAccount implements CommandLineRunner, Ordered {

  @Value("${admin.username}")
  private String adminUsername;
  @Value("${admin.password}")
  private String adminPassword;

  @Autowired
  private ObjectMapper om;
  @Autowired
  private InstitutionRepository instRepo;
  @Autowired
  private DivisionRepository divRepo;
  @Autowired
  private PersonRepository personRepo;
  @Autowired
  private IdentityRepository idRepo;
  @Autowired
  private AuthzRoleRepository authzroleRepo;
  @Autowired
  private PasswordEncoder encoder;


  private void createAdminAccount() {
          String email = "m.binsteiner@auckland.ac.nz";
      Person p = null;
      List<Person> persons = personRepo.findByEmail(email);
      if (persons == null || persons.size() < 1) {
        p = new Person();
        p.setFullName("Markus Binsteiner");
        p.setEmail(email);
        p = personRepo.save(p);

        AuthzRole authzRole = new AuthzRole();
        authzRole.setPersonId(p.getId());
        authzRole.setRoleName("ROLE_ADMIN");
        authzroleRepo.save(authzRole);


      } else {
        p = persons.get(0);
      }

      Identity id = new Identity(p.getId(), adminUsername, UserDao.PROJECT_DB_SERVICE_NAME);
      id.setToken(encoder.encode(adminPassword));
      id.setExpires(LocalDateTime.now().plusYears(10));
      idRepo.save(id);
      personRepo.findAll().forEach(
              (per) -> System.out.println(per.getFullName())
      );
  }

  @Override
  public void run(String... args) throws Exception {
    createAdminAccount();
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
