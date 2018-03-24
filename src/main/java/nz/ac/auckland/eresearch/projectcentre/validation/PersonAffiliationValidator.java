package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonAffiliationService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonAffiliationValidator implements Validator {

  @Autowired
  PersonAffiliationService personService;
  @Autowired
  DivisionalRoleService divisionalRoleService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return PersonAffiliation.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    PersonAffiliation tmp = (PersonAffiliation) o;
    validationUtil.checkNotEmpty(errors, new String[]{"personId"});
    validationUtil.checkNotEmpty(errors, "divisionId", "division");
    validationUtil.checkNotEmpty(errors, "divisionalRoleId", "divisionalRole");
    if (!errors.hasFieldErrors()) {
      validationUtil.validatePersonId(tmp.getPersonId(), errors);
      validationUtil.validateDivisionId(tmp.getDivisionId(), "divisionId", errors);
      if (null == this.divisionalRoleService.findOne(tmp.getDivisionalRoleId(), null)) {
        errors.rejectValue("divisionalRoleId", "Invalid divisional role");
      }
    }
  }

}
