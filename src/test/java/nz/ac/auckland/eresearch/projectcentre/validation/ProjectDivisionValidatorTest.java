package nz.ac.auckland.eresearch.projectcentre.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ProjectDivisionValidatorTest {
  
  ProjectDivision pd;
  Errors errors;
  ProjectDivisionValidator validator;
  ProjectService ps;
  DivisionService ds;
  
  @Before
  public void setup() {
    pd = new ProjectDivision();
    errors = new BeanPropertyBindingResult(pd, "");
    validator = new ProjectDivisionValidator();
    ps = mock(ProjectService.class);
    ds = mock(DivisionService.class);
    ValidationUtil validationUtil = new ValidationUtil();
    ReflectionTestUtils.setField(validationUtil, "projectService", ps);
    ReflectionTestUtils.setField(validationUtil, "divisionService", ds);
    ReflectionTestUtils.setField(validator, "validationUtil", validationUtil);
  }
  
  @Test
  public void testSuccess() {
    pd.setDivisionId(1);
    pd.setProjectId(1);
    when(ps.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new Project());
    when(ds.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new Division());
    validator.validate(pd, errors);
    assertEquals(0, errors.getFieldErrorCount());
  }

  @Test
  public void testEmpty() {
    validator.validate(pd, errors);
    assertEquals(2, errors.getFieldErrorCount());
  }

  @Test
  public void testInvalidProjectId() {
    pd.setDivisionId(1);
    when(ps.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(null);
    when(ds.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new Division());
    validator.validate(pd, errors);
    assertEquals(1, errors.getFieldErrorCount());
    assertNotNull(errors.getFieldError("projectId"));    
  }

  @Test
  public void testInvalidDivisionId() {
    pd.setProjectId(1);
    when(ps.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(new Project());
    when(ds.findOne(anyInt(), anyMapOf(String.class, Integer.class))).thenReturn(null);
    validator.validate(pd, errors);
    assertEquals(1, errors.getFieldErrorCount());
    assertNotNull(errors.getFieldError("divisionId"));    
  }

}
