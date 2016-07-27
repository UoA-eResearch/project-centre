package nz.ac.auckland.eresearch.projectcentre.validation;

import java.time.LocalDate;

import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator {

  @Autowired
  protected ProjectStatusService projectStatusService;
  @Autowired
  protected ProjectTypeService projectTypeService;
  @Autowired
  protected ProjectService projectService;
  @Autowired
  protected ValidationUtil validationUtil;
  
  @Override
  public boolean supports(Class<?> clazz) {
    return Project.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Project p = (Project) o;
    validationUtil.checkNotEmpty(errors, new String[]{"title", "description", "code"});
    validationUtil.checkNotEmpty(errors, "typeId", "type");
    validationUtil.checkNotEmpty(errors, "statusId", "status");
    if (!errors.hasErrors()) {
      this.validateDescription(p.getDescription(), errors);
      this.validateStatus(p.getStatusId(), errors);
      this.validateType(p.getTypeId(), errors);
      this.validateCode(p.getId(), p.getCode(), errors);
    }
    if (p.getStartDate() == null) {
      p.setStartDate(LocalDate.now());
    }
  }

  protected void validateStatus(Integer statusId, Errors errors) {
    ProjectStatus ps = this.projectStatusService.findOne(statusId, null);
    if (ps == null) {
      errors.rejectValue("statusId", "Invalid person status");
    }
  }
  
  protected void validateType(Integer typeId, Errors errors) {
    ProjectType pt = this.projectTypeService.findOne(typeId, null);
    if (pt == null) {
      errors.rejectValue("typeId", "Invalid project type");
    }
  }
  
  protected void validateDescription(String description, Errors errors) {
    int length = description.trim().length();
    if (length < 500 || length > 2500) {
      errors.rejectValue("description", "Project description must be"
          + " > 500 and < 2500 characters");
    }
  }
  
  protected void validateCode(Integer id, String code, Errors errors) {
    Project p = null;
    if (id == null) {
      p = this.projectService.findByCode(code);
    } else {
      p = this.projectService.findByCodeAndIdNot(code, id);      
    }
    if (p != null) {
      errors.rejectValue("code", "Project code already in use: " + code);
    }
  }
  
}
