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
@Table(name = "person_project")
public class PersonProject implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String notes;
  private Integer personId;
  private Integer personRoleId;
  private Integer projectId;

  /* To make life easier for clients who would need to resolve projects and persons anyway */
  @Transient
  private Person person;
  @Transient
  private Project project;
  @Transient
  private String personRole;

  public PersonProject() {
  }

  public PersonProject(String notes, Integer personId, Integer personRoleId,
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

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getPersonRole() {
    return personRole;
  }

  public void setPersonRole(String personRole) {
    this.personRole = personRole;
  }

}
