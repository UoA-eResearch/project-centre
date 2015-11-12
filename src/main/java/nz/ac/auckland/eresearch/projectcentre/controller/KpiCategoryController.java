package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.service.KpiCategoryService;
import nz.ac.auckland.eresearch.projectcentre.validation.RejectEmptyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/kpiCategory")
public class KpiCategoryController extends BaseController<KpiCategory, Integer> {

  @Autowired
  public KpiCategoryController(KpiCategoryService service) {
    super(service, new RejectEmptyValidator(KpiCategory.class, "kpiId", "name"));
  }
}