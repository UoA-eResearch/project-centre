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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "external_reference")
public class ExternalReference implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotNull
  private Integer projectId;
  private String reference;
  private String description;
  @NotNull
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate date;
  private String uri;

  public ExternalReference() {
  }

  public ExternalReference(String description, String uri, int projectId) {
    super();
    this.description = description;
    this.uri = uri;
    this.projectId = projectId;
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

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
