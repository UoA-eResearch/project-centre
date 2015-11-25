package nz.ac.auckland.eresearch.projectcentre.entity;


import nz.ac.auckland.eresearch.projectcentre.util.HasId;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "divisional_role")
public class DivisionalRole implements Serializable, HasId {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(unique = true)
  @NotNull
  @Size(min = 1)
  private String name;

  public DivisionalRole() {
  }

  public DivisionalRole(String name) {
    super();
    this.name = name;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
