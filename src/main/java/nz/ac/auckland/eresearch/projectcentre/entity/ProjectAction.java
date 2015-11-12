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
@Table(name = "project_action")
public class ProjectAction implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;
  @Transient
  String actionType;
  @Transient
  String personFullName;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer actionTypeId;
  private String date;
  private String notes;
  private Integer personId;
  private Integer projectId;

  public ProjectAction() {
  }

  public ProjectAction(Integer actionTypeId, String date, String notes,
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

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
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

  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public String getPersonFullName() {
    return personFullName;
  }

  public void setPersonFullName(String personFullName) {
    this.personFullName = personFullName;
  }

}
