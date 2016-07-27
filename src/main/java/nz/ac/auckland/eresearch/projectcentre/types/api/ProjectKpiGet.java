package nz.ac.auckland.eresearch.projectcentre.types.api;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ProjectKpiGet {

  @NotNull
  private Integer id;
  @NotNull
  private KpiGet kpi;
  @NotNull
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate dateReported;
  private String notes;
  private Float value;

}
