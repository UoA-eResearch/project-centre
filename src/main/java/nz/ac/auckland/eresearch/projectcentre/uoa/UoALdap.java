package nz.ac.auckland.eresearch.projectcentre.uoa;

import com.google.common.collect.Sets;

import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;

import groovy.transform.Synchronized;

import nz.ac.auckland.eresearch.projectcentre.exceptions.LdapImportException;
import nz.ac.auckland.eresearch.projectcentre.exceptions.LdapQueryException;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.net.SocketFactory;

/**
 * Created by markus on 23/11/15.
 */
@Component
public class UoALdap {

  public static final List<String> DEFAULT_ATTRIBUTE_LIST = Lists.newArrayList("dn", "givenName", "department", "sn", "mail", "memberOf");
  public static final String DEFAULT_BASE_DN = "ou=People,dc=UoA,dc=auckland,dc=ac,dc=nz";
  public static final Pattern GROUP_REGULAR_EXPRESSION = Pattern.compile("^CN=([A-Z]*)\\.uos,OU=uos,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz$");

  public static final String STAFF_GROUP = "CN=UniStaff.ec,OU=ec,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz";
  public static final String STUDENT_GROUP = "CN=Enrolled.ec,OU=ec,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz";
  public static final String POSTGRAD_GROUP="CN=Postgraduate.psrwi,OU=psrwi,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz";
  public static final String DOCTORAL_STUDENT_GROUP="CN=doctoralstudent.psrwi,OU=psrwi,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz";
  public static final String CONTRACTOR_GROUP="CN=Contractor.psrwi,OU=psrwi,OU=Groups,DC=UoA,DC=auckland,DC=ac,DC=nz";

  public static final Set<String> ROLE_GROUPS = Sets.newHashSet(STAFF_GROUP, STUDENT_GROUP, POSTGRAD_GROUP, DOCTORAL_STUDENT_GROUP, CONTRACTOR_GROUP);


  private LDAPConnection connection;
  @Value("${uoa.ldap.username}")
  private String username;
  @Value("${uoa.ldap.password}")
  private String password;
  @Value("${uoa.ldap.hostname}")
  private String hostname;
  @Value("${uoa.ldap.port}")
  private Integer port;

  public UoALdap() {


  }

  private static String generateSearchFilterPerson(String filter) {
    String searchfilter = "(&(" + filter + ")(objectCategory=person)(objectClass=user))";
    return searchfilter;
  }

  @Synchronized
  private LDAPConnection getConnection() {
    if (connection == null) {
      // Use no key manager, and trust all certificates. This would not be used
      // in non-trivial code.
      SSLUtil sslUtil = new SSLUtil(null, new TrustAllTrustManager());

      SocketFactory socketFactory;
      // Create the socket factory that will be used to make a secure
      // connection to the server.
      try {
        socketFactory = sslUtil.createSSLSocketFactory();
        connection = new LDAPConnection(socketFactory, hostname, port, username, password);

      } catch (GeneralSecurityException e) {
        throw new RuntimeException("Can't create ssl socket factory", e);
      } catch (LDAPException e) {
        throw new RuntimeException("Can't connect to uoa.", e);
      }
    }
    return connection;
  }

  private void collect(SearchResult res, ResultCollector col) throws LdapImportException {
    for (SearchResultEntry sre : res.getSearchEntries()) {
      col.addEntry(sre);
    }
  }

  public void searchUser(String searchTerm, ResultCollector col) throws LdapQueryException {

    String searchfilter = generateSearchFilterPerson("displayName=" + searchTerm);
    queryLdap(searchfilter, col, false);

  }

  public void searchUserFirstLastName(String firstname, String lastName, ResultCollector col) throws LdapQueryException {
    String searchfilter = "(&(sn=" + lastName + ")(givenName=" + firstname + "))";
    queryLdap(searchfilter, col, false);
  }

  public void searchUpi(String upi, ResultCollector col) throws LdapQueryException {
    String searchfilter = "(cn=" + upi + ")";
    queryLdap(searchfilter, col, true);
  }

  public void searchUpis(String upi, ResultCollector col) throws LdapQueryException {
    String searchfilter = "(cn=" + upi + ")";
    queryLdap(searchfilter, col, false);
  }

  public void queryLdap(String searchFilter, ResultCollector col, Boolean oneResult) throws LdapQueryException {

    List<String> attrlist = col.getAttributeList();

    if (attrlist == null || attrlist.size() == 0) {
      attrlist = DEFAULT_ATTRIBUTE_LIST;
    }

    try {
      SearchRequest searchRequest =
              new SearchRequest(DEFAULT_BASE_DN, SearchScope.SUB, searchFilter);


      ASN1OctetString cookie = null;
      do {
        searchRequest.setControls(
                new Control[]{new SimplePagedResultsControl(1000, cookie)});
        SearchResult searchResult = getConnection().search(searchRequest);

        if (oneResult) {
          if (searchResult.getEntryCount() > 1) {
            throw new LdapQueryException("More than a single result found.");
          }
        }

        collect(searchResult, col);

        cookie = null;
        for (Control c : searchResult.getResponseControls()) {
          if (c instanceof SimplePagedResultsControl) {
            cookie = ((SimplePagedResultsControl) c).getCookie();
          }
        }
      } while ((cookie != null) && (cookie.getValueLength() > 0));

    } catch (LDAPException le) {
      throw new LdapQueryException("Could not query for: " + searchFilter + ". Error: " + le.getExceptionMessage(), le);
    }
  }


}
