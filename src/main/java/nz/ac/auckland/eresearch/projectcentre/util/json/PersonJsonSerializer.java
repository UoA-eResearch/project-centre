package nz.ac.auckland.eresearch.projectcentre.util.json;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Created by markus on 11/11/15.
 */
public class PersonJsonSerializer extends JsonSerializer<Person> {

  @Autowired
  private DivisionService divisionService;
  @Autowired
  private DivisionalRoleService divisionalRoleService;
  @Autowired
  private PersonStatusService personStatusService;

  @Override
  public void serialize(Person p, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

    jgen.writeStartObject();

    if (p.getId() != null) {
      jgen.writeNumberField("id", p.getId());
    }

    if (p.getEmail() != null) {
      jgen.writeStringField("email", p.getEmail());
    }

    if (p.getFullName() != null) {
      jgen.writeStringField("fullName", p.getFullName());
    }

    if (p.getPreferredName() != null) {
      jgen.writeStringField("preferredName", p.getPreferredName());
    }

    if (p.getNotes() != null) {
      jgen.writeStringField("notes", p.getNotes());
    }

    if (p.getPhone() != null) {
      jgen.writeStringField("phone", p.getPhone());
    }

    if (p.getPictureUrl() != null) {
      jgen.writeStringField("pictureUrl", p.getPictureUrl());
    }

    if (p.getStartDate() != null) {
      jgen.writeStringField("startDate", p.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    if (p.getEndDate() != null) {
      jgen.writeStringField("endDate", p.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    if (p.getStatusId() != null) {
      jgen.writeStringField("status", personStatusService.findOne(p.getStatusId()).getName());
    }

    if (p.getAffiliations().size() > 0) {
      jgen.writeArrayFieldStart("affiliations");
      for (Integer id : p.getAffiliations().keySet()) {
        jgen.writeStartObject();
        Division d = divisionService.findOne(id);
        DivisionalRole divrole = divisionalRoleService.findOne(p.getAffiliations().get(id));
        jgen.writeStringField("division", d.getCode());
        jgen.writeStringField("role", divrole.getName());
        jgen.writeEndObject();
      }
      jgen.writeEndArray();
    }
    jgen.writeEndObject();

  }
}
