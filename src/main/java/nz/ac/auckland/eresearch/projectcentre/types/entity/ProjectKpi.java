package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "project_kpi")
public class ProjectKpi implements Serializable, HasId, HasProjectId {
	
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="kpi_id", nullable=false)
  private Integer kpiId;
  @Column(name="kpi_category_id")
  private Integer kpiCategoryId;
  @Column(name="project_id", nullable=false)
  private Integer projectId;
  @Column(name="date_reported", nullable=false)
  private LocalDate dateReported;
  private String notes;
  private Float value;

  public ProjectKpi() {
  }

  public ProjectKpi(Integer kpiCategoryId, LocalDate dateReported, Integer kpiId,
                    String notes, Integer projectId, float value) {
    super();
    this.dateReported = dateReported;
    this.kpiCategoryId = kpiCategoryId;
    this.notes = notes;
    this.projectId = projectId;
    this.value = value;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getKpiCategoryId() {
    return this.kpiCategoryId;
  }

  public void setKpiCategoryId(Integer kpiCategoryId) {
    this.kpiCategoryId = kpiCategoryId;
  }

  public LocalDate getDateReported() {
    return this.dateReported;
  }

  public void setDateReported(LocalDate dateReported) {
    this.dateReported = dateReported;
  }

  public Integer getKpiId() {
    return this.kpiId;
  }

  public void setKpiId(Integer kpiId) {
    this.kpiId = kpiId;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getProjectId() {
    return this.projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public Float getValue() {
    return this.value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

}
