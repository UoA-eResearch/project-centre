package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectFacility;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectFacilityService;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectFacilityValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectFacility")
public class ProjectFacilityController extends BaseController<ProjectFacility, Integer> {

  @Autowired
  public ProjectFacilityController(ProjectFacilityService service, ProjectFacilityValidator validator) {
    super(service, validator);
  }
}