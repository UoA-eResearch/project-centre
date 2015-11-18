package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityInvalidException;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityNotFoundException;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by markus on 16/11/15.
 */
public class ProjectJsonDeserializer extends JsonDeserializer<Project> {

  @Autowired
  private ProjectStatusRepository projstatrepo;
  @Autowired
  private ProjectTypeRepository projtyperepo;
  @Autowired
  private DivisionRepository divrepo;
  @Autowired
  private ObjectMapper om;


  @Override
  public Project deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);


    Project p = new Project();

    // id
    JsonNode temp = node.get("id");
    if (temp != null) {
      p.setId(temp.asInt());
    }

    // statusId
    temp = node.get("statusId");
    if (temp != null) {
      p.setStatusId(temp.asInt());
    } else {
      temp = node.get("status");
      if (temp != null) {
        ProjectStatus stat = projstatrepo.findByName(temp.asText());
        p.setStatusId(stat.getId());
      }
    }

    // type
    temp = node.get("typeId");
    if (temp != null) {
      p.setTypeId(temp.asInt());
    } else {
      temp = node.get("type");
      if (temp != null) {
        ProjectType type = projtyperepo.findByName(temp.asText());
        p.setTypeId(type.getId());
      }
    }

    // description
    temp = node.get("description");
    if (temp != null) {
      p.setDescription(temp.asText());
    }

    // endDate
    temp = node.get("endDate");
    Optional<String> text = JsonHelpers.checkNodeExistsAndNotEmptyString(temp);
    if (text.isPresent()) {
      p.setEndDate(LocalDate.parse(text.get()));
    }

    // startDate
    temp = node.get("startDate");
    text = JsonHelpers.checkNodeExistsAndNotEmptyString(temp);
    if (text.isPresent()) {
      p.setStartDate(LocalDate.parse(text.get()));
    }

    // notes
    temp = node.get("notes");
    if (temp != null) {
      p.setNotes(temp.asText());
    }

    // title
    temp = node.get("title");
    if (temp != null) {
      p.setTitle(temp.asText());
    }

    // code
    temp = node.get("code");
    if (temp != null) {
      p.setCode(temp.asText());
    }

    // nextReviewDate
    temp = node.get("nextReviewDate");
    text = JsonHelpers.checkNodeExistsAndNotEmptyString(temp);
    if (text.isPresent()) {
      p.setNextReviewDate(LocalDate.parse(text.get()));
    }

    // requirements
    temp = node.get("requirements");
    if (temp != null) {
      p.setRequirements(temp.asText());
    }

    // requirements
    temp = node.get("todo");
    if (temp != null) {
      p.setTodo(temp.asText());
    }

    // affiliations
    temp = node.get("divisionIds");
    if (temp == null) {
      temp = node.get("divisions");
    }
    if (temp != null) {
      List<Object> divisions = om.convertValue(temp, List.class);
      List<Integer> divisionIds = Lists.newArrayList();
      for (Object div : divisions) {
        Integer divId = -1;
        if (div instanceof Integer) {
          divId = (Integer) div;
        } else {
          try {
            divId = Integer.parseInt((String) div);
          } catch (NumberFormatException nfe) {
            // means we'll try the string as code
            Division d = divrepo.findByCode((String) div);
            if (d == null) {
              throw new JsonEntityNotFoundException("No division with id/code: " + div);
            }
            divId = d.getId();
          } catch (ClassCastException cce) {
            throw new JsonEntityInvalidException("Can't parse divisions/divisionIds field: " + div);
          }
        }
        divisionIds.add(divId);

      }
      p.setDivisionIds(divisionIds);
    }

    return p;
  }
}
