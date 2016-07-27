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

import java.time.LocalDate;

import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectType;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ProjectValidatorTest {
  
  Project p;
  Errors errors;
  ProjectValidator validator;
  ProjectService ps;
  ProjectStatusService pss;
  ProjectTypeService pts;

  
  @Before
  public void setup() {
    p = new Project();
    errors = new BeanPropertyBindingResult(p, "");
    validator = new ProjectValidator();
    ps = mock(ProjectService.class);
    pss = mock(ProjectStatusService.class);
    pts = mock(ProjectTypeService.class);
    ReflectionTestUtils.setField(validator, "validationUtil", new ValidationUtil());
    ReflectionTestUtils.setField(validator, "projectService", ps);
    ReflectionTestUtils.setField(validator, "projectStatusService", pss);
    ReflectionTestUtils.setField(validator, "projectTypeService", pts);
  }
  
  @Test
  public void testInvalidDescription1() {
    validator.validateDescription(StringUtils.repeat('a', 499), errors);
    assertNotNull(errors.getFieldError("description"));    
  }

  @Test
  public void testInvalidDescription2() {
    validator.validateDescription(StringUtils.repeat('a', 2501), errors);
    assertNotNull(errors.getFieldError("description"));    
  }

  @Test
  public void testDescriptionSucess() {
    validator.validateDescription(StringUtils.repeat('a', 500), errors);
    assertNull(errors.getFieldError("description"));    
  }

  @Test
  public void testNonExistingStatus() {
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(null);
    validator.validateStatus(1, errors);
    assertNotNull(errors.getFieldError("statusId"));    
  }

  @Test
  public void testExistingStatus() {
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new ProjectStatus());
    validator.validateStatus(1, errors);
    assertNull(errors.getFieldError("statusId"));    
  }

  @Test
  public void testNonExistingType() {
    when(pts.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(null);
    validator.validateType(1, errors);
    assertNotNull(errors.getFieldError("typeId"));
  }

  @Test
  public void testExistingType() {
    when(pts.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new ProjectType());
    validator.validateStatus(1, errors);
    assertNull(errors.getFieldError("typeId"));    
  }

  @Test
  public void testEmptyProject() {
    validator.validate(p, errors);
    assertEquals(5, errors.getFieldErrorCount());
  }

  @Test
  public void testUniqueCode() {
    when(ps.findByCode(anyString())).thenReturn(null);
    validator.validateCode(1, "test00001", errors);
    assertNull(errors.getFieldError("email"));    
  }

  @Test
  public void testNotUniqueCodeNewProject() {
    when(ps.findByCode(anyString())).thenReturn(p);
    validator.validateCode(null, "test00001", errors);
    assertNotNull(errors.getFieldError("code"));    
  }

  @Test
  public void testNotUniqueCodeExistingProject() {
    when(ps.findByCodeAndIdNot(anyString(), anyInt())).thenReturn(p);
    validator.validateCode(1, "test00001", errors);
    assertNotNull(errors.getFieldError("code"));    
  }

  @Test
  public void testNewPersonSuccess() {
    when(ps.findByCode(anyString())).thenReturn(null);
    when(pss.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new ProjectStatus());
    when(pts.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new ProjectType());
    p.setCode("test00001");
    p.setDescription(StringUtils.repeat('a', 500));
    p.setStartDate(LocalDate.now());
    p.setStatusId(1);
    p.setTitle("test");
    p.setTypeId(1);
    validator.validate(p, errors);
    assertFalse(errors.hasErrors());
  }

}
