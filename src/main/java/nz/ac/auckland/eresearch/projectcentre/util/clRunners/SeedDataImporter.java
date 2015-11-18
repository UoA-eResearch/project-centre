package nz.ac.auckland.eresearch.projectcentre.util.clRunners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.entity.ExternalReference;
import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
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
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionRepository;
import nz.ac.auckland.eresearch.projectcentre.util.json.JsonDeserializationHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
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
@Profile({"dev", "test", "mysql"})
public class SeedDataImporter implements CommandLineRunner, Ordered {

  private Logger log = LoggerFactory.getLogger(SeedDataImporter.class);

  @Value("${admin.username}")
  private String adminUsername;
  @Value("${admin.password}")
  private String adminPassword;
  @Value("${seed.location}")
  private String defaultSeed;

  @Autowired
  private JsonDeserializationHelper jsonHelper;
  @Autowired
  private InstitutionRepository instrepo; // just to check whether the db already contains values

  @Autowired
  private ObjectMapper om;

  public <T> void addData(Class<T> type, String folder) throws Exception {

    //workaround for type erasure at compile time
    T classHolder = type.getConstructor().newInstance();

    String name = type.getCanonicalName();
    Path start = Paths.get(folder);
    int maxDepth = 1;
    try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
            String.valueOf(path).toLowerCase().contains("init-" + type.getSimpleName().toLowerCase() + ".json"))) {

      List<File> files = stream
              .sorted()
              .map(p -> p.toFile())
              .collect(Collectors.toList());

      for (File file : files) {
        log.debug("Reading: {}", file);
        JsonNode list = (JsonNode) (om.readValue(file, JsonNode.class));
        for (JsonNode node : list) {
          T value = (T) (om.treeToValue(node, classHolder.getClass()));
          log.debug("Persisting value of {}: {}", type.getSimpleName(), value);
          jsonHelper.save(value);
        }
      }
    }
  }


  public void addSeedData(String seedLocation, int seedLevel) throws Exception {

    if (seedLevel > 0) {
      addData(DivisionalRole.class, seedLocation);
      addData(Facility.class, seedLocation);
      addData(Institution.class, seedLocation);
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


    boolean already_contains_data = instrepo.findAll().iterator().hasNext();
    if (already_contains_data) {
      log.debug("There already seems to be data in the db, skipping seeding of data.");
      return;
    }

    int seedLevel = Integer.MAX_VALUE;
    String seedLocation = this.defaultSeed;
    if (args.length > 0) {
      for (String arg : args) {
        if (arg.startsWith("seed-location=")) {
          seedLocation = arg.substring(14);
        } else if (arg.startsWith("seed-level=")) {
          String value = arg.substring(11);
          seedLevel = Integer.parseInt(value);
        }
      }

    }
    addSeedData(seedLocation, seedLevel);
  }
}



