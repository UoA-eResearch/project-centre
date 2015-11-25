package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/institutionalRole")
public class InstitutionalRoleController extends BaseController<DivisionalRole, Integer> {

  @Autowired
  public InstitutionalRoleController(DivisionalRoleService service) {
    super(service, new RejectEmptyValidator(DivisionalRole.class, "name"));
  }
}