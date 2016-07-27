package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectExternalReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ExternalReferenceValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProjectExternalReference.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object externalReference, Errors errors) {
    ProjectExternalReference tmp = (ProjectExternalReference) externalReference;
    validationUtil.checkNotEmpty(errors, new String[]{"projectId"});
    if (!errors.hasErrors()) {
      this.validationUtil.validateProjectId(tmp.getProjectId(), errors);
    }
    if (tmp.getDate() == null) {
      tmp.setDate(LocalDate.now());
    }
  }

}
