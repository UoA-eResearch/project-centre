package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class PersonAffiliationPost {

  @ApiModelProperty(value = "name of divisional role", required = true)
  private String divisionalRole;
  @ApiModelProperty(value = "division id", required = true)
  private Integer divisionId;

  public String getDivisionalRole() {
    return divisionalRole;
  }

  public void setDivisionalRole(String divisionalRole) {
    this.divisionalRole = divisionalRole;
  }

  public Integer getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(Integer divisionId) {
    this.divisionId = divisionId;
  }

}
