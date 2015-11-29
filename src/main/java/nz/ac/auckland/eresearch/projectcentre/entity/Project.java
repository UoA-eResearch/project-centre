package nz.ac.auckland.eresearch.projectcentre.entity;

import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.HasProjectId;
import nz.ac.auckland.eresearch.projectcentre.util.json.ProjectJsonDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.ProjectJsonSerializer;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project")
@JsonDeserialize(using = ProjectJsonDeserializer.class)
@JsonSerialize(using = ProjectJsonSerializer.class)
public class Project implements Serializable, HasId, HasProjectId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ElementCollection
  @Column(name="divisionId")
  @CollectionTable(
    name = "project_division",
    joinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id")
  )
  private List<Integer> divisionIds = Lists.newArrayList();
  @NotNull
  private Integer statusId;
  @NotNull
  private Integer typeId;
  @Size(min = 1, max = 2500)
  private String description;
  private Date endDate;
  @NotNull
  private String title;
  @NotNull // probably good to force that
  private Date nextReviewDate;
  private String notes;
  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String code;
  private String requirements;
  @NotNull
  private Date startDate;
  private String todo;

  public Project() {
  }

  public Project(String code, String description,
                 LocalDate endDate,
                 LocalDate nextReviewDate, String notes, String requirements,
                 LocalDate startDate, Integer statusId, String title, Integer typeId, String todo) {
    super();
    this.description = description;
    this.title = title;
    this.notes = notes;
    this.code = code;
    this.typeId = typeId;
    this.requirements = requirements;
    this.statusId = statusId;
    this.todo = todo;
    if (startDate == null) {
    	this.startDate = null;
    } else {
    	this.startDate = Date.valueOf(startDate);
    }
    if (nextReviewDate == null) {
    	this.nextReviewDate = null;
    } else {
    	this.nextReviewDate = Date.valueOf(nextReviewDate);
    }
    if (endDate == null) {
    	this.endDate = null;
    } else {
    	this.endDate = Date.valueOf(endDate);
    }
  }

  public List<Integer> getDivisionIds() {
    return divisionIds;
  }

  public void setDivisionIds(List<Integer> divisionIds) {
    this.divisionIds = divisionIds;
  }

  @JsonIgnore
  public Integer getProjectId() {
    return this.id;
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

  public LocalDate getEndDate() {
	if (this.endDate == null) {
	  return null;
	} else {
	  return this.endDate.toLocalDate();		
	}
  }

  public void setEndDate(LocalDate endDate) {
	if (endDate == null) {
	  this.endDate = null;
	} else {
	  this.endDate = Date.valueOf(endDate);	  
	}
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getNextReviewDate() {
	if (this.nextReviewDate == null) {
	  return null;
	} else {
	  return this.nextReviewDate.toLocalDate();		
	}
  }

  public void setNextReviewDate(LocalDate nextReviewDate) {
	if (nextReviewDate == null) {
      this.nextReviewDate = null;
	} else {
      this.nextReviewDate = Date.valueOf(nextReviewDate);	  
	}
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getRequirements() {
    return this.requirements;
  }

  public void setRequirements(String requirements) {
    this.requirements = requirements;
  }

  public LocalDate getStartDate() {
	if (this.startDate == null) {
	  return null;
	} else {
	  return this.startDate.toLocalDate();		
	}
  }

  public void setStartDate(LocalDate startDate) {
	if (startDate == null) {
	  this.startDate = null;
	} else {
	  this.startDate = Date.valueOf(startDate);	  
	}
  }

  public Integer getStatusId() {
    return this.statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getTodo() {
    return this.todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }


}
 
