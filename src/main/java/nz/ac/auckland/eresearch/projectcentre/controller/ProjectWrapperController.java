package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.PersonProject;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectFacility;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.service.FacilityService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectActionService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectFacilityService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectKpiService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectPropertyService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ResearchOutputService;
import nz.ac.auckland.eresearch.projectcentre.util.ProjectWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/projectWrapper")
public class ProjectWrapperController {

  @Autowired
  ProjectService projectService;
  @Autowired
  PersonProjectService personProjectService;
  @Autowired
  PersonRepository personRepo;
  @Autowired
  ResearchOutputService researchOutputService;
  @Autowired
  ProjectActionService projectActionService;
  @Autowired
  ProjectKpiService projectKpiService;
  @Autowired
  FacilityService facilityService;
  @Autowired
  ProjectFacilityService projectFacilityService;
  @Autowired
  ProjectPropertyService projectPropertyService;
  private Logger log = LoggerFactory.getLogger(ProjectWrapperController.class);

  // TODO finish this
  @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public
  @ResponseBody
  ProjectWrapper get(@PathVariable Integer projectId) {
    log.debug("GET on " + this.getClass().getSimpleName() + " with id " + projectId);
    ProjectWrapper pw = new ProjectWrapper();
    pw.setProject(this.projectService.findOne(projectId));
    pw.setResearchOutputs(this.researchOutputService.findByProjectId(projectId));
    pw.setProjectActions(this.projectActionService.findByProjectId(projectId));
    pw.setProjectKpis(this.projectKpiService.findByProjectId(projectId));

    List<Person> persons = new LinkedList<Person>();
    Iterable<PersonProject> pps = this.personProjectService.findByProjectId(projectId);
    if (pps != null) {
      for (PersonProject pp : pps) {
        Integer pid = pp.getPersonId();
        Person persTemp = personRepo.findOne(pid);
        persons.add(persTemp);
      }
    }
    pw.setPersons(persons);

    List<ProjectFacility> projectFacilities = this.projectFacilityService.findByProjectId(projectId);
    List<Integer> facilityIds = new LinkedList<Integer>();
    for (ProjectFacility pf : projectFacilities) {
      facilityIds.add(pf.getFacilityId());
    }
    pw.setFacilities(this.facilityService.findAll(facilityIds));
    pw.setProjectProperties(this.projectPropertyService.findByProjectId(projectId));
    return pw;
  }


}
