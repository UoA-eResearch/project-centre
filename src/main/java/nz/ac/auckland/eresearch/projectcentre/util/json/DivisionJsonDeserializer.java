package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityNotFoundException;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.InstitutionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by markus on 10/11/15.
 */
public class DivisionJsonDeserializer extends JsonDeserializer<Division> {

  @Autowired
  private DivisionRepository divRepo;

  @Autowired
  private InstitutionRepository instRepo;

  /*
  This is only used when deserializing without JPA in TestCases.
   */
  private Division assembleDivision(JsonNode node) throws JsonEntityNotFoundException {

    Division div = null;
    JsonNode id = node.get("id");
    if (id != null) {
      div = new Division(id.asInt());
    } else {
      div = new Division();
    }


    JsonNode code = node.get("code");
    if (code != null) {
      div.setCode(code.asText());
    }

    JsonNode instId = node.get("institutionId");
    if (instId != null) {
      div.setInstitutionId(instId.asInt());
    }

    JsonNode instCode = node.get("institutionCode");
    Optional<String> instCodeString = JsonHelpers.checkNodeExistsAndNotEmptyString(instCode);
    if (instCodeString.isPresent()) {
      Institution i = instRepo.findByCode(instCodeString.get());
      if (i == null) {
        throw new JsonEntityNotFoundException("Could not find institution for code: " + instCodeString.get());
      }
      div.setInstitutionId(i.getId());
    }

    JsonNode top = node.get("top");
    if (top != null) {
      Division topDiv = assembleDivision(top);
      div.setTop(topDiv);
    }

    JsonNode parent = node.get("parent");
    if (parent != null) {
      setParent(div, node);
    }

    return div;

  }

  private Division setParent(Division div, JsonNode node) throws JsonEntityNotFoundException {

    Division parent = null;

    JsonNode parentNode = node.get("parentId");
    if (parentNode != null) {
      parent = divRepo.findOne(parentNode.asInt());
    } else {
      parentNode = node.get("parentCode");
      if (parentNode != null) {
        parent = divRepo.findByCode(parentNode.asText());
      } else {
        parentNode = node.get("parent");
        if (parentNode != null) {
          // rest assured test framework creates it's own object mapper, which doesn't auto-wire
          if (divRepo == null) {
            parent = assembleDivision(parentNode);
          } else {
            JsonNode tempNode = parentNode.get("id");
            if (tempNode != null) {
              int tempId = tempNode.asInt();
              parent = divRepo.findOne(tempId);
            }
          }
        }
      }
    }

    if (parent != null) {
      div.setParent(parent);
    }

    return parent;
  }

  @Override
  public Division deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);

    Division providedDivision = new Division();

    String name = node.get("name").asText();
    providedDivision.setName(name);
    String code = node.get("code").asText();
    providedDivision.setCode(code);

    setParent(providedDivision, node);

    Division parent = providedDivision.getParent();

    JsonNode instNode = node.get("institutionId");
    if (instNode != null) {
      if (parent != null && instNode.asInt() != parent.getInstitutionId()) {
        throw new RuntimeException("institutionId does not match parents institutionId");
      } else {
        providedDivision.setInstitutionId(instNode.asInt());
      }
    } else {

      // let's try the institution code
      instNode = node.get("institutionCode");
      if (instNode != null) {
        Institution i = instRepo.findByCode(instNode.asText());

        if (parent != null && i.getId() != parent.getInstitutionId()) {
          throw new RuntimeException("institutionId does not match parents institutionId");
        } else {
          providedDivision.setInstitutionId(i.getId());
        }

      } else {
        // this means we need to use the institution id from parent
        if (parent == null) {
          throw new RuntimeException("No institutionId provided, and no parent");
        }
        providedDivision.setInstitutionId(parent.getInstitutionId());
      }
    }

    return providedDivision;
  }
}
