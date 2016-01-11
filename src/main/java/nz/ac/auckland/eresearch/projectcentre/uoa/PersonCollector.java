package nz.ac.auckland.eresearch.projectcentre.uoa;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.unboundid.ldap.sdk.SearchResultEntry;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.exceptions.LdapImportException;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionalRoleService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Created by markus on 23/11/15.
 */
public class PersonCollector implements ResultCollector<Person> {

  // Not sure if that should go into a external file, or smarter method.
  public static final Map<String, String> LDAP_ROLE_TRANSLATION_MAP =
          new ImmutableMap.Builder<String, String>()
                  .put(UoALdap.STAFF_GROUP, "Staff or Post Doc")
                  .put(UoALdap.POSTGRAD_GROUP, "Staff or Post Doc")
                  .put(UoALdap.STUDENT_GROUP, "Non-PhD Student")
                  .put(UoALdap.DOCTORAL_STUDENT_GROUP, "PhD Student")
                  .put(UoALdap.CONTRACTOR_GROUP, "Visitor")
                  .put("Unknown", "Unknown")
                  .build();

  List<Person> persons = Lists.newArrayList();
  private Logger log = LoggerFactory.getLogger(getClass());
  private DivisionService divServ;
  private DivisionalRoleService divRoleSer;

  private Division topLevelDivision = null;

  public PersonCollector(DivisionService divServ, DivisionalRoleService divRoleSer) {
    this.divServ = divServ;
    this.divRoleSer = divRoleSer;

    topLevelDivision = this.divServ.findByCode("UOFAK");
  }

  @Override
  public List<String> getAttributeList() {
    return Lists.newArrayList("dn", "givenName", "department", "sn", "mail", "memberOf", "telephoneNumber");
  }

  @Override
  public void addEntry(SearchResultEntry sre) throws LdapImportException {

    String cn = sre.getAttributeValue("cn");
    String sn = sre.getAttributeValue("sn");
    String givenName = sre.getAttributeValue("givenName");
    String departement = sre.getAttributeValue("department");
    String mail = sre.getAttributeValue("mail");
    List<String> memberships = Arrays.asList(sre.getAttributeValues("memberOf"));
    String phone = sre.getAttributeValue("telephoneNumber");

    Collection roles = CollectionUtils.intersection(UoALdap.ROLE_GROUPS, memberships);

    if (roles.size() > 1) {
      log.info("Multiple roles detected for {}: {}", cn, StringUtils.join(roles));
      if (roles.contains(UoALdap.STAFF_GROUP) || roles.contains(UoALdap.POSTGRAD_GROUP)) {
        roles = Sets.newHashSet(UoALdap.STAFF_GROUP);
      } else if (roles.contains(UoALdap.DOCTORAL_STUDENT_GROUP)) {
        roles = Sets.newHashSet(UoALdap.DOCTORAL_STUDENT_GROUP);
      } else if (roles.contains(UoALdap.STUDENT_GROUP)) {
        roles = Sets.newHashSet(UoALdap.STUDENT_GROUP);
      } else {
        log.info("Multiple roles detected, and can't auto-determine highest group.");
      }
      log.info("Using role: {}", roles.iterator().next());
    } else if (roles.size() == 0) {
      log.info("No role found for ldap entry for '{}', using 'Unknown' role...", cn);
      roles.add("Unknown");
    }

    String role = LDAP_ROLE_TRANSLATION_MAP.get(roles.iterator().next());

    int roleId = divRoleSer.findByName(role).getId();

    Set<String> membershipCodes = memberships.stream().map(
            dn -> UoALdap.GROUP_REGULAR_EXPRESSION
                    .matcher(dn))
            .filter(Matcher::matches)
            .map(m -> m.group(1))
            .collect(Collectors.toSet());

    Set<Division> divs = divServ.filterTopMostMemberDivisions(membershipCodes);

    if ( divs.size() == 0 ) {
      log.info("Could not find any divisions for ldap entry {}. Using top-level.", cn);
      divs.add(topLevelDivision);
    }

    Person p = new Person();
    p.setFullName(givenName + " " + sn);
    p.setEmail(mail);
    p.setPhone(phone);

    divs.forEach(d -> p.addAffiliation(d.getId(), roleId));

    persons.add(p);
  }

  @Override
  public List<Person> getResults() {
    return persons;
  }
}
