package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;


public class ServiceOwnerPut {

  @ApiModelProperty(value = "service id", required = true)
  private Integer serviceId;
  @ApiModelProperty(value = "ldap group that contains list of service owners", required = true)
  private String ldapGroup;

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
