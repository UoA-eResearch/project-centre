package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


public class ProjectGet extends ProjectPut {

  @ApiModelProperty(value = "project id", required = true)
  private Integer id;
  @ApiModelProperty(value = "list of division ids", required = true)
  private List<ProjectDivisionGet> divisions;

  public ProjectGet() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<ProjectDivisionGet> getDivisions() {
    return divisions;
  }

  public void setDivisions(List<ProjectDivisionGet> divisions) {
    this.divisions = divisions;
  }

}
