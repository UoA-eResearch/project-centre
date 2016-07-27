package nz.ac.auckland.eresearch.projectcentre.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonStatus;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class PersonValidatorTest {

  Person p;
  Errors errors;
  PersonValidator validator;
  PersonService ps;
  PersonStatusService pss;
  
  @Before
  public void setup() {
    p = new Person();
    errors = new BeanPropertyBindingResult(p, "");
    validator = new PersonValidator();
    pss = mock(PersonStatusService.class);
    ps = mock(PersonService.class);
    ReflectionTestUtils.setField(validator, "validationUtil", new ValidationUtil());
    ReflectionTestUtils.setField(validator, "personService", ps);
    ReflectionTestUtils.setField(validator, "personStatusService", pss);
  }
  
  @Test
  public void testValidatePhoneInvalid1() {
    validator.validatePhone("abcd", errors);
    assertNotNull(errors.getFieldError("phone"));    
  }

  @Test
  public void testValidatePhoneInvalid2() {
    validator.validatePhone("", errors);
    assertNotNull(errors.getFieldError("phone"));    
  }

  @Test
  public void testValidatePhoneInvalid3() {
    validator.validatePhone(null, errors);
    assertNotNull(errors.getFieldError("phone"));    
  }

  @Test
  public void testValidatePhoneValid() {
    validator.validatePhone("ext1234", errors);
    assertNull(errors.getFieldError("phone"));    
  }

  @Test
  public void testUniqueEmail() {
    when(ps.findByEmail(anyString())).thenReturn(null);
    validator.validateEmailUnique(1, "test@test.org", errors);
    assertNull(errors.getFieldError("email"));    
  }

  @Test
  public void testNotUniqueEmailNewUser() {
    List<Person> tmp = new LinkedList<Person>();
    tmp.add(p);
    when(ps.findByEmail(anyString())).thenReturn(tmp);
    validator.validateEmailUnique(null, "test@test.org", errors);
    assertNotNull(errors.getFieldError("email"));    
  }

  @Test
  public void testNotUniqueEmailExistingUser() {
    List<Person> tmp = new LinkedList<Person>();
    tmp.add(p);
    when(ps.findByEmailAndIdNot(anyString(), anyInt())).thenReturn(tmp);
    validator.validateEmailUnique(1, "test@test.org", errors);
    assertNotNull(errors.getFieldError("email"));    
  }

  @Test
  public void testNonExistingStatus() {
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(null);
    validator.validateStatusId(1, errors);
    assertNotNull(errors.getFieldError("statusId"));    
  }

  @Test
  public void testExistingStatus() {
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new PersonStatus());
    validator.validateStatusId(1, errors);
    assertNull(errors.getFieldError("statusId"));    
  }

  @Test
  public void testEmptyPerson() {
    validator.validate(p, errors);
    assertEquals(4, errors.getFieldErrorCount());
  }

  @Test
  public void testNewPersonSuccess() {
    when(ps.findByEmail(anyString())).thenReturn(null);
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new PersonStatus());
    p.setEmail("test@test.org");
    p.setFullName("Test");
    p.setPhone("ext123");
    p.setStatusId(1);
    validator.validate(p, errors);
    assertFalse(errors.hasErrors());
  }

}
