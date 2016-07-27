package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;

@Entity
@Table(name = "serviceschema")
public class ServiceSchema implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="service_id", nullable=false)
  private Integer serviceId;
  @Column(name="schema_version", nullable=false)
  private String schemaVersion;
  @Column(name="schema_string", nullable=false)
  private String schemaString;

  public ServiceSchema() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public String getSchemaVersion() {
    return schemaVersion;
  }

  public void setSchemaVersion(String schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  public String getSchemaString() {
    return schemaString;
  }

  public void setSchemaString(String schemaString) {
    this.schemaString = schemaString;
  }

}
