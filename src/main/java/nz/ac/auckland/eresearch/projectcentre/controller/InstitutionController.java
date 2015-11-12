package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.Institution;
import nz.ac.auckland.eresearch.projectcentre.service.InstitutionService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/institution")
public class InstitutionController extends BaseController<Institution, Integer> {

  @Autowired
  public InstitutionController(InstitutionService service) {
    super(service, new RejectEmptyValidator(Institution.class, "name", "code"));
  }
}

