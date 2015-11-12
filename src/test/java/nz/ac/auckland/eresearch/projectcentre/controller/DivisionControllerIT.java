package nz.ac.auckland.eresearch.projectcentre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

import nz.ac.auckland.eresearch.projectcentre.Application;
import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URL;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Created by markus on 12/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@ActiveProfiles("test")
public class DivisionControllerIT {

  @Autowired
  private ObjectMapper om;

  private URL base;
  private TestRestTemplate template;

  @Value("${admin.username}")
  private String adminUsername;
  @Value("${admin.password}")
  private String adminPassword;

  @Value("${local.server.port}")
  private int port;

  @Before
  public void setUp() throws Exception {
    this.base = new URL("http://localhost:" + port + "/api/");
    template = new TestRestTemplate(adminUsername, adminPassword);
    RestAssured.port = port;
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetAll() throws Exception {

    Division[] divs = given().auth().basic(adminUsername, adminPassword)
            .get("/api/division")
            .as(Division[].class);

    assertThat(divs.length, equalTo(8));

    assertThat(divs[7].getParent().getParent().getCode(), equalTo("div10"));

  }

  @Test
  public void testCreate() throws Exception {

  }

  @Test
  public void testGet() throws Exception {

  }

  @Test
  public void testPut() throws Exception {

  }

  @Test
  public void testPatch() throws Exception {

  }

  @Test
  public void testDelete() throws Exception {

  }
}