package nz.ac.auckland.eresearch.projectcentre.types.convert;

import java.util.Map;

import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationGet;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationPost;
import nz.ac.auckland.eresearch.projectcentre.types.api.PersonAffiliationPut;
import nz.ac.auckland.eresearch.projectcentre.types.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.types.entity.DivisionalRole;
import nz.ac.auckland.eresearch.projectcentre.types.entity.PersonAffiliation;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonAffiliationConverter implements TypeConverter<PersonAffiliation, PersonAffiliationGet, PersonAffiliationPut, PersonAffiliationPost> {

  @Autowired
  DivisionService divisionService;
  @Autowired
  DivisionalRoleService divisionalRoleService;
  @Autowired
  private DivisionConverter divisionConverter;
  
  @Override
  public PersonAffiliationGet entity2Get(PersonAffiliation pa, Map<String, Integer> idMap) throws Exception {
    PersonAffiliationGet pag = new PersonAffiliationGet();
    pag.setId(pa.getId());
    pag.setDivisionalRole(this.divisionalRoleService.findOne(pa.getDivisionalRoleId(), null).getName());
    Division d = this.divisionService.findOne(pa.getDivisionId(), null);
    pag.setDivision(this.divisionConverter.entity2Get(d, idMap));
    return pag;
  }

  @Override
  public PersonAffiliationPut entity2Put(PersonAffiliation pa, Map<String, Integer> idMap) throws Exception {
    PersonAffiliationPut pap = new PersonAffiliationPut();
    BeanUtils.copyProperties(pap, pa);
    pap.setDivisionalRole(this.divisionalRoleService.findOne(pa.getDivisionalRoleId(), null).getName());
    return pap;
  }

  @Override
  public PersonAffiliation put2Entity(PersonAffiliationPut in, Map<String, Integer> idMap) throws Exception {
    PersonAffiliation out = this.post2Entity(in, idMap);
    out.setId(idMap.get("id"));
    return out;
  }

  @Override
  public PersonAffiliation post2Entity(PersonAffiliationPost in, Map<String, Integer> idMap) throws Exception {
    PersonAffiliation out = new PersonAffiliation();
    BeanUtils.copyProperties(out, in);
    BeanUtils.populate(out, idMap);
    DivisionalRole role = divisionalRoleService.findByName(in.getDivisionalRole());
    if (role != null) {
      out.setDivisionalRoleId(role.getId());
    }
    return out;
  }

}
