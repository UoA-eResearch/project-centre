package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.PersonRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.service.ProjectMemberService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectMemberPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonRole;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;
import nz.ac.auckland.eresearch.projectcentre.util.MapUtil;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberConverter implements TypeConverter<ProjectMember, ProjectMemberGet, ProjectMemberPut, ProjectMemberPost> {

  @Autowired
  private ProjectMemberService projectMemberService;
  @Autowired
  private PersonRoleService personRoleService;
  @Autowired
  private PersonService personService;
  @Autowired
  private PersonConverter personConverter;

  @Override
  public ProjectMemberGet entity2Get(ProjectMember in, Map<String, Integer> idMap) throws Exception {
    ProjectMemberGet out = new ProjectMemberGet();
    BeanUtils.copyProperties(out, in);
    Person person = personService.findOne(in.getPersonId(), idMap);
    out.setPerson(personConverter.entity2Get(person, new MapUtil("id", person.getId()).create()));
    out.setRole(this.personRoleService.findOne(in.getPersonRoleId(), null).getName());
    return out;
  }

  @Override
  public ProjectMemberPut entity2Put(ProjectMember in, Map<String, Integer> idMap) throws Exception {
    ProjectMemberPut out = new ProjectMemberPut();
    BeanUtils.copyProperties(out, in);
    out.setRole(this.personRoleService.findOne(in.getPersonRoleId(), null).getName());
    return out;
  }

  @Override
  public ProjectMember put2Entity(ProjectMemberPut in, Map<String, Integer> idMap) throws Exception {
    ProjectMember out = new ProjectMember();
    Integer memberId = idMap.get("id");
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    PersonRole pr = personRoleService.findByName(in.getRole());
    if (pr != null) {
      out.setPersonRoleId(pr.getId());      
    }
    ProjectMember pm = projectMemberService.findOne(memberId, idMap);
    out.setPersonId(pm.getPersonId());
    out.setId(memberId);
    return out;
  }

  @Override
  public ProjectMember post2Entity(ProjectMemberPost in, Map<String, Integer> idMap) throws Exception {
    ProjectMember out = new ProjectMember();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    PersonRole pr = personRoleService.findByName(in.getRole());
    if (pr != null) {
      out.setPersonRoleId(pr.getId());      
    }
    return out;
  }

}
