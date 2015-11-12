package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProject;
import nz.ac.auckland.eresearch.projectcentre.service.PersonProjectService;
import nz.ac.auckland.eresearch.projectcentre.validation.PersonProjectValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/personProject")
public class PersonProjectController extends BaseController<PersonProject, Integer> {

  PersonProjectService service;

  @Autowired
  public PersonProjectController(PersonProjectService service, PersonProjectValidator validator) {
    super(service, validator);
    this.service = service;
  }

  @RequestMapping(value = "/findByPersonId/{id}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public
  @ResponseBody
  Iterable<PersonProject> findByResearcherId(@PathVariable Integer id) {
    return service.findByPersonId(id);
  }

}
