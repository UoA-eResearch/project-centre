package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;
import nz.ac.auckland.eresearch.projectcentre.validation.PersonPropertyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/personProperty")
public class PersonPropertyController extends BaseController<PersonProperty, Integer> {

  @Autowired
  public PersonPropertyController(PersonPropertyService service, PersonPropertyValidator validator) {
    super(service, validator);
  }
}