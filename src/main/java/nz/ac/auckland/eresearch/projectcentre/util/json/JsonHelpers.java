package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by markus on 18/11/15.
 */
@Component
public class JsonHelpers {

  private static Logger log = LoggerFactory.getLogger(JsonHelpers.class);
  @Autowired
  private ObjectMapper om;
  private Repositories repositories = null;

  @Autowired
  public JsonHelpers(WebApplicationContext appContext) {
    repositories = new Repositories(appContext);
  }

  public static Optional<String> checkNodeExistsAndNotEmptyString(JsonNode node) {

    if (node == null) {
      return Optional.empty();
    } else {
      String text = node.asText();
      if (Strings.isNullOrEmpty(text)) {
        return Optional.empty();
      } else {
        return Optional.of(text);
      }
    }
  }

  private <T> CrudRepository<T, Serializable> getCrudRepository(T cls) {
    Object inv = repositories.getRepositoryFor(cls.getClass());
    return (CrudRepository<T, Serializable>) inv;

  }

  public <T> T save(T entity) {
    return getCrudRepository(entity).save(entity);
  }

  public <T> T findOne(Class<T> cls, Serializable id) {
    return (T) getCrudRepository(cls).findOne(id);
  }

  public <T> List<T> readJsonFromFile(Class<T> type, String folder, String filename_matcher, boolean persistStraightAway) throws Exception {

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

      List<T> result = Lists.newArrayList();
      for (File file : files) {
        log.debug("Reading: {}", file);
        JsonNode list = (JsonNode) (om.readValue(file, JsonNode.class));
        for (JsonNode node : list) {
          T value = (T) (om.treeToValue(node, classHolder.getClass()));
          log.debug("Persisting value of {}: {}", type.getSimpleName(), value);
          result.add(value);
          if (persistStraightAway) {
            save(value);
          }
        }
      }
      return result;
    }

  }
}
