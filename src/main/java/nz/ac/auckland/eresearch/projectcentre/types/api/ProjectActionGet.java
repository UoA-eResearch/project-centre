package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class ProjectActionGet extends ProjectActionPost {

  // TODO: replace personName by person
  @ApiModelProperty(value = "action id", required = true)
  private Integer id;
  @ApiModelProperty(value = "name of person who performed the action", required = true)
  private String personName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }


}
