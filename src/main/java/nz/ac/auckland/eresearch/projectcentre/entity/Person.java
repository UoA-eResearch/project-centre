package nz.ac.auckland.eresearch.projectcentre.entity;

import com.google.common.collect.Maps;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nz.ac.auckland.eresearch.projectcentre.util.HasId;
import nz.ac.auckland.eresearch.projectcentre.util.json.PersonJsonDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.PersonJsonSerializer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "person")
@JsonDeserialize(using = PersonJsonDeserializer.class)
@JsonSerialize(using = PersonJsonSerializer.class)
public class Person implements Serializable, HasId {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ElementCollection
  private Map<Integer, Integer> affiliations = Maps.newHashMap();

  private Integer statusId;
  private String email;
  private String endDate;
  private String fullName;
  private String notes;
  private String phone;
  private String pictureUrl;
  private String preferredName;
  private String startDate;
  private Timestamp lastModified;

  /* To make life easier for clients who would need to resolve ids anyway */
  @Transient
  private String status;

  public Person() {
  }

  public Map<Integer, Integer> getAffiliations() {
    return affiliations;
  }

  public void setAffiliations(Map<Integer, Integer> affiliations) {
    this.affiliations = affiliations;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEndDate() {
    return this.endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Timestamp getLastModified() {
    return this.lastModified;
  }

  public void setLastModified(Timestamp lastModified) {
    this.lastModified = lastModified;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPictureUrl() {
    return this.pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getPreferredName() {
    return this.preferredName;
  }

  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Integer getStatusId() {
    return this.statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void addAffiliation(int divId, Integer divRoleId) {
    //TODO: maybe put in a validity check here?
    getAffiliations().put(divId, divRoleId);
  }
}
