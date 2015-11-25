package nz.ac.auckland.eresearch.projectcentre.uoa;

/**
 * Created by markus on 23/11/15.
 */

import com.unboundid.ldap.sdk.SearchResultEntry;

import nz.ac.auckland.eresearch.projectcentre.exceptions.LdapImportException;

import java.util.List;

public interface ResultCollector<T> {

  List<String> getAttributeList();

  void addEntry(SearchResultEntry sre) throws LdapImportException;

  List<T> getResults();
}
