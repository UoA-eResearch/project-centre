package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;


public class ProjectPropertyPut {

  @ApiModelProperty(value = "facility id", required = true)
  private String facility;
  @ApiModelProperty(value = "property name", required = true)
  private String propname;
  @ApiModelProperty(value = "property value", required = true)
  private String propvalue;

  public String getFacility() {
    return facility;
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public String getPropname() {
    return propname;
  }

  public void setPropname(String propname) {
    this.propname = propname;
  }

  public String getPropvalue() {
    return propvalue;
  }

  public void setPropvalue(String propvalue) {
    this.propvalue = propvalue;
  }

}
