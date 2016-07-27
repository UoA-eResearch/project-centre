package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class KpiCategoryPut {

  @NotNull
  @ApiModelProperty(value = "kpi id", required = true)
  private Integer kpiId;
  @NotNull
  @ApiModelProperty(value = "kpi category name", required = true)
  private String name;

  public Integer getKpiId() {
    return kpiId;
  }

  public void setKpiId(Integer kpiId) {
    this.kpiId = kpiId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
