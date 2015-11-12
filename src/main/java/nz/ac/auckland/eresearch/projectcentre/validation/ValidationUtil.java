package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.InstitutionService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidationUtil {

  @Autowired
  InstitutionService institutionService;
  @Autowired
  DivisionService divisionService;
  @Autowired
  ProjectService projectService;
  @Autowired
  PersonService personService;

  public void validateInstitutionId(Integer institutionId, Errors errors) {
    Institution institution = institutionService.findOne(institutionId);
    if (institution != null) {
      return;
    }
    errors.rejectValue("institutionId", "institution.id.invalid");
  }

  public void validateDivisionId(Integer divisionId, Errors errors) {
    Division division = divisionService.findOne(divisionId);
    if (division != null) {
      return;
    }
    errors.rejectValue("divisionId", "division.id.invalid");
  }

  public void validateProjectId(Integer id, Errors errors) {
    Project project = projectService.findOne(id);
    if (project == null) {
      errors.rejectValue("projectId", "project.id.invalid");
    }
  }

  public void validatePersonId(Integer id, Errors errors) {
    Person person = personService.findOne(id);
    if (person == null) {
      errors.rejectValue("personId", "person.id.invalid");
    }
  }

}
