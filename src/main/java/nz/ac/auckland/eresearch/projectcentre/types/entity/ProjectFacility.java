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
@Table(name = "project_facility")
public class ProjectFacility implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="facility_id", nullable=false)
  private Integer facilityId;
  @Column(name="project_id", nullable=false)
  private Integer projectId;

  public ProjectFacility() {
  }

  public ProjectFacility(Integer facilityId, Integer projectId) {
    super();
    this.facilityId = facilityId;
    this.projectId = projectId;
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

}
