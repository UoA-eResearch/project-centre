package nz.ac.auckland.eresearch.projectcentre.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nz.ac.auckland.eresearch.projectcentre.util.json.DivisionJsonDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.DivisionJsonSerializer;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
@JsonDeserialize(using = DivisionJsonDeserializer.class)
@JsonSerialize(using = DivisionJsonSerializer.class)
public class Division implements IHierarchyElement {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  private Division parent;

  private String name;

  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String code;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;


  // note: this is really only here to automatically populate a pre-processed table that stores
  // all childs of a division, to make it easier to do custom sql queries
  // so, don't use any of the associated getters/setters
  @ElementCollection
  private Set<Integer> childIds = Sets.newHashSet();

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

  public Division getParent() {
    return parent;
  }

  public void setParent(Division parent) {
    this.parent = parent;
  }

  public Set<Integer> getChildIds() {
    return childIds;
  }

  public void setChildIds(Set<Integer> childIds) {
    this.childIds = childIds;
  }
}
