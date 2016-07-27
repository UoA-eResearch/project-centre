package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutputType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ResearchOutputValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  ResearchOutputTypeService service;

  @Override
  public boolean supports(Class<?> clazz) {
    return ResearchOutput.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object personProject, Errors errors) {
    ResearchOutput ro = (ResearchOutput) personProject;
    validationUtil.checkNotEmpty(errors, new String[]{"personId", "projectId", "typeId", "description"});
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(ro.getProjectId(), errors);
      this.validateResearchOutputTypeId(ro.getTypeId(), errors);
    }
    if (ro.getDateReported() == null) {
      ro.setDateReported(LocalDate.now());
    }
  }

  public void validateResearchOutputTypeId(Integer id, Errors errors) {
    ResearchOutputType rot = service.findOne(id, null);
    if (rot == null) {
      errors.rejectValue("typeId", "Invalid research output type");
    }
  }

}
