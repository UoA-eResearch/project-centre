package nz.ac.auckland.eresearch.projectcentre.types.api;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class ProjectMemberPost extends ProjectMemberPut {

  @NotNull
  @ApiModelProperty(value = "person id", required = true)
  private Integer personId;

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

}
