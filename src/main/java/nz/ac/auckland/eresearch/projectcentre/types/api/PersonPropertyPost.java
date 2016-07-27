package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class PersonPropertyPost extends PersonPropertyPut {

  @ApiModelProperty(value = "person id", required = true)
  private Integer personId;

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

}
