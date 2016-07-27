package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;

@Entity
@Table(name = "kpi", uniqueConstraints =
@UniqueConstraint(columnNames = {"number", "type"}))
public class Kpi implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotNull
  private Integer number;
  @NotNull
  private String measures;
  @NotNull
  private String title;
  private String type;

  public Kpi() {
  }

  public Kpi(Integer number, String measures, String title, String type) {
    super();
    this.number = number;
    this.measures = measures;
    this.title = title;
    this.type = type;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getMeasures() {
    return this.measures;
  }

  public void setMeasures(String measures) {
    this.measures = measures;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
