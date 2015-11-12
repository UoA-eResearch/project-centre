package nz.ac.auckland.eresearch.projectcentre.util.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserInfo extends User {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private Integer institutionId;
  private Integer divisionId;
  private Integer departmentId;
  private String email;
  private String fullName;

  public UserInfo(String username, String password, boolean enabled,
                  boolean accountNonExpired, boolean credentialsNonExpired,
                  boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Integer getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Integer institutionId) {
    this.institutionId = institutionId;
  }

  public Integer getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(Integer divisionId) {
    this.divisionId = divisionId;
  }

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}