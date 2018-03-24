package nz.ac.auckland.eresearch.projectcentre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceOwnerService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceSchemaService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceOwnerPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServicePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServicePut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceSchemaPut;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ServiceConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ServiceOwnerConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ServiceSchemaConverter;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Service;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceOwner;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;
import nz.ac.auckland.eresearch.projectcentre.util.ControllerExceptionHandler;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.validation.ServiceOwnerValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ServiceSchemaValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ServiceValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"Service"})
@RequestMapping(value = "/api/service")
public class ServiceController extends ControllerExceptionHandler {
  
  private BaseController<Service,ServiceGet,ServicePut,ServicePost> serviceController;
  private BaseController<ServiceOwner,ServiceOwnerGet,ServiceOwnerPut,ServiceOwnerPost> serviceOwnerController;
  private BaseController<ServiceSchema,ServiceSchemaGet,ServiceSchemaPut,ServiceSchemaPost> serviceSchemaController;
  
  @Autowired
  public ServiceController(
      ServiceService serviceService, 
      ServiceOwnerService serviceOwnerService,
      ServiceSchemaService serviceSchemaService,
      ServiceValidator serviceValidator,
      ServiceOwnerValidator serviceOwnerValidator,
      ServiceSchemaValidator serviceSchemaValidator,
      ServiceConverter serviceConverter,
      ServiceOwnerConverter serviceOwnerConverter,
      ServiceSchemaConverter serviceSchemaConverter) {
    this.serviceController = new BaseController(serviceService, serviceConverter, serviceValidator);
    this.serviceOwnerController = new BaseController(serviceOwnerService, serviceOwnerConverter, serviceOwnerValidator);
    this.serviceSchemaController = new BaseController(serviceSchemaService, serviceSchemaConverter, serviceSchemaValidator);
  }

  //// service

  @ApiOperation(value = "get list of services", nickname="app.get_services")
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ServiceGet>> serviceGetAll() throws Exception {
    return serviceController.getAll(null);
  }

//  @ApiOperation(value = "create new service", nickname="app.create_services")
//  @RequestMapping(method = RequestMethod.POST)
//  public @ResponseBody ResponseEntity<Void> servicePost(@RequestBody ServicePost newService,
//      HttpServletRequest request) throws Exception {
//    return serviceController.create(newService, null, request);
//  }
//
//  @ApiOperation(value = "get existing service", nickname="app.get_service")
//  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//  public @ResponseBody ResponseEntity<ServiceGet> serviceGet(@PathVariable Integer id) throws Exception {
//    return serviceController.get(new MapUtil("id", id).create());
//  }
//
//  @ApiOperation(value = "patch existing service. same fields like in PUT can be updated", nickname="app.patch_service")
//  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
//  public @ResponseBody ResponseEntity<Void> servicePatch(@PathVariable Integer id,
//      @RequestBody ServicePut params) throws Exception {
//    return serviceController.patch(new MapUtil("id", id).create(), params);
//  }
//
//  @ApiOperation(value = "update existing service", nickname="app.update_service")
//  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//  public @ResponseBody ResponseEntity<Void> servicePut(@PathVariable Integer id,
//      @RequestBody ServicePut serviceUpdate) throws Exception {
//    return serviceController.put(new MapUtil("id", id).create(), serviceUpdate);
//  }
//
//  @ApiOperation(value = "delete existing service", nickname="app.delete_service")
//  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//  public @ResponseBody ResponseEntity<Void> serviceDelete(@PathVariable Integer id) throws Exception {
//    return serviceController.delete(new MapUtil("id", id).create());
//  }
  
