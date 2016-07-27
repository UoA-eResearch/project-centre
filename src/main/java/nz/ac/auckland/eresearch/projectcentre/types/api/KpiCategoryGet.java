package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class KpiCategoryGet {

  @NotNull
  @ApiModelProperty(value = "kpi category id", required = true)
  private Integer id;
  @NotNull
  @ApiModelProperty(value = "kpi category name", required = true)
  private String name;
  @NotNull
  @ApiModelProperty(value = "kpi this category can be used with", required = true)
  private KpiGet kpi;

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

  public KpiGet getKpi() {
    return kpi;
  }

  public void setKpi(KpiGet kpi) {
    this.kpi = kpi;
  }


}
