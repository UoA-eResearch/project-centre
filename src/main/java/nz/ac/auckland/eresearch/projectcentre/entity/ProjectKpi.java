package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "project_kpi")
public class ProjectKpi implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;
  @Transient
  String kpiCategory;
  @Transient
  String kpiType;
  @Transient
  Integer kpiNumber;
  @Transient
  String personFullName;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer kpiId;
  private Integer kpiCategoryId;
  private Integer personId;
  private Integer projectId;
  private String date;
  private String notes;
  private Float value;

  public ProjectKpi() {
  }

  public ProjectKpi(Integer kpiCategoryId, String date, Integer kpiId,
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

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
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

  public String getKpiCategory() {
    return kpiCategory;
  }

  public void setKpiCategory(String kpiCategory) {
    this.kpiCategory = kpiCategory;
  }

  public String getPersonFullName() {
    return personFullName;
  }

  public void setPersonFullName(String personFullName) {
    this.personFullName = personFullName;
  }

  public String getKpiType() {
    return kpiType;
  }

  public void setKpiType(String kpiType) {
    this.kpiType = kpiType;
  }

  public Integer getKpiNumber() {
    return kpiNumber;
  }

  public void setKpiNumber(Integer kpiNumber) {
    this.kpiNumber = kpiNumber;
  }

}
