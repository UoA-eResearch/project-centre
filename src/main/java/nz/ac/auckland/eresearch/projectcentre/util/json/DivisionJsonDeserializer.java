package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityNotFoundException;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by markus on 10/11/15.
 */
public class DivisionJsonDeserializer extends JsonDeserializer<Division> {

  @Autowired
  private DivisionService divisionService;

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
      int parentId = parentNode.asInt();
      parent = divisionService.findOne(parentId);
      if (parent == null ) {
        throw new JsonEntityNotFoundException("Could not find parent division with id: "+parentId);
      }
    } else {
      parentNode = node.get("parentCode");
      if (parentNode != null) {
        String parentCode = parentNode.asText();
        parent = divisionService.findByCode(parentCode);
        if ( parent == null ) {
          throw new JsonEntityNotFoundException("Could not find parent division with code: "+parentCode);
        }
      } else {
        parentNode = node.get("parent");
        if (parentNode != null) {
          // rest assured test framework creates it's own object mapper, which doesn't auto-wire
          if (divisionService == null) {
            parent = assembleDivision(parentNode);
          } else {
            JsonNode tempNode = parentNode.get("id");
            if (tempNode != null) {
              int tempId = tempNode.asInt();
              parent = divisionService.findOne(tempId);
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

    return providedDivision;
  }
}
