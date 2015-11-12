package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/facility")
public class FacilityController extends BaseController<Facility, Integer> {

  @Autowired
  public FacilityController(FacilityService service) {
    super(service, new RejectEmptyValidator(Facility.class, "name"));
  }
}