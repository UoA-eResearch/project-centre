package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PersonPost extends PersonPut {

  @ApiModelProperty(value = "List of affiliations of person.", required = false)
  private List<PersonAffiliationPost> affiliations;

  public List<PersonAffiliationPost> getAffiliations() {
    return affiliations;
  }

  public void setAffiliations(List<PersonAffiliationPost> affiliations) {
    this.affiliations = affiliations;
  }
  
}
