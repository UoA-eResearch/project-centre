package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateDeserializer;
import nz.ac.auckland.eresearch.projectcentre.util.json.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PersonPut {

  @ApiModelProperty(value = "person status", required = true)
  private String status;
  @ApiModelProperty(value = "person full name", required = true)
  private String fullName;
  @ApiModelProperty(value = "person phone", required = true)
  private String phone;
  @ApiModelProperty(value = "person e-mail", required = true)
  private String email;
  @ApiModelProperty(value = "last day, if person leaves the organization", required = false)
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate endDate;
  @ApiModelProperty(value = "notes about the person", required = false)
  private String notes;
  @ApiModelProperty(value = "url to picture of person", required = false)
  private String pictureUrl;
  @ApiModelProperty(value = "preferred name", required = false)
  private String preferredName;
  @ApiModelProperty(value = "Date when person profile is created", required = true)
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate startDate;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getPreferredName() {
    return preferredName;
  }

  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

}
