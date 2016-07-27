package nz.ac.auckland.eresearch.projectcentre.validation;

import java.time.LocalDate;
import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

  @Autowired
  PersonService personService;
  @Autowired
  PersonStatusService personStatusService;
  @Autowired
  ValidationUtil validationUtil;

  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Person p = (Person) o;
    validationUtil.checkNotEmpty(errors, new String[]{"email", "fullName", "phone"});
    validationUtil.checkNotEmpty(errors, "statusId", "status");
    if (!errors.hasErrors()) {
      this.validateEmailUnique(p.getId(), p.getEmail(), errors);
      this.validateStatusId(p.getStatusId(), errors);
      this.validatePhone(p.getPhone(), errors);
    }
    if (p.getStartDate() == null) {
      p.setStartDate(LocalDate.now());
    }
  }

  protected void validateEmailUnique(Integer personId, String email, Errors errors) {
    List<Person> pl = null;
    if (personId == null) { // new person
      pl = this.personService.findByEmail(email);
    } else { // existing person
      pl = this.personService.findByEmailAndIdNot(email, personId);      
    }
    if (pl != null && pl.size() > 0) {
      errors.rejectValue("email", "E-mail address already in use");
    }
  }

  protected void validateStatusId(Integer statusId, Errors errors) {
    if (null == this.personStatusService.findOne(statusId, null)) {
      errors.rejectValue("statusId", "Invalid person status");
    }
  }

  protected void validatePhone(String phone, Errors errors) {
    if (phone == null || !phone.matches(".*\\d+.*")) {
      errors.rejectValue("phone", "Invalid phone number");
    }
  }

}
