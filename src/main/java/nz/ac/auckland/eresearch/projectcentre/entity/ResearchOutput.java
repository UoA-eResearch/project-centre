package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "research_output")
public class ResearchOutput implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer personId;
  @NotNull
  private Integer projectId;
  @NotNull
  private Integer typeId;
  private LocalDate date;
  private String description;
  private String uri;
  private String doi;


  public ResearchOutput() {
  }

  public ResearchOutput(LocalDate date, String description, String doi,
                        String uri, byte pending, Integer personId, Integer projectId,
                        Integer typeId) {
    super();
    this.date = date;
    this.description = description;
    this.uri = uri;
    this.doi = doi;
    this.personId = personId;
    this.projectId = projectId;
    this.typeId = typeId;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDoi() {
    return this.doi;
  }

  public void setDoi(String doi) {
    this.doi = doi;
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
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

  public Integer getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }


}
