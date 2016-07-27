package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class ProjectDivisionGet {

  @ApiModelProperty(value = "project division id", required = true)
  private Integer id;
  @ApiModelProperty(value = "division", required = true)
  private DivisionGet division;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DivisionGet getDivision() {
    return division;
  }

  public void setDivision(DivisionGet division) {
    this.division = division;
  }

}
