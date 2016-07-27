package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class KpiGet extends KpiPut {

  @ApiModelProperty(value = "KPI id", required = true)
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
