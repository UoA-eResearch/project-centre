package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectActionValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectAction")
public class ProjectActionController extends BaseController<ProjectAction, Integer> {

  @Autowired
  public ProjectActionController(ProjectActionService service, ProjectActionValidator validator) {
    super(service, validator);
  }

}
