package nz.ac.auckland.eresearch.projectcentre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import nz.ac.auckland.eresearch.projectcentre.service.IdentityService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonAffiliationService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonPropertyService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectMemberService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPropertyPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPut;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberGet;
import nz.ac.auckland.eresearch.projectcentre.types.convert.PersonAffiliationConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.PersonConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.PersonPropertyConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectConverter;
import nz.ac.auckland.eresearch.projectcentre.types.convert.ProjectMemberConverter;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Identity;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonProperty;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;
import nz.ac.auckland.eresearch.projectcentre.util.ControllerExceptionHandler;
import nz.ac.auckland.eresearch.projectcentre.util.LocationUtil;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.validation.PersonAffiliationValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.PersonPropertyValidator;
import nz.ac.auckland.eresearch.projectcentre.validation.PersonValidator;

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
@Api(tags={"Person"})
@RequestMapping(value = "/api/person")
public class PersonController extends ControllerExceptionHandler {
  
  private BaseController<Person,PersonGet,PersonPut,PersonPost> personController;
  private BaseController<PersonAffiliation,PersonAffiliationGet,PersonAffiliationPut,PersonAffiliationPost> affiliationController;
  private BaseController<PersonProperty,PersonPropertyGet,PersonPropertyPut,PersonPropertyPost> propertyController;
  private PersonAffiliationService affiliationService;
  private IdentityService identityService;
  private PersonService personService;
  private ProjectService projectService;
  private ProjectMemberService memberService;
  private PersonConverter personConverter;
  private ProjectConverter projectConverter;
  private ProjectMemberConverter projectMemberConverter;
  private PersonAffiliationConverter affiliationConverter;
  private LocationUtil locationUtil;
  
  @Autowired
  public PersonController(
      PersonService personService, 
      IdentityService identityService, 
      PersonAffiliationService personAffiliationService,
      PersonPropertyService personPropertyService,
      ProjectService projectService, 
      ProjectMemberService memberService, 
      PersonValidator personValidator,
      PersonAffiliationValidator personAffiliationValidator,
      PersonPropertyValidator personPropertyValidator,
      PersonConverter personConverter,
      PersonAffiliationConverter personAffiliationConverter,
      PersonPropertyConverter personPropertyConverter,
      ProjectConverter projectConverter,
      ProjectMemberConverter projectMemberConverter,
      LocationUtil locationUtil) {
    this.locationUtil = locationUtil;
    this.personService = personService;
    this.identityService = identityService;
    this.affiliationService = personAffiliationService;
    this.projectService = projectService;
    this.memberService = memberService;
    this.personConverter = personConverter;
    this.projectConverter = projectConverter;
    this.projectMemberConverter = projectMemberConverter;
    this.affiliationConverter = personAffiliationConverter;
    this.personController = new BaseController(personService, personConverter, personValidator);
    this.affiliationController = new BaseController(personAffiliationService, personAffiliationConverter, personAffiliationValidator);
    this.propertyController = new BaseController(personPropertyService, personPropertyConverter, personPropertyValidator);
  }

  //// person
  
  @ApiOperation(value = "get list of persons", nickname="get_persons")
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<PersonGet>> personGetAll() throws Exception {
    return personController.getAll(null);
  }

