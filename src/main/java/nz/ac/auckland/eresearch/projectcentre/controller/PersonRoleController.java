package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/personRole")
public class PersonRoleController extends BaseController<PersonRole, Integer> {

  @Autowired
  public PersonRoleController(PersonRoleService service) {
    super(service, new RejectEmptyValidator(PersonRole.class, "name"));
  }
}