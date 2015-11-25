package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionalRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by markus on 11/11/15.
 */
public class PersonJsonSerializer extends JsonSerializer<Person> {

  @Autowired
  private DivisionRepository divRepo;
  @Autowired
  private DivisionalRoleRepository divroleRepo;
  @Autowired
  private PersonStatusRepository perstatRepo;

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
      jgen.writeStringField("status", perstatRepo.findOne(p.getStatusId()).getName());
    }

    if (p.getAffiliations().size() > 0) {
      jgen.writeArrayFieldStart("affiliations");
      for (Integer id : p.getAffiliations().keySet()) {
        jgen.writeStartObject();
        Division d = divRepo.findOne(id);
        DivisionalRole divrole = divroleRepo.findOne(p.getAffiliations().get(id));
        jgen.writeStringField("division", d.getCode());
        jgen.writeStringField("role", divrole.getName());
        jgen.writeEndObject();
      }
      jgen.writeEndArray();
    }
    jgen.writeEndObject();

  }
}
