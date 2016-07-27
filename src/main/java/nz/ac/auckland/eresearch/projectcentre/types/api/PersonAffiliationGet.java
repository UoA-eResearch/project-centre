package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class PersonAffiliationGet {

  @ApiModelProperty(value = "affiliation id", required = true)
  private Integer id;
  @ApiModelProperty(value = "name of divisional role", required = true)
  private String divisionalRole;
  @ApiModelProperty(value = "division", required = true)
  private DivisionGet division;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDivisionalRole() {
    return divisionalRole;
  }

  public void setDivisionalRole(String divisionalRole) {
    this.divisionalRole = divisionalRole;
  }

  public DivisionGet getDivision() {
    return division;
  }

  public void setDivision(DivisionGet division) {
    this.division = division;
  }


}
