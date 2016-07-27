package nz.ac.auckland.eresearch.projectcentre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionTypeService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectTypeService;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputTypeService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceInstanceStatusService;
import nz.ac.auckland.eresearch.projectcentre.types.IdNameType;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.DivisionPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypeGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.IdNameTypePut;
import nz.ac.auckland.eresearch.projectcentre.types.convert.DivisionConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.IdNameTypeConverter;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.types.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectActionType;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectStatus;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectType;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutputType;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstanceStatus;
import nz.ac.auckland.eresearch.projectcentre.util.ControllerExceptionHandler;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.validation.DivisionValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.IdNameTypeValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"Utilities"}, description="Miscellaneous utility operations")
@RequestMapping(value = "/api/util")
public class UtilController extends ControllerExceptionHandler {

  private BaseController<Division, DivisionGet, DivisionPut, DivisionPost> divisionController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> facilityController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> personRoleController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> personStatusController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> projectStatusController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> projectTypeController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> divisionalRoleController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> projectActionTypeController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> researchOutputTypeController;
  private BaseController<IdNameType, IdNameTypeGet, IdNameTypePut, IdNameTypePost> serviceInstanceStatusController;

  @Autowired
  public UtilController(ValidationUtil validationUtil, 
      DivisionService divisionService,
      FacilityService facilityService,
      PersonRoleService personRoleService,
      PersonStatusService personStatusService,
      ProjectStatusService projectStatusService,
      ProjectTypeService projectTypeService,
      ResearchOutputTypeService researchOutputTypeService,
      DivisionalRoleService divisionalRoleService,
      ProjectActionTypeService projectActionTypeService,
      ServiceInstanceStatusService serviceInstanceStatusService,
      DivisionConverter divisionConverter,
      DivisionValidator divisionValidator) {
    
    this.facilityController = new BaseController(facilityService,
        new IdNameTypeConverter<Facility>(Facility.class),
        new IdNameTypeValidator<Facility>(validationUtil, facilityService));
    this.divisionalRoleController = new BaseController(divisionalRoleService,
        new IdNameTypeConverter<DivisionalRole>(DivisionalRole.class),
        new IdNameTypeValidator<DivisionalRole>(validationUtil, divisionalRoleService));
    this.personRoleController = new BaseController(personRoleService,
        new IdNameTypeConverter<PersonRole>(PersonRole.class),
        new IdNameTypeValidator<PersonRole>(validationUtil, personRoleService));
    this.personStatusController = new BaseController(personStatusService,
        new IdNameTypeConverter<PersonStatus>(PersonStatus.class),
        new IdNameTypeValidator<PersonStatus>(validationUtil, personStatusService));
    this.projectStatusController = new BaseController(projectStatusService,
        new IdNameTypeConverter<ProjectStatus>(ProjectStatus.class),
        new IdNameTypeValidator<ProjectStatus>(validationUtil, projectStatusService));
    this.projectTypeController = new BaseController(projectTypeService,
        new IdNameTypeConverter<ProjectType>(ProjectType.class),
        new IdNameTypeValidator<ProjectType>(validationUtil, projectTypeService));
    this.projectActionTypeController = new BaseController(projectActionTypeService,
        new IdNameTypeConverter<ProjectActionType>(ProjectActionType.class),
        new IdNameTypeValidator<ProjectActionType>(validationUtil, projectActionTypeService));
    this.researchOutputTypeController = new BaseController(researchOutputTypeService,
        new IdNameTypeConverter<ResearchOutputType>(ResearchOutputType.class),
        new IdNameTypeValidator<ResearchOutputType>(validationUtil, researchOutputTypeService));
    this.serviceInstanceStatusController = new BaseController(serviceInstanceStatusService,
        new IdNameTypeConverter<ServiceInstanceStatus>(ServiceInstanceStatus.class),
        new IdNameTypeValidator<ServiceInstanceStatus>(validationUtil, serviceInstanceStatusService));
    this.divisionController = new BaseController(divisionService, divisionConverter, divisionValidator);
  }

