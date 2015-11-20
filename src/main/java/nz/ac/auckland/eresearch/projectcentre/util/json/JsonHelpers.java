package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.google.common.base.Strings;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * Created by markus on 18/11/15.
 */
public class JsonHelpers {

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
}
