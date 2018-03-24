package nz.ac.auckland.eresearch.projectcentre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nz.ac.auckland.eresearch.projectcentre.service.ExternalReferenceService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectDivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectMemberService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceInstanceService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectDivisionPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferenceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferencePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferencePut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstanceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstancePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstancePut;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ExternalReferenceConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectActionConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectDivisionConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectMemberConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectPropertyConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ResearchOutputConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ServiceInstanceConverter;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectDivision;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectExternalReference;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ResearchOutput;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstance;
import nz.ac.auckland.eresearch.projectcentre.util.ControllerExceptionHandler;
import nz.ac.auckland.eresearch.projectcentre.util.LocationUtil;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.util.ProjectWrapper;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;
import nz.ac.auckland.eresearch.projectcentre.validation.ExternalReferenceValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.MyValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectActionValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectDivisionValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectMemberValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectPropertyValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ProjectValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ResearchOutputValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.ServiceInstanceValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@Api(tags={"Project"})
@RequestMapping(value = "/api/project")
public class ProjectController extends ControllerExceptionHandler {

  protected Logger log = LoggerFactory.getLogger(getClass());
  private BaseController<Project,ProjectGet,ProjectPut,ProjectPost> projectController;
  private BaseController<ProjectAction,ProjectActionGet,ProjectActionPut,ProjectActionPost> actionController;
  private BaseController<ResearchOutput,ResearchOutputGet,ResearchOutputPut,ResearchOutputPost> researchOutputController;
  private BaseController<ProjectMember,ProjectMemberGet,ProjectMemberPut,ProjectMemberPost> memberController;
  private BaseController<ProjectExternalReference,ProjectExternalReferenceGet,ProjectExternalReferencePut,ProjectExternalReferencePost> externalReferenceController;
  private BaseController<ProjectProperty,ProjectPropertyGet,ProjectPropertyPut,ProjectPropertyPost> propertyController;
  private BaseController<ProjectDivision,ProjectDivisionGet,ProjectDivisionPut,ProjectDivisionPost> projectDivisionController;
  private BaseController<ServiceInstance,ServiceInstanceGet,ServiceInstancePut,ServiceInstancePost> serviceInstanceController;
  private TypeConverter<Project,ProjectGet,ProjectPut,ProjectPost> projectConverter;
  private ProjectService projectService;
  private ProjectDivisionService projectDivisionService;
  private ProjectDivisionValidator projectDivisionValidator;
  private ProjectValidator projectValidator;
  private LocationUtil locationUtil;
  
  @Autowired
  public ProjectController(
      ProjectService projectService,
      ProjectActionService actionService,
      ProjectMemberService memberService,
      ExternalReferenceService externalReferenceService,
      ProjectDivisionService projectDivisionService,
      ResearchOutputService outputService,
      ProjectPropertyService propertyService,
      ServiceInstanceService serviceInstanceService,
      ProjectValidator projectValidator,
      ProjectActionValidator actionValidator, 
      ProjectMemberValidator memberValidator, 
      ExternalReferenceValidator externalReferenceValidator, 
      ResearchOutputValidator outputValidator, 
      ProjectDivisionValidator projectDivisionValidator,
      ProjectPropertyValidator propertyValidator,
      ServiceInstanceValidator serviceInstanceValidator,
      ProjectConverter projectConverter, 
      ProjectActionConverter actionConverter, 
      ProjectMemberConverter memberConverter,
      ExternalReferenceConverter externalReferenceConverter,
      ResearchOutputConverter outputConverter,
      ProjectPropertyConverter propertyConverter,
      ProjectDivisionConverter projectDivisionConverter,
      ServiceInstanceConverter serviceInstanceConverter,
      LocationUtil locationUtil) {
    this.projectService = projectService;
    this.projectDivisionService = projectDivisionService;
    this.projectValidator = projectValidator;
    this.projectDivisionValidator = projectDivisionValidator;
    this.projectController = new BaseController(projectService, projectConverter, projectValidator);
    this.actionController = new BaseController(actionService, actionConverter, actionValidator);
    this.memberController = new BaseController(memberService, memberConverter, memberValidator);
    this.externalReferenceController = new BaseController(externalReferenceService, externalReferenceConverter, externalReferenceValidator);
    this.researchOutputController = new BaseController(outputService, outputConverter, outputValidator);
    this.propertyController = new BaseController(propertyService, propertyConverter, propertyValidator);
    this.projectDivisionController = new BaseController(projectDivisionService, projectDivisionConverter, projectDivisionValidator);
    this.serviceInstanceController = new BaseController(serviceInstanceService, serviceInstanceConverter, serviceInstanceValidator);
    this.projectConverter = projectConverter;
  }

