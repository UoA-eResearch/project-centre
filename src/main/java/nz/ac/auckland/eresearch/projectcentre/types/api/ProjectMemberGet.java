package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class ProjectMemberGet {

  @ApiModelProperty(value = "membership id", required = true)
  private Integer id;
  @ApiModelProperty(value = "role of person on project", required = true)
  private String role;
  @ApiModelProperty(value = "notes about project member", required = false)
  private String notes;
  @ApiModelProperty(value = "person on project", required = false)
  private PersonGet person;
  @ApiModelProperty(value = "project", required = false)
  private ProjectGet project;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public PersonGet getPerson() {
    return person;
  }

  public void setPerson(PersonGet person) {
    this.person = person;
  }

  public ProjectGet getProject() {
    return project;
  }

  public void setProject(ProjectGet project) {
    this.project = project;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }


}
