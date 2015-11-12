package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/personStatus")
public class PersonStatusController extends BaseController<PersonStatus, Integer> {

  @Autowired
  public PersonStatusController(PersonStatusService service) {
    super(service, new RejectEmptyValidator(PersonStatus.class, "name"));
  }
}