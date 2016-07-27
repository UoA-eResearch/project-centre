package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

@Entity
@Table(name = "person_project")
public class ProjectMember implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String notes;
  @Column(name="person_id", nullable=false)
  private Integer personId;
  @Column(name="person_role_id", nullable=false)
  private Integer personRoleId;
  @Column(name="project_id", nullable=false)
  private Integer projectId;
  
  public ProjectMember() {
	  
  }
  
  public ProjectMember(String notes, Integer personId, Integer personRoleId,
                       int projectId) {
    super();
    this.notes = notes;
    this.personId = personId;
    this.personRoleId = personRoleId;
    this.projectId = projectId;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getPersonRoleId() {
    return this.personRoleId;
  }

  public void setPersonRoleId(Integer personRoleId) {
    this.personRoleId = personRoleId;
  }

  public Integer getProjectId() {
    return this.projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }
  
}
