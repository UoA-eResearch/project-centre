package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;
import nz.ac.auckland.eresearch.projectcentre.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResearchOutputValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  DateUtil dateUtil;
  @Autowired
  ResearchOutputTypeService service;

  @Override
  public boolean supports(Class<?> clazz) {
    return ResearchOutput.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object personProject, Errors errors) {
    ResearchOutput ro = (ResearchOutput) personProject;
    String[] notEmpty = {"personId", "projectId", "typeId", "description"};
    new RejectEmptyValidator(ResearchOutput.class, notEmpty).validate(personProject, errors);
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(ro.getProjectId(), errors);
      this.validationUtil.validatePersonId(ro.getPersonId(), errors);
      this.validateResearchOutputTypeId(ro.getTypeId(), errors);
    }
    ro.setDate(dateUtil.getCurrentDate());
  }

  public void validateResearchOutputTypeId(Integer id, Errors errors) {
    ResearchOutputType rot = service.findOne(id);
    if (rot == null) {
      errors.rejectValue("typeId", "research.output.type.id.invalid");
    }
  }

}
