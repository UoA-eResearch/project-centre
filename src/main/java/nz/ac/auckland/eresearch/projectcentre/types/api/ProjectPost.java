package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ProjectPost extends ProjectPut {

  @ApiModelProperty(value = "list of division ids", required = false)
  private List<Integer> divisionIds;

  public ProjectPost() {}

  public List<Integer> getDivisionIds() {
    return divisionIds;
  }

  public void setDivisionIds(List<Integer> divisionIds) {
    this.divisionIds = divisionIds;
  }

}
