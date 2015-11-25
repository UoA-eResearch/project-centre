package nz.ac.auckland.eresearch.projectcentre.uoa;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Created by markus on 23/11/15.
 */
public class SimplePerson {

  private String cn;
  private String givenName;
  private String department;
  private String sn;
  private String mail;
  private Set<String> memberOf;

  public String getCn() {
    return cn;
  }

  public void setCn(String cn) {
    this.cn = cn;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public Set<String> getMemberOf() {
    return memberOf;
  }

  public void setMemberOf(List<String> memberOf) {

    Set<String> members = memberOf.stream().map(
            dn -> UoALdap.GROUP_REGULAR_EXPRESSION
                    .matcher(dn))
            .filter(Matcher::matches)
            .map(m -> m.group(1))
            .collect(Collectors.toSet());

    this.memberOf = members;
  }

  @Override
  public String toString() {
    return "SimplePerson{" +
            "cn='" + cn + '\'' +
            ", givenName='" + givenName + '\'' +
            ", department='" + department + '\'' +
            ", sn='" + sn + '\'' +
            ", mail='" + mail + '\'' +
            ", memberOf=" + memberOf +
            '}';
  }
}
