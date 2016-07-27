package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;

@Entity
@Table(name = "serviceowner")
public class ServiceOwner implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="service_id", nullable=false)
  private Integer serviceId;
  @Column(name="ldap_group", nullable=false)
  @Size(min = 1, max=100)
  private String ldapGroup;

  public ServiceOwner() {
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

  public String getLdapGroup() {
    return ldapGroup;
  }

  public void setLdapGroup(String ldapGroup) {
    this.ldapGroup = ldapGroup;
  }

}
