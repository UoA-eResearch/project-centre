package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

@Entity
@Table(name = "project_researchoutput")
public class ResearchOutput implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="project_id", nullable=false)
  private Integer projectId;
  @Column(name="type_id", nullable=false)
  private Integer typeId;
  @Column(name="date_reported", nullable=false)
  private LocalDate dateReported;
  private String description;
  private String uri;
  private String doi;

  public ResearchOutput() {
  }

  public ResearchOutput(LocalDate dateReported, String description, String doi,
                        String uri, byte pending, Integer projectId,
                        Integer typeId) {
    super();
    this.dateReported = dateReported;
    this.description = description;
    this.uri = uri;
    this.doi = doi;
    this.projectId = projectId;
    this.typeId = typeId;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getDateReported() {
    return this.dateReported;
  }

  public void setDateReported(LocalDate dateReported) {
    this.dateReported = dateReported;
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
