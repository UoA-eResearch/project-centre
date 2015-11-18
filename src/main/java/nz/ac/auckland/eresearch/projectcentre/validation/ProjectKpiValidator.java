package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Kpi;
import nz.ac.auckland.eresearch.projectcentre.entity.KpiCategory;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.service.KpiCategoryService;
import nz.ac.auckland.eresearch.projectcentre.service.KpiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ProjectKpiValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  KpiService kpiService;
  @Autowired
  KpiCategoryService kpiCategoryService;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectKpi.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object projectKpi, Errors errors) {
    ProjectKpi pk = (ProjectKpi) projectKpi;
    String[] notEmpty = {"kpiId", "personId", "projectId"};
    new RejectEmptyValidator(ProjectKpi.class, notEmpty).validate(projectKpi, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(pk.getProjectId(), errors);
      this.validationUtil.validatePersonId(pk.getPersonId(), errors);
      this.validateKpiId(pk.getKpiId(), errors);
      if (pk.getKpiCategoryId() != null) {
        this.validateKpiId(pk.getKpiCategoryId(), errors);
      }
    }
    pk.setDate(LocalDate.now());

  }

  public void validateKpiId(Integer id, Errors errors) {
    Kpi k = kpiService.findOne(id);
    if (k == null) {
      errors.rejectValue("kpiId", "kpi.id.invalid");
    }
  }

  public void validateKpiCategoryId(Integer id, Errors errors) {
    KpiCategory kc = kpiCategoryService.findOne(id);
    if (kc == null) {
      errors.rejectValue("kpiId", "kpi.category.id.invalid");
    }
  }

}
