package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ProjectMemberPut {

  @ApiModelProperty(value = "role of person on project", required = true)
  @NotNull
  private String role;
  @ApiModelProperty(value = "notes about project member", required = false)
  private String notes;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

}
