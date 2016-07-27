package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;

@Entity
@Table(name = "person_affiliation")
public class PersonAffiliation implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="person_id")
  @NotNull
  private Integer personId;
  @Column(name="division_id")
  @NotNull
  private Integer divisionId;
  @Column(name="divisional_role_id")
  @NotNull
  private Integer divisionalRoleId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public Integer getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(Integer divisionId) {
    this.divisionId = divisionId;
  }

  public Integer getDivisionalRoleId() {
    return divisionalRoleId;
  }

  public void setDivisionalRoleId(Integer divisionalRoleId) {
    this.divisionalRoleId = divisionalRoleId;
  }

}
