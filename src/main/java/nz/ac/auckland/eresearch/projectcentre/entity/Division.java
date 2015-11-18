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
import javax.persistence.Transient;

/**
 * Created by markus on 10/11/15. <p> Implemented using the pattern found here:
 * http://novyden.blogspot.co.nz/2008/01/managing-hierarchical-data-tree-in.html
 */
@Entity
@Table(name = "division_table")
@EntityListeners({HierarchyListener.class})
@JsonDeserialize(using = DivisionJsonDeserializer.class)
@JsonSerialize(using = DivisionJsonSerializer.class)
public class Division implements IHierarchyElement {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "PARENT_ID")
  private Division parent;
  @Basic(optional = false)
  @Column(name = "LEVEL", nullable = false)
  private Short level;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TOP_ID")
  @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
  private Division top;

  private String name;
  @Column(unique = true)
  private String code;

  private Integer institutionId;

  // parentCode is just a convenience field, parent field itself will always have precedence
  @Transient
  private String parentCode;

  // parentId is just a convenience field, parent field itself will always have precedence
  @Transient
  private Integer parentId;
  // institutionCode is just a convenience field, id will always have precedence
  @Transient
  private String institutionCode;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  public Division() {
  }

  public Division(int id) {
    setId(id);
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getInstitutionCode() {
    return institutionCode;
  }

  public void setInstitutionCode(String institutionCode) {
    this.institutionCode = institutionCode;
  }

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
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