  // TODO: Transaction
  @ApiOperation(value = "create new person", nickname="create_person")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> personPost(@RequestBody PersonPost newPerson,
      HttpServletRequest request) throws Exception {
    Integer personId = personController.createWithoutResponse(newPerson, null, request).getId();
    
    if (newPerson.getAffiliations() != null) {
      for (PersonAffiliationPost affil : newPerson.getAffiliations()) {
        PersonAffiliation pa = this.affiliationConverter.post2Entity(affil, new MapUtil("personId", personId).create());
        this.affiliationService.create(pa);
      }
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(locationUtil.createLocation(request, personId));
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }
  
  @ApiOperation(value = "get existing person", nickname="get_person")
  @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<PersonGet> personGet(@PathVariable Integer personId) throws Exception {
    return personController.get(new MapUtil("id", personId).create());
  }

  @ApiOperation(value = "get existing person by identity, e.g. UPI", nickname="find_person_by_identity")
  @RequestMapping(value = "/findByIdentity/{identity}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<PersonGet> personGetByIdentity(@PathVariable String identity) throws Exception {
    Identity i = this.identityService.findByUsername(identity);
    if (i == null || i.getPersonId() == null) {
      return new ResponseEntity<PersonGet>(HttpStatus.NOT_FOUND);
    }
    Person p = this.personService.findOne(i.getPersonId(), null);
    PersonGet pg = this.personConverter.entity2Get(p, null);
    return new ResponseEntity<PersonGet>(pg, HttpStatus.OK);
  }


  @ApiOperation(value = "patch existing person. same fields like in PUT can be updated", nickname="patch_person")
  @RequestMapping(value = "/{personId}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> personPatch(@PathVariable Integer personId,
      @RequestBody PersonPut params) throws Exception {
    return personController.patch(new MapUtil("id", personId).create(), params);
  }

  @ApiOperation(value = "update existing person", nickname="update_person")
  @RequestMapping(value = "/{personId}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> personPut(@PathVariable Integer personId,
      @RequestBody PersonPut projectUpdate) throws Exception {
    return personController.put(new MapUtil("id", personId).create(), projectUpdate);
  }

  @ApiOperation(value = "delete existing person", nickname="delete_person")
  @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> personDelete(@PathVariable Integer personId) throws Exception {
    return personController.delete(new MapUtil("id", personId).create());
  }
  
  //// person affiliation
  
  @ApiOperation(value = "get all affiliations of a person", nickname="get_person_affiliations")
  @RequestMapping(value = "/{personId}/affiliation", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<PersonAffiliationGet>> affiliationGetAll(@PathVariable Integer personId) throws Exception {
    return affiliationController.getAll(new MapUtil("personId", personId).create());
  }

  @ApiOperation(value = "add affiliation to person", nickname="create_person_affiliation")
  @RequestMapping(value = "/{personId}/affiliation", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> affiliationPost(@PathVariable Integer personId,
      @RequestBody PersonAffiliationPost newAffiliation, HttpServletRequest request) throws Exception {
    return affiliationController.create(newAffiliation, new MapUtil("personId", personId).create(), request);
    
  }

  @ApiOperation(value = "get existing person affiliation", nickname="get_person_affiliation")
  @RequestMapping(value = "/{personId}/affiliation/{affiliationId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<PersonAffiliationGet> affiliationGet(@PathVariable Integer personId,
      @PathVariable Integer affiliationId) throws Exception {
    return affiliationController.get(new MapUtil("id", affiliationId).add("personId", personId).create());
  }

  @ApiOperation(value = "remove existing person affiliation", nickname="delete_person_affiliation")
  @RequestMapping(value = "/{personId}/affiliation/{affiliationId}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> affiliationDelete(@PathVariable Integer personId,
      @PathVariable Integer affiliationId) throws Exception {
    return affiliationController.delete(new MapUtil("id", affiliationId).add("personId", personId).create());
  }

  @ApiOperation(value = "patch existing person affiliation. same input fields like in PUT can be updated", nickname="patch_person_affiliation")
  @RequestMapping(value = "/{personId}/affiliation/{affiliationId}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> affiliationPatch(@PathVariable Integer personId,
      @PathVariable Integer affiliationId, @RequestBody PersonAffiliationPut params) throws Exception {
    return affiliationController.patch(new MapUtil("id", affiliationId).add("personId", personId).create(), params);
  }

  @ApiOperation(value = "update existing person affiliation", nickname="update_person_affiliation")
  @RequestMapping(value = "/{personId}/affiliation/{affiliationId}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> affiliationPut(@PathVariable Integer personId,
      @PathVariable Integer affiliationId, @RequestBody PersonAffiliationPut personAffiliationUpdate) throws Exception {
    return affiliationController.put(new MapUtil("id", affiliationId).add("personId", personId).create(), personAffiliationUpdate);
  }

  //// person property
  
  @ApiOperation(value = "get all properties of a person", nickname="get_person_properties")
  @RequestMapping(value = "/{personId}/property", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<PersonPropertyGet>> propertyGetAll(@PathVariable Integer personId) throws Exception {
    return propertyController.getAll(new MapUtil("personId", personId).create());
  }

  @ApiOperation(value = "add property to person", nickname="create_person_property")
  @RequestMapping(value = "/{personId}/property", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<Void> propertyPost(@PathVariable Integer personId,
      @RequestBody PersonPropertyPost newProperty, HttpServletRequest request) throws Exception {
    return propertyController.create(newProperty, new MapUtil("personId", personId).create(), request);
    
  }

  @ApiOperation(value = "get existing person property", nickname="get_person_property")
  @RequestMapping(value = "/{personId}/property/{propertyId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<PersonPropertyGet> propertyGet(@PathVariable Integer personId,
      @PathVariable Integer propertyId) throws Exception {
    return propertyController.get(new MapUtil("id", propertyId).add("personId", personId).create());
  }

  @ApiOperation(value = "remove existing person property", nickname="delete_person_property")
  @RequestMapping(value = "/{personId}/property/{propertyId}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<Void> propertyDelete(@PathVariable Integer personId,
      @PathVariable Integer propertyId) throws Exception {
    return propertyController.delete(new MapUtil("id", propertyId).add("personId", personId).create());
  }

  @ApiOperation(value = "patch existing person property. same input fields like in PUT can be updated", nickname="patch_person_property")
  @RequestMapping(value = "/{personId}/property/{propertyId}", method = RequestMethod.PATCH)
  public @ResponseBody ResponseEntity<Void> propertyPatch(@PathVariable Integer personId,
      @PathVariable Integer propertyId, @RequestBody PersonPropertyPut params) throws Exception {
    return propertyController.patch(new MapUtil("id", propertyId).add("personId", personId).create(), params);
  }

  @ApiOperation(value = "update existing person property", nickname="update_person_property")
  @RequestMapping(value = "/{personId}/property/{propertyId}", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<Void> propertyPut(@PathVariable Integer personId,
      @PathVariable Integer propertyId, @RequestBody PersonPropertyPut propertyUpdate) throws Exception {
    return propertyController.put(new MapUtil("id", propertyId).add("personId", personId).create(), propertyUpdate);
  }

  //// helper methods
  
  @ApiOperation(value = "get all projects of a person", nickname="get_person_projects")
  @RequestMapping(value = "/{personId}/projects", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody ResponseEntity<List<ProjectMemberGet>> getProjects(@PathVariable Integer personId) throws Exception {
    // TODO: check if converter can be be used for both use-cases to include project
    // and person, based on id in map
    List<ProjectMember> pmList = this.memberService.findByPersonId(personId);
    List<Integer> projectIds = pmList.stream().map(ProjectMember::getProjectId).collect(Collectors.toList());
    Iterable<Project> pl = projectService.findList(projectIds);
    Map<Integer, Project> projectMap = new HashMap<Integer, Project>();
    for (Project p : pl) {
      projectMap.put(p.getId(), p);
    }
    List<ProjectMemberGet> pmgl = new LinkedList<ProjectMemberGet>();
    for (ProjectMember pm: pmList) {
      ProjectMemberGet pmg = projectMemberConverter.entity2Get(pm, null);
      pmg.setPerson(null);
      pmg.setProject(projectConverter.entity2Get(projectMap.get(pm.getProjectId()), null));
      pmgl.add(pmg);
    }
    return new ResponseEntity<List<ProjectMemberGet>>(pmgl, HttpStatus.OK);
  }


  
}
