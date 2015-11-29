package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "external_reference")
public class ExternalReference implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotNull
  private Integer projectId;
  private String description;
  private Date date;
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
	if (this.date == null) {
		return null;
	} else {
	    return this.date.toLocalDate();		
	}
  }

  public void setDate(LocalDate date) {
    if (date == null) {
      this.date = null;
    } else {
  	  this.date = Date.valueOf(date);    	
    }
  }

}
