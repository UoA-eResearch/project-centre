package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.ExternalReference;
import nz.ac.auckland.eresearch.projectcentre.service.ExternalReferenceService;
import nz.ac.auckland.eresearch.projectcentre.validation.ExternalReferenceValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/externalReference")
public class ExternalReferenceController extends BaseController<ExternalReference, Integer> {

  @Autowired
  public ExternalReferenceController(ExternalReferenceService service, ExternalReferenceValidator validator) {
    super(service, validator);
  }
}

