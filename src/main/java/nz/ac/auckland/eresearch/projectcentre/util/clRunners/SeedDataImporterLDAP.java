package nz.ac.auckland.eresearch.projectcentre.util.clRunners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.entity.ExternalReference;
import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.exceptions.LdapQueryException;
import nz.ac.auckland.eresearch.projectcentre.listeners.DivisionShadowTableHelper;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.uoa.PersonCollector;
import nz.ac.auckland.eresearch.projectcentre.uoa.UoAGroups;
import nz.ac.auckland.eresearch.projectcentre.uoa.UoALdap;
import nz.ac.auckland.eresearch.projectcentre.util.json.JsonHelpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by markus on 12/11/15.
 */
@Component
@Profile({"ldap"})
public class SeedDataImporterLDAP implements CommandLineRunner, Ordered {

  private Logger log = LoggerFactory.getLogger(SeedDataImporterLDAP.class);

  @Value("${admin.username}")
  private String adminUsername;
  @Value("${admin.password}")
  private String adminPassword;
  @Value("${seed.location}")
  private String defaultSeed;
  @Value("${hierarchyfile.location}")
  private String defaultHierarchyLocation;

  @Autowired
  private JsonHelpers jsonHelper;
  @Autowired
  private DivisionService divService;
  @Autowired
  private DivisionalRoleService divRoleService;
  @Autowired
  private DivisionShadowTableHelper shadow;
  @Autowired
  private UoALdap ldap;


  private boolean overwrite = false;

  @Autowired
  private ObjectMapper om;

  public <T> void addData(Class<T> type, String folder) throws Exception {

    List<T> objects = jsonHelper.deserializeJsonFromFile(type, folder, type.getSimpleName().toLowerCase()+".json", true);

  }

  private void addPersonData(String folder) throws Exception {
    Path start = Paths.get(folder);
    int maxDepth = 1;

    try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
            String.valueOf(path).toLowerCase().contains("init-person.json"))) {

      List<File> files = stream
              .sorted()
              .map(p -> p.toFile())
              .collect(Collectors.toList());

      PersonCollector col = new PersonCollector(divService, divRoleService);

      int success = 0;
      int failed = 0;
      for (File file : files) {
        log.debug("Reading: {}", file);
        JsonNode list = (JsonNode) (om.readValue(file, JsonNode.class));
        for (JsonNode node : list) {
          String upi = (String) (om.treeToValue(node, String.class));
          try {
            ldap.searchUpi(upi, col);
            success++;
          } catch (LdapQueryException lqe) {
            System.out.println(lqe.getMessage());
            failed++;
          }
        }
      }

      for (Person p : col.getResults()) {
        log.debug("Persisting value of Person: {}", p);
        jsonHelper.save(p);
      }
    }
  }


  public void addDivisionData(String hierarchyLocation) throws Exception {

    UoAGroups uoaGroups = new UoAGroups(hierarchyLocation);

    for (String g : uoaGroups.getUoAGroups()) {

      log.debug("Reading: {}", g);
      JsonNode divNode = (JsonNode) (om.readValue(g, JsonNode.class));
      Division div = (Division) (om.treeToValue(divNode, Division.class));
      log.debug("Persisting value of {}: {}", "Division", div);
      jsonHelper.save(div);
    }

  }


  public void addSeedData(String hierarchyLocation, String seedLocation, int seedLevel) throws Exception {

    if (seedLevel > 0) {
      addData(DivisionalRole.class, seedLocation);
      addData(Facility.class, seedLocation);
      addData(Kpi.class, seedLocation);
      addData(KpiCategory.class, seedLocation);
      addData(PersonRole.class, seedLocation);
      addData(PersonStatus.class, seedLocation);
      addData(ProjectActionType.class, seedLocation);
      addData(ProjectStatus.class, seedLocation);
      addData(ProjectType.class, seedLocation);
      addData(ResearchOutputType.class, seedLocation);
    }

    if (seedLevel > 1) {
      addDivisionData(hierarchyLocation);
    }

    if (seedLevel > 2) {
      addPersonData(seedLocation);
    }

    if (seedLevel > 3) {
      addData(PersonProperty.class, seedLocation);
    }

    if (seedLevel > 4) {
      addData(Project.class, seedLocation);
    }

    if (seedLevel > 5) {
      addData(ProjectProperty.class, seedLocation);
      addData(ProjectAction.class, seedLocation);
      //addData(ProjectFacility.class, seedLocation);
      addData(ProjectKpi.class, seedLocation);
      addData(ResearchOutput.class, seedLocation);
      addData(ExternalReference.class, seedLocation);
    }


  }


  @Override
  public int getOrder() {
    return 2;
  }

  @Override
  public void run(String... args) throws Exception {

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(adminUsername, adminPassword));

    // TODO: using divRepo for now, otherwise I'd have to figure out how to auth from CommandlineRunner
    boolean already_contains_data = divService.findAll().iterator().hasNext();
    if (already_contains_data && ! overwrite) {
      log.debug("There already seems to be data in the db, skipping seeding of data.");
      return;
    }

    int seedLevel = Integer.MAX_VALUE;
    String seedLocation = this.defaultSeed;
    String hierarchyLocation = this.defaultHierarchyLocation;
    if (args.length > 0) {
      for (String arg : args) {
        if (arg.startsWith("hierarchyfile-location=")) {
          hierarchyLocation = arg.substring(22);
        } else if (arg.startsWith("seed-level=")) {
          String value = arg.substring(11);
          seedLevel = Integer.parseInt(value);
        } else if (arg.startsWith("seed-location=")) {
          seedLocation = arg.substring(14);
        }
      }

    }
    addSeedData(hierarchyLocation, seedLocation, seedLevel);
  }
}