  //// service owner
  
//  @ApiOperation(value = "get list of service owners", nickname="app.get_service_owners")
//  @RequestMapping(value = "/owner", method = RequestMethod.GET)
//  public @ResponseBody ResponseEntity<List<ServiceOwnerGet>> serviceOwnerGetAll() throws Exception {
//    return serviceOwnerController.getAll(null);
//  }
//
//  @ApiOperation(value = "create new service owner", nickname="app.create_service_owner")
//  @RequestMapping(value = "/owner", method = RequestMethod.POST)
//  public @ResponseBody ResponseEntity<Void> serviceOwnerPost(@RequestBody ServiceOwnerPost newServiceOwner,
//      HttpServletRequest request) throws Exception {
//    return serviceOwnerController.create(newServiceOwner, null, request);
//  }
//  
//  @ApiOperation(value = "get existing service owner", nickname="app.get_service_owner")
//  @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
//  public @ResponseBody ResponseEntity<ServiceOwnerGet> serviceOwnerGet(@PathVariable Integer id) throws Exception {
//    return serviceOwnerController.get(new MapUtil("id", id).create());
//  }
//
//  @ApiOperation(value = "patch existing service owner. same fields like in PUT can be updated", nickname="app.update_service_owner")
//  @RequestMapping(value = "/owner/{id}", method = RequestMethod.PATCH)
//  public @ResponseBody ResponseEntity<Void> serviceOwnerPatch(@PathVariable Integer id,
//      @RequestBody ServiceOwnerPut params) throws Exception {
//    return serviceOwnerController.patch(new MapUtil("id", id).create(), params);
//  }
//
//  @ApiOperation(value = "update existing service owner", nickname="app.update_service_owner")
//  @RequestMapping(value = "/owner/{id}", method = RequestMethod.PUT)
//  public @ResponseBody ResponseEntity<Void> serviceOwnerPut(@PathVariable Integer id,
//      @RequestBody ServiceOwnerPut serviceOwnerUpdate) throws Exception {
//    return serviceOwnerController.put(new MapUtil("id", id).create(), serviceOwnerUpdate);
//  }
//
//  @ApiOperation(value = "delete existing service owner", nickname="app.delete_service_owner")
//  @RequestMapping(value = "/owner/{id}", method = RequestMethod.DELETE)
//  public @ResponseBody ResponseEntity<Void> serviceOwnerDelete(@PathVariable Integer id) throws Exception {
//    return serviceOwnerController.delete(new MapUtil("id", id).create());
//  }
  
  //// service schema
  
  @ApiOperation(value = "get list of service schemas", nickname="app.get_service_schemas")
  @RequestMapping(value = "/schema", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ServiceSchemaGet>> serviceSchemaGetAll() throws Exception {
    return serviceSchemaController.getAll(null);
  }

  @ApiOperation(value = "create new service schema", nickname="app.create_service_schema")
  @RequestMapping(value = "/schema", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> serviceSchemaPost(@RequestBody ServiceSchemaPost newServiceSchema,
      HttpServletRequest request) throws Exception {
    return serviceSchemaController.create(newServiceSchema, null, request);
  }
  
  @ApiOperation(value = "get existing service schema", nickname="app.get_service_schema")
  @RequestMapping(value = "/schema/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ServiceSchemaGet> serviceSchemaGet(@PathVariable Integer id) throws Exception {
    return serviceSchemaController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "patch existing service schema. same fields like in PUT can be updated", nickname="app.patch_service_schema")
  @RequestMapping(value = "/schema/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> serviceSchemaPatch(@PathVariable Integer id,
      @RequestBody ServiceSchemaPut params) throws Exception {
    return serviceSchemaController.patch(new MapUtil("id", id).create(), params);
  }

  @ApiOperation(value = "update existing service schema", nickname="app.update_service_schema")
  @RequestMapping(value = "/schema/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> serviceSchemaPut(@PathVariable Integer id,
      @RequestBody ServiceSchemaPut serviceSchemaUpdate) throws Exception {
    return serviceSchemaController.put(new MapUtil("id", id).create(), serviceSchemaUpdate);
  }

  @ApiOperation(value = "delete existing service schema", nickname="app.delete_service_schema")
  @RequestMapping(value = "/schema/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> serviceSchemaDelete(@PathVariable Integer id) throws Exception {
    return serviceSchemaController.delete(new MapUtil("id", id).create());
  }  
  
}
