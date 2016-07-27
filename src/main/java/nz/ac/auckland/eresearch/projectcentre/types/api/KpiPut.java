package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class KpiPut {

  @NotNull
  @ApiModelProperty(value = "KPI number, like 9 in NESI-9", required = true)
  private Integer number;
  @NotNull
  @ApiModelProperty(value = "Description of how KPI is measured", required = true)
  private String measures;
  @NotNull
  @ApiModelProperty(value = "Title of KPI", required = true)
  private String title;
  @ApiModelProperty(value = "KPI type, like NESI in NESI-9", required = true)
  private String type;

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getMeasures() {
    return measures;
  }

  public void setMeasures(String measures) {
    this.measures = measures;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
