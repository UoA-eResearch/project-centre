package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/researchOutputType")
public class ResearchOutputTypeController extends BaseController<ResearchOutputType, Integer> {

  @Autowired
  public ResearchOutputTypeController(ResearchOutputTypeService service) {
    super(service, new RejectEmptyValidator(ResearchOutputType.class, "name"));
  }
}