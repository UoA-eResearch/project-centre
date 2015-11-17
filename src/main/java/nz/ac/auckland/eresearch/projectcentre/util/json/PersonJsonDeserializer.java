package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityInvalidException;
import nz.ac.auckland.eresearch.projectcentre.exceptions.JsonEntityNotFoundException;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionalRoleRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * Created by markus on 16/11/15.
 */
public class PersonJsonDeserializer extends JsonDeserializer<Person> {

  @Autowired
  private PersonStatusRepository perstatrepo;
  @Autowired
  private DivisionRepository divrepo;
  @Autowired
  private DivisionalRoleRepository divrolerepo;
  @Autowired
  private ObjectMapper om;


  @Override
  public Person deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);


    Person p = new Person();

    // id
    JsonNode temp = node.get("id");
    if ( temp != null ) {
      p.setId(temp.asInt());
    }

    // email
    temp = node.get("email");
    if ( temp != null ) {
      p.setEmail(temp.asText());
    }

    // full name
    temp = node.get("fullName");
    if ( temp != null ) {
      p.setFullName(temp.asText());
    }

    // preferred name
    temp = node.get("preferredName");
    if ( temp != null ) {
      p.setPreferredName(temp.asText());
    }

    // notes
    temp = node.get("notes");
    if ( temp != null ) {
      p.setNotes(temp.asText());
    }

    // phone
    temp = node.get("phone");
    if ( temp != null ) {
      p.setPhone(temp.asText());
    }

    // picture url
    temp = node.get("pictureUrl");
    if ( temp != null ) {
      p.setPictureUrl(temp.asText());
    }

    // startdate
    temp = node.get("startDate");
    if ( temp != null ) {
      p.setStartDate(temp.asText());
    }

    // enddate
    temp = node.get("endDate");
    if ( temp != null ) {
      p.setEndDate(temp.asText());
    }

    // status
    temp = node.get("statusId");
    if ( temp != null ) {
      p.setStatusId(temp.asInt());
    } else {
      temp = node.get("status");
      if ( temp != null ) {
        PersonStatus status = perstatrepo.findByName(temp.asText());
        p.setStatusId(status.getId());
        if ( status == null ) {
          throw new JsonEntityNotFoundException("No person status named: "+temp.asText());
        }
      }
    }

    // affiliations
    temp = node.get("affiliations");
    if ( temp != null ) {
      Map<String, Object> affiliations = om.convertValue(temp, Map.class);
      for ( String div : affiliations.keySet() ) {
        int divId = -1;
        try {
          divId = Integer.parseInt(div);
        } catch (NumberFormatException nfe) {
          // means we'll try the string as code
          Division d = divrepo.findByCode(div);
          if ( d == null ) {
            throw new JsonEntityNotFoundException("No division with id/code: "+div);
          }
          divId = d.getId();
        }

        Object value = affiliations.get(div);
        Integer divRoleId = -1;
        if ( value instanceof Integer ) {
          divRoleId = (Integer)value;
        } else {
          try {
            try {
              divRoleId = Integer.parseInt((String) value);
            } catch (NumberFormatException nfe) {
              // means we'll try the string as name
              DivisionalRole dr = divrolerepo.findByName((String) value);
              if ( dr == null ) {
                throw new JsonEntityNotFoundException("No divisional role with id/code: "+value);
              }
              divRoleId = dr.getId();
            }
          } catch (ClassCastException cce) {
            throw new JsonEntityInvalidException("Can't parse affiliation field");
          }
        }

        p.addAffiliation(divId, divRoleId);

      }
    }

    return p;
  }
}
