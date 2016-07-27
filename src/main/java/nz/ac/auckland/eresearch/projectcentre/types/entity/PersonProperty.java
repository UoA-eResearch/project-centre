package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;

@Entity
@Table(name = "person_properties")
public class PersonProperty implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="person_id")
  @NotNull
  private Integer personId;
  @NotNull
  @Size(min = 1)
  private String propname;
  private String propvalue;

  public PersonProperty() {
  }

  public PersonProperty(Integer personId, String propname, String propvalue) {
    super();
    this.personId = personId;
    this.propname = propname;
    this.propvalue = propvalue;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPersonId() {
    return this.personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
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

}
