package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class PersonPropertyGet extends PersonPropertyPut {

  @ApiModelProperty(value = "person property id", required = true)
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


}
