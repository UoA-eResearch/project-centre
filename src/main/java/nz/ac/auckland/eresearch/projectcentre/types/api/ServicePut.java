package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class ServicePut {

  @ApiModelProperty(value = "drupal id of service", required = true)
  private Integer drupalId;
  @ApiModelProperty(value = "name of service", required = true)
  private String name;

  public Integer getDrupalId() {
    return drupalId;
  }

  public void setDrupalId(Integer drupalId) {
    this.drupalId = drupalId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
