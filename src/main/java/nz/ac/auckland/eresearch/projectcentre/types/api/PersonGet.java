package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PersonGet extends PersonPut {

  @ApiModelProperty(value = "person id", required = true)
  private Integer id;
  @ApiModelProperty(value = "List of affiliations of person", required = true)
  private List<PersonAffiliationGet> affiliations;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<PersonAffiliationGet> getAffiliations() {
    return affiliations;
  }

  public void setAffiliations(List<PersonAffiliationGet> affiliations) {
    this.affiliations = affiliations;
  }

}
