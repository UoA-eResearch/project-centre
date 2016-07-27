package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

public class DivisionPut {

  @ApiModelProperty(value = "division name", required = true)
  private String name;
  @ApiModelProperty(value = "division code", required = true)
  private String code;
  @ApiModelProperty(value = "parent division id, if applicable", required = false)
  private Integer parentId;

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

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }


}
