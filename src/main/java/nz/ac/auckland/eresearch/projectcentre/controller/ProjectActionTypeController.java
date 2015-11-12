package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionTypeService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectActionType")
public class ProjectActionTypeController extends BaseController<ProjectActionType, Integer> {

  @Autowired
  public ProjectActionTypeController(ProjectActionTypeService service) {
    super(service, new RejectEmptyValidator(ProjectActionType.class, "name"));
  }
}