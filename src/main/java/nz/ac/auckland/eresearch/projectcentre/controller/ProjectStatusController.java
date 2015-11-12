package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectStatus")
public class ProjectStatusController extends BaseController<ProjectStatus, Integer> {

  @Autowired
  public ProjectStatusController(ProjectStatusService service) {
    super(service, new RejectEmptyValidator(ProjectStatus.class, "name"));
  }
}