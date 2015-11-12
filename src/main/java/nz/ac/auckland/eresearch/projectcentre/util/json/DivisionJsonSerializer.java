package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import java.io.IOException;

/**
 * Created by markus on 11/11/15.
 */
public class DivisionJsonSerializer extends JsonSerializer<Division> {


  @Override
  public void serialize(Division value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

    //TODO: maybe add institution details here as well, should only be a one-time lookup cost
    jgen.writeStartObject();
    addDivision(value, jgen);
    jgen.writeEndObject();
  }

  public void addDivision(Division value, JsonGenerator jgen) throws IOException {

    jgen.writeNumberField("id", value.getId());
    jgen.writeStringField("name", value.getName());
    jgen.writeStringField("code", value.getCode());
    int level = value.getLevel();
    jgen.writeNumberField("level", level);
    jgen.writeNumberField("institutionId", value.getInstitutionId());
    jgen.writeStringField("institutionCode", value.getInstitutionCode());
    int topId = value.getTop().getId();
    jgen.writeNumberField("topId", value.getTop().getId());
    if (level != 0) {
      jgen.writeObjectFieldStart("top");
      addDivision(value.getTop(), jgen);
      jgen.writeEndObject();
    }
    Division parent = value.getParent();
    if (parent != null) {
      jgen.writeObjectFieldStart("parent");
      addDivision(parent, jgen);
      jgen.writeEndObject();
    }

  }
}
