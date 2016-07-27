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

@Entity
@Table(name = "project_action")
public class ProjectAction implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="action_type_id")
  @NotNull
  private Integer actionTypeId;
  @NotNull
  private LocalDate date;
  private String notes;
  @Column(name="person_id")
  @NotNull
  private Integer personId;
  @Column(name="project_id")
  @NotNull
  private Integer projectId;

  public ProjectAction() {
  }

  public ProjectAction(Integer actionTypeId, LocalDate date, String notes,
                       int personId, Integer projectId) {
    super();
    this.actionTypeId = actionTypeId;
    this.date = date;
    this.notes = notes;
    this.personId = personId;
    this.projectId = projectId;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getActionTypeId() {
    return this.actionTypeId;
  }

  public void setActionTypeId(Integer actionTypeId) {
    this.actionTypeId = actionTypeId;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

}
