package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectType")
public class ProjectTypeController extends BaseController<ProjectType, Integer> {

  @Autowired
  public ProjectTypeController(ProjectTypeService service) {
    super(service, new RejectEmptyValidator(ProjectType.class, "name"));
  }
}