  //// divisional role

  @ApiOperation(value = "get all divisional roles")
  @RequestMapping(value = "/divisionalrole", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> divRoleGetAll() throws Exception {
    return divisionalRoleController.getAll(null);
  }

  @ApiOperation(value = "create new divisional role")
  @RequestMapping(value = "/divisionalrole", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> divRolePost(
      @RequestBody IdNameTypePost newDivisionalRole, HttpServletRequest request) throws Exception {
    return divisionalRoleController.create(newDivisionalRole, null, request);
  }

  @ApiOperation(value = "get existing divisional role")
  @RequestMapping(value = "/divisionalrole/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> divRoleGet(@PathVariable Integer id) throws Exception {
    return divisionalRoleController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing divisional role")
  @RequestMapping(value = "/divisionalrole/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> divRoleDelete(@PathVariable Integer id) throws Exception {
    return divisionalRoleController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing divisional role")
  @RequestMapping(value = "/divisionalrole/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> divRolePut(@PathVariable Integer id,
      @RequestBody IdNameTypePut divisionalRoleUpdate) throws Exception {
    return divisionalRoleController.put(new MapUtil("id", id).create(), divisionalRoleUpdate);
  }
  
  //// person status
  
  @ApiOperation(value = "get all person status options")
  @RequestMapping(value = "/personstatus", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> personStatusGetAll() throws Exception {
    return personStatusController.getAll(null);
  }

  @ApiOperation(value = "create new person status")
  @RequestMapping(value = "/personstatus", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> personStatusPost (
      @RequestBody IdNameTypePost newPersonStatus, HttpServletRequest request) throws Exception {
    return personStatusController.create(newPersonStatus, null, request);
  }

  @ApiOperation(value = "get existing person status")
  @RequestMapping(value = "/personstatus/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> personStatusGet(@PathVariable Integer id) throws Exception {
    return personStatusController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing person status")
  @RequestMapping(value = "/personstatus/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> personStatusDelete(@PathVariable Integer id) throws Exception {
    return personStatusController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing person status")
  @RequestMapping(value = "/personstatus/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> personStatusPut(@PathVariable Integer id,
      @RequestBody IdNameTypePut personStatusUpdate) throws Exception {
    return personStatusController.put(new MapUtil("id", id).create(), personStatusUpdate);
  }

  //// division
  
  @ApiOperation(value = "get all divisions")
  @RequestMapping(value = "/division", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<DivisionGet>> divisionGetAll() throws Exception {
    return divisionController.getAll(null);
  }

  @ApiOperation(value = "create new division")
  @RequestMapping(value = "/division", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> divisionPost(
      @RequestBody DivisionPost newDivision, HttpServletRequest request) throws Exception {
    // TODO: Add child entry in division_children table
    return divisionController.create(newDivision, null, request);
  }

  @ApiOperation(value = "get existing division")
  @RequestMapping(value = "/division/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<DivisionGet> divisionGet(@PathVariable Integer id) throws Exception {
    return divisionController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing division")
  @RequestMapping(value = "/division/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> divisionDelete(@PathVariable Integer id) throws Exception {
    // TODO: Remove child entries from division_children table
    return divisionController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "patch existing division. same input fields like in PUT can be updated")
  @RequestMapping(value = "/division/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> divisionPatch(@PathVariable Integer id,
      @RequestBody DivisionPut params) throws Exception {
    return divisionController.patch(new MapUtil("id", id).create(), params);
  }

  @ApiOperation(value = "update existing division")
  @RequestMapping(value = "/division/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> divisionPut(@PathVariable Integer id,
      @RequestBody DivisionPut divisionUpdate) throws Exception {
    return divisionController.put(new MapUtil("id", id).create(), divisionUpdate);
  }

  //// facility

  @ApiOperation(value = "get all facilities")
  @RequestMapping(value = "/facility", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> facilityGetAll() throws Exception {
    return facilityController.getAll(null);
  }

  @ApiOperation(value = "create new facility")
  @RequestMapping(value = "/facility", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> facilityPost(
      @RequestBody IdNameTypePost newFacility, HttpServletRequest request) throws Exception {
    return facilityController.create(newFacility, null, request);
  }

  @ApiOperation(value = "get existing facility")
  @RequestMapping(value = "/facility/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> facilityGet(@PathVariable Integer id) throws Exception {
    return facilityController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing facility")
  @RequestMapping(value = "/facility/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> facilityDelete(@PathVariable Integer id) throws Exception {
    return facilityController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing facility")
  @RequestMapping(value = "/facility/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> facilityPut(@PathVariable Integer id,
      @RequestBody IdNameTypePut facilityUpdate) throws Exception {
    return facilityController.put(new MapUtil("id", id).create(), facilityUpdate);
  }

  //// project status
  
  @ApiOperation(value = "get all project status options")
  @RequestMapping(value = "/projectstatus", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> projectStatusGetAll() throws Exception {
    return projectStatusController.getAll(null);
  }

  @ApiOperation(value = "create new project status")
  @RequestMapping(value = "/projectstatus", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> projectStatusPost (
      @RequestBody IdNameTypePost newProjectStatus, HttpServletRequest request) throws Exception {
    return projectStatusController.create(newProjectStatus, null, request);
  }

  @ApiOperation(value = "get existing project status")
  @RequestMapping(value = "/projectstatus/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> projectStatusGet(@PathVariable Integer id) throws Exception {
    return projectStatusController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing project status")
  @RequestMapping(value = "/projectstatus/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> projectStatusDelete(@PathVariable Integer id) throws Exception {
    return projectStatusController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing project status")
  @RequestMapping(value = "/projectstatus/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> projectStatusPut(@PathVariable Integer id,
      @RequestBody IdNameTypePut projectStatusUpdate) throws Exception {
    return projectStatusController.put(new MapUtil("id", id).create(), projectStatusUpdate);
  }

  //// project action type
  
  @ApiOperation(value = "get all project action type options")
  @RequestMapping(value = "/projectactiontype", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> projectActionTypeGetAll() throws Exception {
    return projectActionTypeController.getAll(null);
  }

  @ApiOperation(value = "create new project action type")
  @RequestMapping(value = "/projectactiontype", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> projectActionTypePost (
      @RequestBody IdNameTypePost newProjectActionType, HttpServletRequest request) throws Exception {
    return projectActionTypeController.create(newProjectActionType, null, request);
  }

  @ApiOperation(value = "get existing project action type")
  @RequestMapping(value = "/projectactiontype/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> projectActionTypeGet(@PathVariable Integer id) throws Exception {
    return projectActionTypeController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing project action type")
  @RequestMapping(value = "/projectactiontype/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> projectActionTypeDelete(@PathVariable Integer id) throws Exception {
    return projectActionTypeController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing project action type")
  @RequestMapping(value = "/projectactiontype/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> projectActionTypePut(@PathVariable Integer id,
      @RequestBody IdNameTypePut projectActionTypeUpdate) throws Exception {
    return projectActionTypeController.put(new MapUtil("id", id).create(), projectActionTypeUpdate);
  }

  //// project type
  
  @ApiOperation(value = "get all project type options")
  @RequestMapping(value = "/projecttype", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> projectTypeGetAll() throws Exception {
    return projectTypeController.getAll(null);
  }

  @ApiOperation(value = "create new project type")
  @RequestMapping(value = "/projecttype", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> projectTypePost (
      @RequestBody IdNameTypePost newProjectType, HttpServletRequest request) throws Exception {
    return projectTypeController.create(newProjectType, null, request);
  }

  @ApiOperation(value = "get existing project type")
  @RequestMapping(value = "/projecttype/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> projectTypeGet(@PathVariable Integer id) throws Exception {
    return projectTypeController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing project type")
  @RequestMapping(value = "/projecttype/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> projectTypeDelete(@PathVariable Integer id) throws Exception {
    return projectTypeController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing project type")
  @RequestMapping(value = "/projecttype/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> projectTypePut(@PathVariable Integer id,
      @RequestBody IdNameTypePut projectTypeUpdate) throws Exception {
    return projectTypeController.put(new MapUtil("id", id).create(), projectTypeUpdate);
  }

  //// research output type
  
  @ApiOperation(value = "get all research output type options")
  @RequestMapping(value = "/researchoutputtype", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> researchOutputTypeGetAll() throws Exception {
    return researchOutputTypeController.getAll(null);
  }

  @ApiOperation(value = "create new research output type")
  @RequestMapping(value = "/researchoutputtype", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> researchOutputTypePost (
      @RequestBody IdNameTypePost newResearchOutputType, HttpServletRequest request) throws Exception {
    return researchOutputTypeController.create(newResearchOutputType, null, request);
  }

  @ApiOperation(value = "get existing research output type")
  @RequestMapping(value = "/researchoutputtype/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> researchOutputTypeGet(@PathVariable Integer id) throws Exception {
    return researchOutputTypeController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing research output type")
  @RequestMapping(value = "/researchoutputtype/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> researchOutputTypeDelete(@PathVariable Integer id) throws Exception {
    return researchOutputTypeController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing research output type")
  @RequestMapping(value = "/researchoutputtype/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> researchOutputTypePut(@PathVariable Integer id,
      @RequestBody IdNameTypePut researchOutputTypeUpdate) throws Exception {
    return researchOutputTypeController.put(new MapUtil("id", id).create(), researchOutputTypeUpdate);
  }

  //// person role
  
  @ApiOperation(value = "get all person role options")
  @RequestMapping(value = "/personrole", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> personRoleGetAll() throws Exception {
    return personRoleController.getAll(null);
  }

  @ApiOperation(value = "create new person role")
  @RequestMapping(value = "/personrole", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> personRolePost (
      @RequestBody IdNameTypePost newPersonRole, HttpServletRequest request) throws Exception {
    return personRoleController.create(newPersonRole, null, request);
  }

  @ApiOperation(value = "get existing person role")
  @RequestMapping(value = "/personrole/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> personRoleGet(@PathVariable Integer id) throws Exception {
    return personRoleController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing person role")
  @RequestMapping(value = "/personrole/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> personRoleDelete(@PathVariable Integer id) throws Exception {
    return personRoleController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing person role")
  @RequestMapping(value = "/personrole/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> personRolePut(@PathVariable Integer id,
      @RequestBody IdNameTypePut personRoleUpdate) throws Exception {
    return personRoleController.put(new MapUtil("id", id).create(), personRoleUpdate);
  }

  //// service instance status
  
  @ApiOperation(value = "get all service instance status options")
  @RequestMapping(value = "/serviceinstancestatus", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<IdNameTypeGet>> serviceInstanceStatusGetAll() throws Exception {
    return serviceInstanceStatusController.getAll(null);
  }

  @ApiOperation(value = "create new service instance status")
  @RequestMapping(value = "/serviceinstancestatus", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> serviceInstanceStatusPost (
      @RequestBody IdNameTypePost newServiceInstanceStatus, HttpServletRequest request) throws Exception {
    return serviceInstanceStatusController.create(newServiceInstanceStatus, null, request);
  }

  @ApiOperation(value = "get existing service instance status")
  @RequestMapping(value = "/serviceinstancestatus/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<IdNameTypeGet> serviceInstanceStatusGet(@PathVariable Integer id) throws Exception {
    return serviceInstanceStatusController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "remove existing service instance status")
  @RequestMapping(value = "/serviceinstancestatus/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> serviceInstanceStatusDelete(@PathVariable Integer id) throws Exception {
    return serviceInstanceStatusController.delete(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "update existing service instance status")
  @RequestMapping(value = "/serviceinstancestatus/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> serviceInstanceStatusPut(@PathVariable Integer id,
      @RequestBody IdNameTypePut serviceInstanceStatusUpdate) throws Exception {
    return serviceInstanceStatusController.put(new MapUtil("id", id).create(), serviceInstanceStatusUpdate);
  }

}
