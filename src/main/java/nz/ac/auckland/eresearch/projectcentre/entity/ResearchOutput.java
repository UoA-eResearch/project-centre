package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
  @NotNull
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate date;
  private String description;
  private String uri;
  private String doi;
  @Transient
  private String personFullName;
  @Transient
  private String typeName;
  @Transient
  private String projectCode;


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

	public String getPersonFullName() {
		return personFullName;
	}

	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
