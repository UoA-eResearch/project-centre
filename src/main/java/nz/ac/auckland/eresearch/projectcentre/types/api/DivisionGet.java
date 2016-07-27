package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class DivisionGet {

  @ApiModelProperty(value = "division id", required = true)
  private Integer id;
  @ApiModelProperty(value = "division name", required = true)
  private String name;
  @ApiModelProperty(value = "division code", required = true)
  private String code;
  @ApiModelProperty(value = "parent division, if applicable", required = true)
  private DivisionGet parent;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public DivisionGet getParent() {
    return parent;
  }

  public void setParent(DivisionGet parent) {
    this.parent = parent;
  }


}
