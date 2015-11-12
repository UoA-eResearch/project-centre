package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController extends BaseController<Project, Integer> {

  private ProjectService service;

  @Autowired
  public ProjectController(ProjectService service, ProjectValidator validator) {
    super(service, validator);
    this.service = service;
  }

  @RequestMapping(value = "/findByDivisionId/{id}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public
  @ResponseBody
  List<Project> findByDivisionId(@PathVariable Integer id) {
    return service.findByDivisionId(id);
  }

}
