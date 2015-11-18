package nz.ac.auckland.eresearch.projectcentre.entity;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project_properties")
public class ProjectProperty implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer facilityId;
  @NotNull
  private Integer projectId;
  @NotNull
  @Size(min = 1)
  private String propname;
  private String propvalue;
  private Timestamp timestamp;

  public ProjectProperty() {
  }

  public ProjectProperty(Integer facilityId, Integer projectId,
                         String propname, String propvalue, Timestamp timestamp) {
    super();
    this.facilityId = facilityId;
    this.projectId = projectId;
    this.propname = propname;
    this.propvalue = propvalue;
    this.timestamp = timestamp;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getFacilityId() {
    return this.facilityId;
  }

  public void setFacilityId(Integer facilityId) {
    this.facilityId = facilityId;
  }

  public Integer getProjectId() {
    return this.projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public String getPropname() {
    return this.propname;
  }

  public void setPropname(String propname) {
    this.propname = propname;
  }

  public String getPropvalue() {
    return this.propvalue;
  }

  public void setPropvalue(String propvalue) {
    this.propvalue = propvalue;
  }

  public Timestamp getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

}