  //// project
  
  @ApiOperation(value = "get list of projects", nickname="backend.project.get_projects")
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ProjectGet>> projectGetAll() throws Exception {
    return projectController.getAll(null);
  }

  // TODO: Transaction
  // FIXME: projects likely need to be created by non-admins. add ability to add member ids.
  @ApiOperation(value = "create new project", nickname="backend.project.create_project")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> projectPost(@RequestBody ProjectPost newProject,
      HttpServletRequest request) throws Exception {
    
    Project tmp = this.projectConverter.post2Entity(newProject, null);
    new MyValidator(projectValidator).validate(tmp);
    Project p = this.projectService.create(tmp);

    try {
      if (newProject.getDivisionIds() != null) {
        for (Integer divisionId : newProject.getDivisionIds()) {
          ProjectDivision pd = new ProjectDivision(p.getId(), divisionId);
          new MyValidator(projectDivisionValidator).validate(pd);
          this.projectDivisionService.create(pd);
        }
      }
    } catch (Exception e) {
      this.projectService.delete(p.getId());
      throw e;
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(locationUtil.createLocation(request, p.getId()));
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }
  
  @ApiOperation(value = "get existing project", nickname="backend.project.get_project")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectGet> projectGet(@PathVariable Integer id) throws Exception {
    return projectController.get(new MapUtil("id", id).create());
  }

  @ApiOperation(value = "get existing project by code", nickname="backend.project.find_project_by_code")
  @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectGet> projectGetByCode(@PathVariable String code) throws Exception {
    Project p = this.projectService.findByCode(code);
    if (p == null) {
      return new ResponseEntity<ProjectGet>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<ProjectGet>(projectConverter.entity2Get(p, null), HttpStatus.OK);
  }

  @ApiOperation(value = "patch existing project. same fields like in PUT can be updated", nickname="backend.project.patch_project")
  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> projectPatch(@PathVariable Integer id,
      @RequestBody ProjectPut params) throws Exception {
    return projectController.patch(new MapUtil("id", id).create(), params);
  }

  @ApiOperation(value = "update existing project", nickname="backend.project.update_project")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> projectPut(@PathVariable Integer id,
      @RequestBody ProjectPut projectUpdate) throws Exception {
    return projectController.put(new MapUtil("id", id).create(), projectUpdate);
  }

  @ApiOperation(value = "delete existing project", nickname="backend.project.delete_project")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> projectDelete(@PathVariable Integer id) throws Exception {
    return projectController.delete(new MapUtil("id", id).create());
  }

  //// project action
  
  @ApiOperation(value = "get list of project actions", nickname="backend.project.get_project_actions")
  @RequestMapping(value = "/{projectId}/action", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ProjectActionGet>> actionGetAll(
    @PathVariable Integer projectId) throws Exception {
    return actionController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "create new project action", nickname="backend.project.create_project_action")
  @RequestMapping(value = "/{projectId}/action", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> actionPost(@RequestBody ProjectActionPost newProjectAction,
      @PathVariable Integer projectId, HttpServletRequest request) throws Exception {
    return actionController.create(newProjectAction, new MapUtil("projectId", projectId).create(), request);
  }
  
  @ApiOperation(value = "get existing project action", nickname="backend.project.get_project_action")
  @RequestMapping(value = "/{projectId}/action/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectActionGet> actionGet(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return actionController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing project action. same fields like in PUT can be updated", nickname="backend.project.patch_project_action")
  @RequestMapping(value = "/{projectId}/action/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> actionPatch(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ProjectActionPut params) throws Exception {
    return actionController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing project action", nickname="backend.project.update_project_action")
  @RequestMapping(value = "/{projectId}/action/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> actionPut(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ProjectActionPut projectActionUpdate) throws Exception {
    return actionController.put(new MapUtil("id", id).add("projectId", projectId).create(), projectActionUpdate);
  }

  @ApiOperation(value = "delete existing project action", nickname="backend.project.delete_project_action")
  @RequestMapping(value = "/{projectId}/action/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> actionDelete(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return actionController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  //// research output
  
  @ApiOperation(value = "get list of research outputs", nickname="backend.project.get_project_researchoutputs")
  @RequestMapping(value = "/{projectId}/researchoutput", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ResearchOutputGet>> researchoutputGetAll(
    @PathVariable Integer projectId) throws Exception {
    return researchOutputController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "create new research output", nickname="backend.project.create_project_researchoutput")
  @RequestMapping(value = "/{projectId}/researchoutput", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> researchoutputPost(@RequestBody ResearchOutputPost newResearchOutput,
      @PathVariable Integer projectId, HttpServletRequest request) throws Exception {
    return researchOutputController.create(newResearchOutput, new MapUtil("projectId", projectId).create(), request);
  }
  
  @ApiOperation(value = "get existing research output", nickname="backend.project.get_project_researchoutput")
  @RequestMapping(value = "/{projectId}/researchoutput/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ResearchOutputGet> researchoutputGet(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return researchOutputController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing research output. same fields like in PUT can be updated", nickname="backend.project.patch_project_researchoutput")
  @RequestMapping(value = "/{projectId}/researchoutput/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> researchoutputPatch(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ResearchOutputPut params) throws Exception {
    return researchOutputController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing research output", nickname="backend.project.update_project_researchoutput")
  @RequestMapping(value = "/{projectId}/researchoutput/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> researchoutputPut(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ResearchOutputPut researchOutputUpdate) throws Exception {
    return researchOutputController.put(new MapUtil("id", id).add("projectId", projectId).create(), researchOutputUpdate);
  }

  @ApiOperation(value = "delete existing research output", nickname="backend.project.delete_project_researchoutput")
  @RequestMapping(value = "/{projectId}/researchoutput/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> researchoutputDoDelete(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return researchOutputController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  //// member
  
  @ApiOperation(value = "get all members of project", nickname="backend.project.get_project_members")
  @RequestMapping(value = "/{projectId}/member", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ProjectMemberGet>> memberGetAll(@PathVariable Integer projectId) throws Exception {
    return memberController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "add member to project", nickname="backend.project.create_project_member")
  @RequestMapping(value = "/{projectId}/member", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> memberPost(@PathVariable Integer projectId,
      @RequestBody ProjectMemberPost newProjectMember, HttpServletRequest request) throws Exception {
    return memberController.create(newProjectMember, new MapUtil("projectId", projectId).create(), request);
  }

  @ApiOperation(value = "get existing project member", nickname="backend.project.get_project_member")
  @RequestMapping(value = "/{projectId}/member/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectMemberGet> memberGet(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return memberController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "remove existing project member", nickname="backend.project.delete_project_member")
  @RequestMapping(value = "/{projectId}/member/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> memberDelete(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return memberController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing project member. same input fields like in PUT can be updated", nickname="backend.project.patch_project_member")
  @RequestMapping(value = "/{projectId}/member/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> memberPatch(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectMemberPut params) throws Exception {
    return memberController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing project member", nickname="backend.project.update_project_member")
  @RequestMapping(value = "/{projectId}/member/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> memberPut(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectMemberPut projectMemberUpdate) throws Exception {
    return memberController.put(new MapUtil("id", id).add("projectId", projectId).create(), projectMemberUpdate);
  }

  //// external reference
  
  @ApiOperation(value = "get all external references of project", nickname="backend.project.get_project_ext_refs")
  @RequestMapping(value = "/{projectId}/externalreference", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ProjectExternalReferenceGet>> externalReferenceGetAll(@PathVariable Integer projectId) throws Exception {
    return externalReferenceController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "add external reference to project", nickname="backend.project.create_project_ext_ref")
  @RequestMapping(value = "/{projectId}/externalreference", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> externalReferencePost(@PathVariable Integer projectId,
      @RequestBody ProjectExternalReferencePost newExternalReference, HttpServletRequest request) throws Exception {
    return externalReferenceController.create(newExternalReference, new MapUtil("projectId", projectId).create(), request);
  }

  @ApiOperation(value = "get existing external reference", nickname="backend.project.get_project_ext_ref")
  @RequestMapping(value = "/{projectId}/externalreference/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectExternalReferenceGet> externalReferenceGet(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return externalReferenceController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "remove existing external reference", nickname="backend.project.delete_project_ext_ref")
  @RequestMapping(value = "/{projectId}/externalreference/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> externalReferenceDelete(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return externalReferenceController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing external reference. same input fields like in PUT can be updated", nickname="backend.project.patch_project_ext_ref")
  @RequestMapping(value = "/{projectId}/externalreference/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> externalReferencePatch(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectExternalReferencePut params) throws Exception {
    return externalReferenceController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing external reference", nickname="backend.project.update_project_ext_ref")
  @RequestMapping(value = "/{projectId}/externalreference/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> externalReferencePut(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectExternalReferencePut externalReferenceUpdate) throws Exception {
    return externalReferenceController.put(new MapUtil("id", id).add("projectId", projectId).create(), externalReferenceUpdate);
  }
  
  //// project property
  
  @ApiOperation(value = "get all properties of a project", nickname="backend.project.get_project_properties")
  @RequestMapping(value = "/{projectId}/property", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<ProjectPropertyGet>> propertyGetAll(@PathVariable Integer projectId) throws Exception {
    return propertyController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "add property to project", nickname="backend.project.create_project_property")
  @RequestMapping(value = "/{projectId}/property", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> propertyPost(@PathVariable Integer projectId,
      @RequestBody ProjectPropertyPost newProperty, HttpServletRequest request) throws Exception {
    return propertyController.create(newProperty, new MapUtil("projectId", projectId).create(), request);
    
  }

  @ApiOperation(value = "get existing project property", nickname="backend.project.get_project_property")
  @RequestMapping(value = "/{projectId}/property/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectPropertyGet> propertyGet(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return propertyController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "remove existing project property", nickname="backend.project.delete_project_property")
  @RequestMapping(value = "/{projectId}/property/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> propertyDelete(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return propertyController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing project property. same input fields like in PUT can be updated", nickname="backend.project.patch_project_property")
  @RequestMapping(value = "/{projectId}/property/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> propertyPatch(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectPropertyPut params) throws Exception {
    return propertyController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing project property", nickname="backend.project.update_project_property")
  @RequestMapping(value = "/{projectId}/property/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> propertyPut(@PathVariable Integer projectId,
      @PathVariable Integer id, @RequestBody ProjectPropertyPut propertyUpdate) throws Exception {
    return propertyController.put(new MapUtil("id", id).add("projectId", projectId).create(), propertyUpdate);
  }

  //// project division
  
  @ApiOperation(value = "add division to project", nickname="backend.project.get_project_divisions")
  @RequestMapping(value = "/{projectId}/division", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> divisionPost(@PathVariable Integer projectId,
      @RequestBody ProjectDivisionPost newDivision, HttpServletRequest request) throws Exception {
    return projectDivisionController.create(newDivision, new MapUtil("projectId", projectId).create(), request);
    
  }

  @ApiOperation(value = "remove existing project division", nickname="backend.project.delete_project_division")
  @RequestMapping(value = "/{projectId}/division/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> divisionDelete(@PathVariable Integer projectId,
      @PathVariable Integer id) throws Exception {
    return projectDivisionController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  //// service instance
  
  @ApiOperation(value = "get list of service instances", nickname="backend.project.get_project_service_instances")
  @RequestMapping(value = "/{projectId}/service", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<ServiceInstanceGet>> serviceInstanceGetAll(
      @PathVariable Integer projectId) throws Exception {
    return serviceInstanceController.getAll(new MapUtil("projectId", projectId).create());
  }

  @ApiOperation(value = "create new service instance", nickname="backend.project.create_project_service_instance")
  @RequestMapping(value = "/{projectId}/service", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> serviceInstancePost(@PathVariable Integer projectId,
      @RequestBody ServiceInstancePost newServiceInstance, HttpServletRequest request) throws Exception {
    return serviceInstanceController.create(newServiceInstance, new MapUtil("projectId", projectId).create(), request);
  }
  
  @ApiOperation(value = "get existing service instance", nickname="backend.project.get_project_service_instance")
  @RequestMapping(value = "/{projectId}/service/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ServiceInstanceGet> serviceInstanceGet(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return serviceInstanceController.get(new MapUtil("id", id).add("projectId", projectId).create());
  }

  @ApiOperation(value = "patch existing service instance. same fields like in PUT can be updated", nickname="backend.project.patch_project_service_instance")
  @RequestMapping(value = "/{projectId}/service/{id}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> serviceInstancePatch(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ServiceInstancePut params) throws Exception {
    return serviceInstanceController.patch(new MapUtil("id", id).add("projectId", projectId).create(), params);
  }

  @ApiOperation(value = "update existing service instance", nickname="backend.project.update_project_service_instance")
  @RequestMapping(value = "/{projectId}/service/{id}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> serviceInstancePut(@PathVariable Integer id,
      @PathVariable Integer projectId, @RequestBody ServiceInstancePut serviceInstanceUpdate) throws Exception {
    return serviceInstanceController.put(new MapUtil("id", id).add("projectId", projectId).create(), serviceInstanceUpdate);
  }

  @ApiOperation(value = "delete existing service instance", nickname="backend.project.delete_project_service_instance")
  @RequestMapping(value = "/{projectId}/service/{id}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> serviceInstanceDelete(@PathVariable Integer id,
      @PathVariable Integer projectId) throws Exception {
    return serviceInstanceController.delete(new MapUtil("id", id).add("projectId", projectId).create());
  }

  //// wrapper
  
  @ApiOperation(value = "get a wrapper object of a project", nickname="backend.project.get_project_wrapper")
  @RequestMapping(value = "/wrapper/{projectId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectWrapper> wrapperGet(@PathVariable Integer projectId) throws Exception {
    ProjectGet pg = projectController.getRaw(new MapUtil("id", projectId).create());
    if (pg == null) {
      return new ResponseEntity<ProjectWrapper>(HttpStatus.NOT_FOUND);
    }
    ProjectWrapper wrapper = new ProjectWrapper();
    wrapper.setProject(pg);
    Map<String,Integer> tmpMap = new MapUtil("projectId", projectId).create();
    wrapper.setMembers(memberController.getAllRaw(tmpMap));
    wrapper.setExternalReferences(externalReferenceController.getAllRaw(tmpMap));
    wrapper.setResearchOutputs(researchOutputController.getAllRaw(tmpMap));
    wrapper.setProjectActions(actionController.getAllRaw(tmpMap));
    wrapper.setServiceInstances(serviceInstanceController.getAllRaw(tmpMap));
    wrapper.setProjectProperties(propertyController.getAllRaw(tmpMap));
    return new ResponseEntity<ProjectWrapper>(wrapper, HttpStatus.OK);
  }

  @ApiOperation(value = "get a wrapper object of a project by code", nickname="backend.project.find_project_wrapper_by_code")
  @RequestMapping(value = "/wrapper/findByCode/{code}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<ProjectWrapper> wrapperGetByCode(@PathVariable String code) throws Exception {
    Project p = this.projectService.findByCode(code);
    if (p == null) {
      return new ResponseEntity<ProjectWrapper>(HttpStatus.NOT_FOUND);
    }
    return this.wrapperGet(p.getId());
  }

}
