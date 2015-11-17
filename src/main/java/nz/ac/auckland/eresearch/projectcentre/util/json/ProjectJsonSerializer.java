package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectStatusRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.ProjectTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by markus on 11/11/15.
 */
public class ProjectJsonSerializer extends JsonSerializer<Project> {

  @Autowired
  private DivisionRepository divRepo;
  @Autowired
  private ProjectStatusRepository projstatrepo;
  @Autowired
  private ProjectTypeRepository projtyperepo;
  @Autowired
  private ObjectMapper om;


  @Override
  public void serialize(Project p, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

    jgen.writeStartObject();

    if (p.getId() != null) {
      jgen.writeNumberField("id", p.getId());
    }

    if (p.getStatusId() != null ) {
      jgen.writeStringField("status", projstatrepo.findOne(p.getStatusId()).getName());
    }

    if (p.getTypeId() != null) {
      jgen.writeStringField("type", projtyperepo.findOne(p.getTypeId()).getName());
    }

    if (p.getDescription() != null ) {
      jgen.writeStringField("description", p.getDescription());
    }

    if (p.getNotes() != null ) {
      jgen.writeStringField("notes", p.getNotes());
    }

    if (p.getStartDate() != null ) {
      jgen.writeStringField("startDate", p.getStartDate());
    }

    if (p.getEndDate() != null ) {
      jgen.writeStringField("endDate", p.getEndDate());
    }

    if (p.getTitle() != null ) {
      jgen.writeStringField("title", p.getTitle());
    }

    if (p.getCode() != null ) {
      jgen.writeStringField("code", p.getCode());
    }

    if (p.getNextReviewDate() != null ) {
      jgen.writeStringField("nextReviewDate", p.getNextReviewDate());
    }

    if (p.getRequirements() != null) {
      jgen.writeStringField("requirements", p.getRequirements());
    }

    if (p.getTodo() != null) {
      jgen.writeStringField("todo", p.getTodo());
    }

    if (p.getDivisionIds().size() > 0) {
      jgen.writeArrayFieldStart("divisions");
      for ( Integer id : p.getDivisionIds() ) {
        Division div = divRepo.findOne(id);
        jgen.writeString(div.getCode());
//        om.writeValue(jgen, div); // we could do this too
      }
      jgen.writeEndArray();
    }

    jgen.writeEndObject();

  }
}
