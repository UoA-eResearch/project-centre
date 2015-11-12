package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectKpiService;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectKpiValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/projectKpi")
public class ProjectKpiController extends BaseController<ProjectKpi, Integer> {

  @Autowired
  public ProjectKpiController(ProjectKpiService service, ProjectKpiValidator validator) {
    super(service, validator);
  }

}
