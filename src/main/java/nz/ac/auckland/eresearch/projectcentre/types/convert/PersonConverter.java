package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonAffiliationService;
import nz.ac.auckland.eresearch.projectcentre.service.PersonStatusService;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter implements TypeConverter<Person, PersonGet, PersonPut, PersonPost> {

  @Autowired
  PersonStatusService personStatusService;
  @Autowired
  DivisionService divisionService;
  @Autowired
  DivisionalRoleService divisionalRoleService;
  @Autowired
  PersonAffiliationService personAffiliationService;
  @Autowired
  DivisionConverter divisionConverter;
  @Autowired
  PersonAffiliationConverter converterAffilConverter;
  
  @Override
  public PersonGet entity2Get(Person p, Map<String, Integer> idMap) throws Exception {
    PersonGet pg = new PersonGet();
    BeanUtils.copyProperties(pg, p);
    pg.setStatus(this.personStatusService.findOne(p.getStatusId(), null).getName());
    Iterable<PersonAffiliation> pas = this.personAffiliationService.findByPersonId(p.getId());
    List<PersonAffiliationGet> pags = new LinkedList<PersonAffiliationGet>();
    for (PersonAffiliation pa: pas) {
      PersonAffiliationGet pag = new PersonAffiliationGet();
      Division d = this.divisionService.findOne(pa.getDivisionId(), null);
      BeanUtils.copyProperties(pag, pa);
      pag.setDivision(this.divisionConverter.entity2Get(d, null));
      pag.setDivisionalRole(this.divisionalRoleService.findOne(pa.getDivisionalRoleId(), null).getName());
      pags.add(pag);
    }
    pg.setAffiliations(pags);
    return pg;
  }

  @Override
  public PersonPut entity2Put(Person in, Map<String, Integer> idMap) throws Exception {
    PersonPut out = new PersonPut();
    BeanUtils.copyProperties(out, in);
    out.setStatus(this.personStatusService.findOne(in.getStatusId(), idMap).getName());
    return out;
  }

  @Override
  public Person put2Entity(PersonPut in, Map<String, Integer> idMap) throws Exception {
    Person out = new Person();
    BeanUtils.copyProperties(out, in);
    if (in.getStatus() != null) {
      out.setStatusId(this.personStatusService.findByName(in.getStatus()).getId());      
    }
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public Person post2Entity(PersonPost in, Map<String, Integer> idMap) throws Exception {
    Person out = new Person();
    BeanUtils.copyProperties(out, in);
    if (in.getStatus() != null) {
      out.setStatusId(this.personStatusService.findByName(in.getStatus()).getId());      
    }
    return out;
  }

}
