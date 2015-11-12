package nz.ac.auckland.eresearch.projectcentre.util.permissions;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.auth.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;

import java.lang.annotation.Annotation;

import javax.persistence.Table;

public class EditProjectPermission implements Permission {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  private String getOriginalProjectIdQuery = "SELECT _FIELD_ FROM _TABLE_ WHERE id = ?";
  private String isOnProjectQuery = "SELECT DISTINCT projectId FROM person_project WHERE projectId = ? AND personId = ?";

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    boolean isAllowed = false;
    try {
      Integer entityId = ((HasId) targetDomainObject).getId();
      Integer newProjectId = ((HasProjectId) targetDomainObject).getProjectId();
      Integer personId = ((UserInfo) authentication.getPrincipal()).getId();
      String query = createGetOriginalProjectIdQuery(targetDomainObject);
      Integer oldProjectId = jdbcTemplate.queryForObject(query, Integer.class, entityId);
      jdbcTemplate.queryForObject(isOnProjectQuery, Integer.class, oldProjectId, personId);
      if (oldProjectId == newProjectId) {
        isAllowed = true;
      } else {
        jdbcTemplate.queryForObject(isOnProjectQuery, Integer.class, newProjectId, personId);
        isAllowed = true;
      }
    } catch (IncorrectResultSizeDataAccessException e) {
      System.err.println("Edit operation not authorized on original or new project");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return isAllowed;
  }

  private String createGetOriginalProjectIdQuery(Object targetDomainObject) throws Exception {
    String field = null;
    String table = this.getTableAnnotationName(targetDomainObject);
    field = table.equals("project") ? "id" : "projectId";
    return this.getOriginalProjectIdQuery.replace("_TABLE_", table).replace("_FIELD_", field);
  }

  private String getTableAnnotationName(Object targetDomainObject) throws Exception {
    Table[] annotations = targetDomainObject.getClass().getAnnotationsByType(Table.class);
    if (annotations == null || annotations.length != 1) {
      throw new RuntimeException("Can't derive table name from targetDomainObjects' " +
              "Table annotation. targetDomainObject=" + targetDomainObject);
    }
    Annotation a = annotations[0];
    String value = (String) a.annotationType().getMethod("name").invoke(a);
    return value;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

}
