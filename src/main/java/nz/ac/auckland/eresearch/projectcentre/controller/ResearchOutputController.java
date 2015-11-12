package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService;
import nz.ac.auckland.eresearch.projectcentre.validation.ResearchOutputValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/researchOutput")
public class ResearchOutputController extends BaseController<ResearchOutput, Integer> {

  @Autowired
  public ResearchOutputController(ResearchOutputService service, ResearchOutputValidator validator) {
    super(service, validator);
  }

}
