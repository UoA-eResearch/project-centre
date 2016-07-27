package nz.ac.auckland.eresearch.projectcentre.types.api;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModelProperty;

public class ServiceSchemaPut {

  @ApiModelProperty(value = "service id", required = true)
  private Integer serviceId;
  @ApiModelProperty(value = "schema version", required = true)
  private String schemaVersion;
  @ApiModelProperty(value = "JSON schema for all instances of the service for the specified version", required = true)
  private JsonNode schema;

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

  public JsonNode getSchema() {
    return schema;
  }

  public void setSchema(JsonNode schema) {
    this.schema = schema;
  }
  
}
