package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.ExternalReferenceService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferenceGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferencePost;
import nz.ac.auckland.eresearch.projectcentre.types.api.ProjectExternalReferencePut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectExternalReference;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalReferenceConverter implements TypeConverter<ProjectExternalReference, ProjectExternalReferenceGet, ProjectExternalReferencePut, ProjectExternalReferencePost> {

  @Autowired
  private PersonService personService;
  @Autowired
  private ExternalReferenceService externalReferenceService;
  
  @Override
  public ProjectExternalReferenceGet entity2Get(ProjectExternalReference in, Map<String, Integer> idMap) throws Exception {
    ProjectExternalReferenceGet out = new ProjectExternalReferenceGet();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public ProjectExternalReferencePut entity2Put(ProjectExternalReference in, Map<String, Integer> idMap) throws Exception {
    ProjectExternalReferencePut out = new ProjectExternalReferencePut();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

  @Override
  public ProjectExternalReference put2Entity(ProjectExternalReferencePut in, Map<String, Integer> idMap) throws Exception {
    ProjectExternalReference out = this.post2Entity(in, idMap);
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public ProjectExternalReference post2Entity(ProjectExternalReferencePost in, Map<String, Integer> idMap) throws Exception {
    ProjectExternalReference out = new ProjectExternalReference();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    return out;
  }

}
