package nz.ac.auckland.eresearch.projectcentre.util.clRunners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
import nz.ac.auckland.eresearch.projectcentre.repositories.AuthzRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by markus on 12/11/15.
 */
@Component
@Profile({"dev", "test"})
public class SeedDataImporter implements CommandLineRunner, Ordered{

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
  private AuthzRoleRepository authRoleRepo;


  public void addSeedData() throws IOException {

      List<Institution> institutions = om.readValue(new File("/home/markus/src/work/projects-centre/seed-data/minimal/01-init-institution.json"),
              new TypeReference<List<Institution>>() {
              });
      institutions.forEach(
              (Institution i) -> {
                System.out.println("Saving: " + i.getCode());
                instRepo.save(i);
              }
      );

      JsonNode divisions = om.readValue(new File("/home/markus/src/work/projects-centre/seed-data/minimal/02-init-division.json"), JsonNode.class);

      StreamSupport.stream(divisions.spliterator(), false)
              .map((JsonNode dn) -> {
                try {
                  return (Division) (om.treeToValue(dn, Division.class));
                } catch (JsonProcessingException e) {
                  e.printStackTrace();
                  throw new RuntimeException("Could not convert node: " + dn);
                }
              })
              .forEach(d -> divRepo.save(d));


  }

  @Override
  public int getOrder() {
    return 2;
  }

  @Override
  public void run(String... args) throws Exception {
    addSeedData();
  }
}



