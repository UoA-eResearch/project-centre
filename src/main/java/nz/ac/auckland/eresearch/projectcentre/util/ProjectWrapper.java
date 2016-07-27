package nz.ac.auckland.eresearch.projectcentre.util;

import java.util.LinkedList;
import java.util.List;

import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectActionGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferenceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectPropertyGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ResearchOutputGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ServiceInstanceGet;

// TODO: add service instances
// TODO: remove facilities
public class ProjectWrapper {

  private ProjectGet project;
  private List<ProjectMemberGet> members;
  private List<ResearchOutputGet> researchOutputs;
  private List<ProjectActionGet> projectActions;
  private List<ProjectExternalReferenceGet> externalReferences;
  private List<ServiceInstanceGet> serviceInstances;
  private List<ProjectPropertyGet> projectProperties;

  public ProjectWrapper() {
    project = new ProjectGet();
    members = new LinkedList<ProjectMemberGet>();
    researchOutputs = new LinkedList<ResearchOutputGet>();
    projectActions = new LinkedList<ProjectActionGet>();
    externalReferences = new LinkedList<ProjectExternalReferenceGet>();
    serviceInstances = new LinkedList<ServiceInstanceGet>();
    projectProperties = new LinkedList<ProjectPropertyGet>();
  }

  public ProjectGet getProject() {
    return project;
  }

  public void setProject(ProjectGet project) {
    this.project = project;
  }

  public List<ProjectMemberGet> getMembers() {
    return members;
  }

  public void setMembers(List<ProjectMemberGet> members) {
    this.members = members;
  }

  public List<ResearchOutputGet> getResearchOutputs() {
    return researchOutputs;
  }

  public void setResearchOutputs(List<ResearchOutputGet> researchOutputs) {
    this.researchOutputs = researchOutputs;
  }

  public List<ProjectActionGet> getProjectActions() {
    return projectActions;
  }

  public void setProjectActions(List<ProjectActionGet> projectActions) {
    this.projectActions = projectActions;
  }

  public List<ProjectPropertyGet> getProjectProperties() {
    return projectProperties;
  }

  public void setProjectProperties(List<ProjectPropertyGet> projectProperties) {
    this.projectProperties = projectProperties;
  }

  public List<ProjectExternalReferenceGet> getExternalReferences() {
    return externalReferences;
  }

  public void setExternalReferences(List<ProjectExternalReferenceGet> externalReferences) {
    this.externalReferences = externalReferences;
  }

  public List<ServiceInstanceGet> getServiceInstances() {
    return serviceInstances;
  }

  public void setServiceInstances(List<ServiceInstanceGet> serviceInstances) {
    this.serviceInstances = serviceInstances;
  }

}
