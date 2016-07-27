package nz.ac.auckland.eresearch.projectcentre.types.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "identity", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class Identity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name="person_id", nullable=true)
  private Integer personId;
  @Column(name="username", nullable=false)
  @Size(min = 1)
  private String username;
  private Timestamp created;
  private Timestamp expires;
  private String token = "";

  public Identity() {

  }

  public Identity(int personId, String username) {
    this.personId = personId;
    this.username = username;
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
