package nz.ac.auckland.eresearch.projectcentre.uoa;

import com.google.common.collect.Lists;

import com.unboundid.ldap.sdk.SearchResultEntry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by markus on 23/11/15.
 */
public class SimpleResultCollector implements ResultCollector<SimplePerson> {

  List<SimplePerson> persons = Lists.newArrayList();

  @Override
  public List<String> getAttributeList() {
    return UoALdap.DEFAULT_ATTRIBUTE_LIST;
  }

  @Override
  public void addEntry(SearchResultEntry sre) {

    String cn = sre.getAttributeValue("cn");
    String sn = sre.getAttributeValue("sn");
    String givenName = sre.getAttributeValue("givenName");
    String departement = sre.getAttributeValue("department");
    String mail = sre.getAttributeValue("mail");
    String[] memberships = sre.getAttributeValues("memberOf");

    SimplePerson p = new SimplePerson();
    p.setCn(cn);
    p.setSn(sn);
    p.setGivenName(givenName);
    p.setDepartment(departement);
    p.setMail(mail);
    if (memberships != null) {
      p.setMemberOf(Arrays.asList(memberships));
    }

    persons.add(p);
  }

  @Override
  public List<SimplePerson> getResults() {
    return persons;
  }
}
