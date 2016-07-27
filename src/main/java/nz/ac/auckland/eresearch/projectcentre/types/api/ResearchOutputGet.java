package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class ResearchOutputGet extends ResearchOutputPost{

  @ApiModelProperty(value = "project research output id", required = true)
  private Integer id;

  public ResearchOutputGet() {}

  public ResearchOutputGet(Integer id, LocalDate dateReported, String type,
      String description, String uri, String doi) {
    super();
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
