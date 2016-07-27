package nz.ac.auckland.eresearch.projectcentre.types.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "project_division")
public class ProjectDivision {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="project_id")
  @NotNull
  private Integer projectId;
  @Column(name="division_id")
  @NotNull
  private Integer divisionId;

  public ProjectDivision() {}
  
  public ProjectDivision(Integer projectId, Integer divisionId) {
    super();
    this.projectId = projectId;
    this.divisionId = divisionId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public Integer getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(Integer divisionId) {
    this.divisionId = divisionId;
  }

}
