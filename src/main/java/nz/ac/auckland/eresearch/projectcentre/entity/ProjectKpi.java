package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "project_kpi")
public class ProjectKpi implements Serializable, HasId, HasProjectId {
	
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer kpiId;
  private Integer kpiCategoryId;
  private Integer personId;
  private Integer projectId;
  @NotNull
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate date;
  private String notes;
  private Float value;
  @Transient
  KpiCategory kpiCategory;
  @Transient
  Kpi kpi;
  @Transient
  String personFullName;
  @Transient
  String projectCode;

  public ProjectKpi() {
  }

  public ProjectKpi(Integer kpiCategoryId, LocalDate date, Integer kpiId,
                    String notes, int personId, Integer projectId, float value) {
    super();
    this.date = date;
    this.kpiCategoryId = kpiCategoryId;
    this.notes = notes;
    this.personId = personId;
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

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

  public Integer getPersonId() {
    return this.personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
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

  public String getPersonFullName() {
    return personFullName;
  }

  public void setPersonFullName(String personFullName) {
    this.personFullName = personFullName;
  }

	public KpiCategory getKpiCategory() {
		return kpiCategory;
	}

	public void setKpiCategory(KpiCategory kpiCategory) {
		this.kpiCategory = kpiCategory;
	}

	public Kpi getKpi() {
		return kpi;
	}

	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

}
