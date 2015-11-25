package nz.ac.auckland.eresearch.projectcentre.entity;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.json.ProjectJsonDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.ProjectJsonSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project")
@JsonDeserialize(using = ProjectJsonDeserializer.class)
@JsonSerialize(using = ProjectJsonSerializer.class)
public class Project implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ElementCollection
  private List<Integer> divisionIds = Lists.newArrayList();
  @NotNull
  private Integer statusId;
  @NotNull
  private Integer typeId;
  @Size(min = 500, max = 2500)
  private String description;
  private LocalDate endDate;
  @NotNull
  private String title;
  @NotNull // probably good to force that
  private LocalDate nextReviewDate;
  private String notes;
  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String code;
  private String requirements;
  @NotNull
  private LocalDate startDate;
  private String todo;

  public Project() {
  }

  public Project(String code, String description,
                 LocalDate endDate,
                 LocalDate nextReviewDate, String notes, String requirements,
                 LocalDate startDate, Integer statusId, String title, Integer typeId, String todo) {
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


  public LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getNextReviewDate() {
    return this.nextReviewDate;
  }

  public void setNextReviewDate(LocalDate nextReviewDate) {
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

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(LocalDate startDate) {
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


}
 