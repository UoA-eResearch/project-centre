package nz.ac.auckland.eresearch.projectcentre.entity;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "project")
public class Project implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ElementCollection
  private List<Integer> divisionIds = Lists.newArrayList();
  private Integer statusId;
  private Integer typeId;
  private String description;
  private String endDate;
  private String title;
  private String nextReviewDate;
  private String notes;
  private String code;
  private String requirements;
  private String startDate;
  private String todo;

  /* To make life easier for clients who would need to resolve ids anyway */
  @Transient
  private List<String> divisions;
  @Transient
  private List<String> institutions;
  @Transient
  private String status;
  @Transient
  private String type;

  public Project() {
  }

  public Project(String code, String description,
                 String endDate,
                 String nextReviewDate, String notes, String requirements,
                 String startDate, Integer statusId, String title, Integer typeId, String todo) {
    super();
    this.description = description;
    this.endDate = endDate;
    this.title = title;
    this.nextReviewDate = nextReviewDate;
    this.notes = notes;
    this.code = code;
    this.typeId = typeId;
    this.requirements = requirements;
    this.startDate = startDate;
    this.statusId = statusId;
    this.todo = todo;
  }

  public List<Integer> getDivisionIds() {
    return divisionIds;
  }

  public void setDivisionIds(List<Integer> divisionIds) {
    this.divisionIds = divisionIds;
  }

  @JsonIgnore
  public Integer getProjectId() {
    return this.id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getEndDate() {
    return this.endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNextReviewDate() {
    return this.nextReviewDate;
  }

  public void setNextReviewDate(String nextReviewDate) {
    this.nextReviewDate = nextReviewDate;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getRequirements() {
    return this.requirements;
  }

  public void setRequirements(String requirements) {
    this.requirements = requirements;
  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Integer getStatusId() {
    return this.statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getTodo() {
    return this.todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }


  public List<String> getDivision() {
    return divisions;
  }

  public void setDivisions(List<String> divisions) {
    this.divisions = divisions;
  }

  public List<String> getInstitutions() {
    return institutions;
  }

  public void setInstitutions(List<String> institutions) {
    this.institutions = institutions;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
 