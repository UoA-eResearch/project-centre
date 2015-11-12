package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.service.KpiService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/kpi")
public class KpiController extends BaseController<Kpi, Integer> {

  @Autowired
  public KpiController(KpiService service) {
    super(service, new RejectEmptyValidator(Kpi.class, "number", "measures", "title", "type"));
  }
}