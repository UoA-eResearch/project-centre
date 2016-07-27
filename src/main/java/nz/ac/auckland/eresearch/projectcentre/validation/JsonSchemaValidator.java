package nz.ac.auckland.eresearch.projectcentre.validation;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@Component
public class JsonSchemaValidator {

  public final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
  public final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";

  public JsonNode getJsonNode(String jsonText) throws IOException {
    return JsonLoader.fromString(jsonText);
  }

  public JsonNode getJsonNode(File jsonFile) throws IOException {
    return JsonLoader.fromFile(jsonFile);
  }

  public JsonNode getJsonNode(URL url) throws IOException {
    return JsonLoader.fromURL(url);
  }
  
  public JsonNode getJsonNodeFromResource(String resource) throws IOException {
    return JsonLoader.fromResource(resource);
  }

  public JsonSchema getSchemaNode(String schemaText) throws IOException, ProcessingException {
    final JsonNode schemaNode = getJsonNode(schemaText);
    return _getSchemaNode(schemaNode);
  }

  public JsonSchema getSchemaNode(File schemaFile) throws IOException, ProcessingException {
    final JsonNode schemaNode = getJsonNode(schemaFile);
    return _getSchemaNode(schemaNode);
  }

  public JsonSchema getSchemaNode(URL schemaFile) throws IOException, ProcessingException {
    final JsonNode schemaNode = getJsonNode(schemaFile);
    return _getSchemaNode(schemaNode);
  }

  public JsonSchema getSchemaNodeFromResource(String resource) throws IOException,
      ProcessingException {
    final JsonNode schemaNode = getJsonNodeFromResource(resource);
    return _getSchemaNode(schemaNode);
  }

  public void validateJson(JsonSchema jsonSchemaNode, JsonNode jsonNode)
      throws ProcessingException {
    ProcessingReport report = jsonSchemaNode.validate(jsonNode);
    if (!report.isSuccess()) {
      for (ProcessingMessage processingMessage : report) {
        throw new ProcessingException(processingMessage);
      }
    }
  }

  public boolean isJsonValid(JsonSchema jsonSchemaNode, JsonNode jsonNode)
      throws ProcessingException {
    ProcessingReport report = jsonSchemaNode.validate(jsonNode);
    return report.isSuccess();
  }

  public boolean isJsonValid(String schemaText, String jsonText) throws ProcessingException,
      IOException {
    final JsonSchema schemaNode = getSchemaNode(schemaText);
    final JsonNode jsonNode = getJsonNode(jsonText);
    return isJsonValid(schemaNode, jsonNode);
  }

  public boolean isJsonValid(File schemaFile, File jsonFile) throws ProcessingException,
      IOException {
    final JsonSchema schemaNode = getSchemaNode(schemaFile);
    final JsonNode jsonNode = getJsonNode(jsonFile);
    return isJsonValid(schemaNode, jsonNode);
  }

  public boolean isJsonValid(URL schemaURL, URL jsonURL) throws ProcessingException,
      IOException {
    final JsonSchema schemaNode = getSchemaNode(schemaURL);
    final JsonNode jsonNode = getJsonNode(jsonURL);
    return isJsonValid(schemaNode, jsonNode);
  }

  public void validateJson(String schemaText, String jsonText) throws IOException,
      ProcessingException {
    final JsonSchema schemaNode = getSchemaNode(schemaText);
    final JsonNode jsonNode = getJsonNode(jsonText);
    validateJson(schemaNode, jsonNode);
  }

  public void validateJson(File schemaFile, File jsonFile) throws IOException,
      ProcessingException {
    final JsonSchema schemaNode = getSchemaNode(schemaFile);
    final JsonNode jsonNode = getJsonNode(jsonFile);
    validateJson(schemaNode, jsonNode);
  }

  public void validateJson(URL schemaDocument, URL jsonDocument) throws IOException,
      ProcessingException {
    final JsonSchema schemaNode = getSchemaNode(schemaDocument);
    final JsonNode jsonNode = getJsonNode(jsonDocument);
    validateJson(schemaNode, jsonNode);
  }

  public void validateJsonResource(String schemaResource, String jsonResource)
      throws IOException, ProcessingException {
    final JsonSchema schemaNode = getSchemaNode(schemaResource);
    final JsonNode jsonNode = getJsonNodeFromResource(jsonResource);
    validateJson(schemaNode, jsonNode);
  }

  private JsonSchema _getSchemaNode(JsonNode jsonNode) throws ProcessingException {
    final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
    if (null == schemaIdentifier) {
      ((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
    }

    final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
    return factory.getJsonSchema(jsonNode);
  }
}
