package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectPropertyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectProperty")
public class ProjectPropertyController extends BaseController<ProjectProperty, Integer> {

  @Autowired
  public ProjectPropertyController(ProjectPropertyService service, ProjectPropertyValidator validator) {
    super(service, validator);
  }

}
