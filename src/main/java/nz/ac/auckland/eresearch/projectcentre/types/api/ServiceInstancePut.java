package nz.ac.auckland.eresearch.projectcentre.types.api;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.databind.JsonNode;


public class ServiceInstancePut {

  @ApiModelProperty(value = "service name", required = true)
  private String serviceName;
  @ApiModelProperty(value = "schema version", required = false)
  private String schemaVersion;
  @ApiModelProperty(value = "service instance status", required = true)
  private String status;
  @ApiModelProperty(value = "actual service instance. this can an arbitrary JSON object, but it must validate against the requested schema", required = false)
  private JsonNode instance;

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getSchemaVersion() {
    return schemaVersion;
  }

  public void setSchemaVersion(String schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public JsonNode getInstance() {
    return instance;
  }

  public void setInstance(JsonNode instance) {
    this.instance = instance;
  }

}
