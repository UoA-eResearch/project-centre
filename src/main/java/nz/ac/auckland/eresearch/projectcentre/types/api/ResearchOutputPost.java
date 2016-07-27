package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ResearchOutputPost {

  @ApiModelProperty(value = "date when research output was reported", required = true)
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @NotNull
  private LocalDate dateReported;
  @ApiModelProperty(value = "type of research output", required = true)
  @NotNull
  private String type;
  @ApiModelProperty(value = "citation or description", required = false)
  private String description;
  @ApiModelProperty(value = "external link", required = false)
  private String uri;
  @ApiModelProperty(value = "DOI", required = false)
  private String doi;

  public ResearchOutputPost() {}

  public ResearchOutputPost(LocalDate dateReported, String type, String description,
      String uri, String doi) {
    super();
    this.dateReported = dateReported;
    this.type = type;
    this.description = description;
    this.uri = uri;
    this.doi = doi;
  }

  public LocalDate getDateReported() {
    return dateReported;
  }

  public void setDateReported(LocalDate dateReported) {
    this.dateReported = dateReported;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getDoi() {
    return doi;
  }

  public void setDoi(String doi) {
    this.doi = doi;
  }

}
