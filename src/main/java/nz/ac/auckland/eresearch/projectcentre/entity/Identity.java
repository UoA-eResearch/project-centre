package nz.ac.auckland.eresearch.projectcentre.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by markus on 9/11/15.
 */
@Entity
@Table(name = "identity")
public class Identity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotNull
  private Integer personId;
  @NotNull
  @Size(min = 1)
  private String username;
  @NotNull
  @Size(min = 1)
  private String service;

  private Timestamp created;
  private Timestamp expires;

  private String token = "";

  public Identity() {

  }

  public Identity(int personId, String username, String service) {
    this.personId = personId;
    this.username = username;
    this.service = service;
    this.created = Timestamp.valueOf(LocalDateTime.now());
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public LocalDateTime getCreated() {
    return created.toLocalDateTime();
  }

  public void setCreated(LocalDateTime created) {
    this.created = Timestamp.valueOf(created);
  }

  public LocalDateTime getExpires() {
    return expires.toLocalDateTime();
  }

  public void setExpires(LocalDateTime expires) {
    this.expires = Timestamp.valueOf(expires);
  }
}
