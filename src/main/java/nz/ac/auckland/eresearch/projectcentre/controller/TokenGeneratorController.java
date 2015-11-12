package nz.ac.auckland.eresearch.projectcentre.controller;

import nz.ac.auckland.eresearch.projectcentre.util.auth.ApiToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@RestController
@RequestMapping(value = "/authenticate/api")
public class TokenGeneratorController {

  private final String sqlSelect = "SELECT personId FROM person_properties WHERE propname = 'eppn' AND propvalue = ?";
  private final String sqlDelete = "DELETE FROM apitoken WHERE personId = ?";
  private final String sqlInsert = "INSERT INTO apitoken (personId, token, validUntil) VALUES (?, ?, ?)";
  private Logger log = LoggerFactory.getLogger(TokenGeneratorController.class);
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public
  @ResponseBody
  ApiToken generate(HttpServletRequest request) throws Exception {
    String eppn = (String) request.getAttribute("eppn");
    if (eppn == null || eppn.trim().length() == 0) {
      throw new Exception("Failed to generate token. No user identifier found");
    }
    log.debug("Received token request by " + eppn);
    String tokenString = UUID.randomUUID().toString();
    Integer personId = jdbcTemplate.queryForObject(sqlSelect, new Object[]{eppn}, Integer.class);
    if (personId == null) {
      throw new Exception("Failed to generate token. No person found for given identifier");
    }
    Timestamp ts = new Timestamp(System.currentTimeMillis() + (86400 * 1000));
    jdbcTemplate.update(sqlDelete, personId);
    jdbcTemplate.update(sqlInsert, personId, passwordEncoder.encode(tokenString), ts);
    ApiToken token = new ApiToken();
    token.setToken(tokenString);
    token.setValidUntil(ts.toString());
    return token;
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

}
