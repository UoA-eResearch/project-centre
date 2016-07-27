package nz.ac.auckland.eresearch.projectcentre.validation;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class ValidationUtil {

  @Autowired
  DivisionService divisionService;
  @Autowired
  ProjectService projectService;
  @Autowired
  PersonService personService;
  @Autowired
  DivisionalRoleService divisionalRoleService;

  public void validateDivisionId(Integer id, String field, Errors errors) {
    if (null == divisionService.findOne(id, null)) {
      errors.rejectValue(field, "Invalid or no division id: " + id);
    }
  }

  public void validateDivisionalRole(String roleName, String field, Errors errors) {
    if (null == divisionalRoleService.findByName(roleName)) {
      errors.rejectValue(field, "Invalid or no divisional role: " + roleName);
    }
  }

  public void validateProjectId(Integer id, Errors errors) {
    if (null == projectService.findOne(id, null)) {
      errors.rejectValue("projectId", "Invalid or no project id: " + id);
    }
  }

  public void validatePersonId(Integer id, Errors errors) {
    if (null == personService.findOne(id, null)) {
      errors.rejectValue("personId", "Invalid or no person id: " + id);
    }
  }
  
  public void checkNotEmpty(Errors errors, Map<String,String> fields) {
    if (fields != null) {
      for (String field: fields.keySet()) {
        this.checkNotEmpty(errors, field, fields.get(field));
      }
    }
  }

  public void checkNotEmpty(Errors errors, String[] fields) {
    if (fields != null) {
      for (String field: fields) {
        this.checkNotEmpty(errors, field, field);
      }
    }
  }

  public void checkNotEmpty(Errors errors, String field, String translatedField) {
    if (field != null) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, field,
          "Empty or invalid value for mandatory field " + translatedField);
    }
  }

}
