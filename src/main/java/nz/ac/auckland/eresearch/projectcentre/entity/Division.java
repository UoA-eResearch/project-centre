package nz.ac.auckland.eresearch.projectcentre.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nz.ac.auckland.eresearch.projectcentre.listeners.HierarchyListener;
import nz.ac.auckland.eresearch.projectcentre.util.json.DivisionJsonDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.DivisionJsonSerializer;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by markus on 10/11/15. <p> Implemented using the pattern found here:
 * http://novyden.blogspot.co.nz/2008/01/managing-hierarchical-data-tree-in.html
 */
@Entity
@Table(name = "division")
@EntityListeners({HierarchyListener.class})
@JsonDeserialize(using = DivisionJsonDeserializer.class)
@JsonSerialize(using = DivisionJsonSerializer.class)
public class Division implements IHierarchyElement {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_div_id")
  private Division parent;
  @Basic(optional = false)
  @Column(name = "level", nullable = false)
  private Short level;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "top_div_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
  private Division top;

  private String name;

  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String code;

  @NotNull
  private Integer institutionId;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  public Division() {
  }

  public Division(int id) {
    setId(id);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public Integer getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Integer institutionId) {
    this.institutionId = institutionId;
  }


  public Division getParent() {
    return parent;
  }

  public void setParent(Division parent) {
    this.parent = parent;
  }


  public Short getLevel() {
    return level;
  }

  public void setLevel(Short theLevel) {
    level = theLevel;
  }


  public Division getTop() {
    return top;
  }

  public void setTop(IHierarchyElement theTop) {
    top = (Division) theTop;
  }


}
