package nz.ac.auckland.eresearch.projectcentre.util;

import nz.ac.auckland.eresearch.projectcentre.entity.Facility;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.entity.Project;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectAction;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectKpi;
import nz.ac.auckland.eresearch.projectcentre.entity.ProjectProperty;
import nz.ac.auckland.eresearch.projectcentre.entity.ResearchOutput;

import java.util.LinkedList;
import java.util.List;

public class ProjectWrapper {

  private Project project;
  private List<Person> persons;
  private Iterable<ResearchOutput> researchOutputs;
  private Iterable<ProjectAction> projectActions;
  private Iterable<Facility> facilities;
  private Iterable<ProjectKpi> projectKpis;
  private Iterable<ProjectProperty> projectProperties;

  public ProjectWrapper() {
    project = new Project();
    persons = new LinkedList<Person>();
    researchOutputs = new LinkedList<ResearchOutput>();
    projectActions = new LinkedList<ProjectAction>();
    projectKpis = new LinkedList<ProjectKpi>();
    facilities = new LinkedList<Facility>();
    projectProperties = new LinkedList<ProjectProperty>();
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }

  public Iterable<ResearchOutput> getResearchOutputs() {
    return researchOutputs;
  }

  public void setResearchOutputs(Iterable<ResearchOutput> researchOutputs) {
    this.researchOutputs = researchOutputs;
  }

  public Iterable<ProjectAction> getProjectActions() {
    return projectActions;
  }

  public void setProjectActions(Iterable<ProjectAction> projectActions) {
    this.projectActions = projectActions;
  }

  public Iterable<Facility> getFacilities() {
    return facilities;
  }

  public void setFacilities(Iterable<Facility> facilities) {
    this.facilities = facilities;
  }

  public Iterable<ProjectKpi> getProjectKpis() {
    return projectKpis;
  }

  public void setProjectKpis(Iterable<ProjectKpi> projectKpis) {
    this.projectKpis = projectKpis;
  }

  public Iterable<ProjectProperty> getProjectProperties() {
    return projectProperties;
  }

  public void setProjectProperties(Iterable<ProjectProperty> projectProperties) {
    this.projectProperties = projectProperties;
  }

}
