package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PersonValidator implements Validator {

  @Autowired
  PersonService personService;

  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object person, Errors errors) {
    Person p = (Person) person;
    String[] notEmpty = {"email", "fullName", "phone", "startDate"};
    new RejectEmptyValidator(Person.class, notEmpty).validate(person, errors);
    if (!errors.hasErrors()) {
      this.validateEmail(p, errors);
    }
  }

  private void validateEmail(Person person, Errors errors) {
    if (person.getId() == null) { // new person
      Person p = this.personService.findByEmail(person.getEmail());
      if ( p != null ) {
        errors.rejectValue("email", "person.email.in.use");
      }
    } else { // existing person
      List<Person> tmp = this.personService.findByEmailAndIdNot(person.getEmail(), person.getId());
      if (tmp != null && tmp.size() > 0) {
        errors.rejectValue("email", "person.email.in.use");
      }
    }
  }

}
