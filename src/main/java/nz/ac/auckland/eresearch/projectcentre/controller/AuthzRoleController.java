package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.AuthzRole;
import nz.ac.auckland.eresearch.projectcentre.service.AuthzRoleService;
import nz.ac.auckland.eresearch.projectcentre.validation.AuthzRoleValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/authzRole")
public class AuthzRoleController extends BaseController<AuthzRole, Integer> {

  @Autowired
  public AuthzRoleController(AuthzRoleService service, AuthzRoleValidator validator) {
    super(service, validator);
  }
}