package nz.ac.auckland.eresearch.projectcentre.util.clRunners;

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
import nz.ac.auckland.eresearch.projectcentre.listeners.DivisionShadowTableHelper;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
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

import java.util.List;

/**
 * Created by markus on 12/11/15.
 */
@Component
@Profile({"minimal"})
public class SeedDataImporter implements CommandLineRunner, Ordered {

  private Logger log = LoggerFactory.getLogger(SeedDataImporter.class);

  @Value("${admin.username}")
  private String adminUsername;
  @Value("${admin.password}")
  private String adminPassword;
  @Value("${seed.location}")
  private String defaultSeed;
  @Value("${seed.level:10000}")
  private Integer seedLevel;

  @Autowired
  private JsonHelpers jsonHelper;
  @Autowired
  private DivisionRepository divrepo;
  @Autowired
  private DivisionShadowTableHelper shadow;

  @Autowired
  private ObjectMapper om;

  public <T> void addData(Class<T> type, String folder) throws Exception {

    List<T> objects = jsonHelper.deserializeJsonFromFile(type, folder, type.getSimpleName().toLowerCase()+".json", true);

  }


  public void addSeedData(String seedLocation, int seedLevel) throws Exception {

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
      addData(Division.class, seedLocation);
    }

    if (seedLevel > 2) {
      addData(Person.class, seedLocation);
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

    boolean already_contains_data = divrepo.findAll().iterator().hasNext();
    if (already_contains_data) {
      log.debug("There already seems to be data in the db, skipping seeding of data.");
      return;
    }

    int sl = this.seedLevel;
    String seedLocation = this.defaultSeed;
    if (args.length > 0) {
      for (String arg : args) {
        if (arg.startsWith("seed-location=")) {
          seedLocation = arg.substring(14);
        } else if (arg.startsWith("seed-level=")) {
          String value = arg.substring(11);
          sl = Integer.parseInt(value);
        }
      }

    }
    addSeedData(seedLocation, sl);
  }
}



