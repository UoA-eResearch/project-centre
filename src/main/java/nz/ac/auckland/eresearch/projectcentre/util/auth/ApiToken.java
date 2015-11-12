package nz.ac.auckland.eresearch.projectcentre.util.auth;


public class ApiToken {

  private String token;
  private String validUntil;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(String validUntil) {
    this.validUntil = validUntil;
  }

}
