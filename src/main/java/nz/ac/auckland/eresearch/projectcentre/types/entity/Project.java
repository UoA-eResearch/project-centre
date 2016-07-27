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
import javax.validation.constraints.Size;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

@Entity
@Table(name = "project")
public class Project implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="status_id")
  @NotNull
  private Integer statusId;
  @Column(name="type_id")
  @NotNull
  private Integer typeId;
  @Size(min = 1, max = 2500)
  private String description;
  @Column(name="end_date")
  private LocalDate endDate;
  @NotNull
  private String title;
  @Column(name="next_review_date")
  private LocalDate nextReviewDate;
  private String notes;
  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String code;
  private String requirements;
  @Column(name="start_date")
  @NotNull
  private LocalDate startDate;
  private String todo;

  public Project() {
  }
  
  public Project(Integer id, Integer statusId, Integer typeId, String description,
      LocalDate endDate, String title, LocalDate nextReviewDate, String notes, String code,
      String requirements, LocalDate startDate, String todo) {
    super();
    this.id = id;
    this.statusId = statusId;
    this.typeId = typeId;
    this.description = description;
    this.endDate = endDate;
    this.title = title;
    this.nextReviewDate = nextReviewDate;
    this.notes = notes;
    this.code = code;
    this.requirements = requirements;
    this.startDate = startDate;
    this.todo = todo;
  }

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
 
