package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.InstitutionalRole;
import nz.ac.auckland.eresearch.projectcentre.service.InstitutionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/institutionalRole")
public class InstitutionalRoleController extends BaseController<InstitutionalRole, Integer> {

  @Autowired
  public InstitutionalRoleController(InstitutionalRoleService service) {
    super(service, new RejectEmptyValidator(InstitutionalRole.class, "name"));
  }
}