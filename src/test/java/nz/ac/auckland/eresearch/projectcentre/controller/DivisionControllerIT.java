package nz.ac.auckland.eresearch.projectcentre.controller;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import nz.ac.auckland.eresearch.projectcentre.Application;
import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.service.DivisionService;
import nz.ac.auckland.eresearch.projectcentre.util.json.JsonHelpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jayway.restassured.RestAssured.*;
import static nz.ac.auckland.eresearch.projectcentre.TestHelpers.createEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Created by markus on 12/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@IntegrationTest({"server.port=0"})
@ActiveProfiles("test,h2,dev")
@SpringApplicationConfiguration(classes = {Application.class})
public class DivisionControllerIT {


  @Autowired
  private ObjectMapper om;
  @Autowired
  private JsonHelpers jsonHelpers;
  @Autowired
  private DivisionService divServ;
  @Autowired
  private DivisionRepository divRepo;

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
//    this.base = new URL("http://localhost:" + port + "/api/");
//    template = new TestRestTemplate(adminUsername, adminPassword);
    RestAssured.port = port;
    RestAssured.defaultParser = Parser.JSON;

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(adminUsername, adminPassword));

    divRepo.deleteAll();

  }


  @After
  public void tearDown() throws Exception {
  }

  private Division createDivision(int nr) {
    Division d = new Division();
    d.setCode("Division" + nr);
    d.setName("Name Division" + nr);

    d = divServ.create(d);

    return d;

  }

  @Test
  public void testAll() throws Exception {

    List<Division> divs = IntStream.range(1, 10).mapToObj(nr -> createDivision(nr)).collect(Collectors.toList());

    Response r = given().auth().basic(adminUsername, adminPassword)
            .get("/api/division");


    Division[] divsResult = r.as(Division[].class);

    assertThat("there are 9 elements", divsResult.length, is(9));

    for (int i = 0; i < 9; i++) {
      Division it = divsResult[i];
      assertThat("code is Division" + (i + 1), divsResult[i].getCode(), is("Division" + (i + 1)));
      assertThat("name is Name Division" + (i + 1), divsResult[i].getName(), is("Name Division" + (i + 1)));
    }

    System.out.println(divsResult);

  }

  @Test
  public void testGetMany() throws Exception {

    List<Division> divs = IntStream.range(1, 10).mapToObj(nr -> createDivision(nr)).collect(Collectors.toList());

    List<Response> responses = divs.stream().map(d ->
            given().auth().basic(adminUsername, adminPassword)
                    .get("/api/division/" + d.getId())).collect(Collectors.toList());


    responses.forEach(it -> {

              Integer id = it.jsonPath().getInt("id");
              String code = it.jsonPath().getString("code");
              String name = it.jsonPath().getString("name");

              assertThat("id exists", id, notNullValue());
              assertThat("code exists", code, notNullValue());
              assertThat("name exists", name, notNullValue());
              assertThat("name is valid", name, containsString(code));

            }
    );

  }



  @Test
  public void testCreate() throws Exception {

    Response r = createEntity(adminUsername, adminPassword, "create-division.json", "division");

    List<Division> divs = Lists.newArrayList(divServ.findAll());

    assertThat("Exactly one division exists.", divs.size(), equalTo(1));

  }

  @Test
  public void testCreateTwice() throws Exception {

    Response r = createEntity(adminUsername, adminPassword, "create-division.json", "division");

    Response r2 = createEntity(adminUsername, adminPassword, "create-division-same-code.json", "division");

    Map test = r2.as(Map.class);
    assertThat("Status 500 is not present", test.get("status"), equalTo(500));

  }


  @Test
  public void testGet() throws Exception {

    Division d = createDivision(1);

    Response r = given().auth().basic(adminUsername, adminPassword)
            .get("/api/division/" + d.getId());

    Integer id = r.jsonPath().getInt("id");
    String code = r.jsonPath().getString("code");
    String name = r.jsonPath().getString("name");

    assertThat("id does exist", id, notNullValue());
    assertThat("code does exist", code, notNullValue());
    assertThat("code is valid", code, containsString(code));
  }

  @Test
  public void testPut() throws Exception {
    //TODO: tests that change code
  }

  @Test
  public void testCreateWithParent() throws Exception {

    Response r1 = createEntity(adminUsername, adminPassword, "create-division.json", "division");

    Response r2 = createEntity(adminUsername, adminPassword, "create-division-with-parent.json", "division");

    List<Division> divs = divRepo.findAll();

    assertThat("division was created", divs.size(), is(2));

    Division d = divRepo.findByCode("div_test_1");
    assertThat("Parent exists", d, notNullValue());
    assertThat("Parent id does exist", d.getId(), notNullValue());
    assertThat("Parent code does exist", d.getCode(), notNullValue());
    assertThat("Parent name does exist", d.getName(), notNullValue());
    assertThat("Parent does not have parent", d.getParent(), nullValue());

    d = divRepo.findByCode("div_child_1");
    assertThat("Child exiss", d, notNullValue());
    assertThat("Child id does exist", d.getId(), notNullValue());
    assertThat("Child code does exist", d.getCode(), notNullValue());
    assertThat("Child name does exist", d.getName(), notNullValue());
    assertThat("Child does have parent", d.getParent(), notNullValue());
    d = d.getParent();
    assertThat("Parent id does exist", d.getId(), notNullValue());
    assertThat("Parent code does exist", d.getCode(), notNullValue());
    assertThat("Parent name does exist", d.getName(), notNullValue());
    assertThat("Parent does not have parent", d.getParent(), nullValue());

  }

  @Test
  public void testCreateWithGrandParent() throws Exception {

    Response r1 = createEntity(adminUsername, adminPassword,"create-division.json", "division");

    Response r2 = createEntity(adminUsername, adminPassword,"create-division-with-parent.json", "division");

    Response r3 = createEntity(adminUsername, adminPassword,"create-division-with-grandparent.json", "division");

    List<Division> divs = divRepo.findAll();

    assertThat("divisions were created", divs.size(), is(3));

    Division d = divRepo.findByCode("div_test_1");
    assertThat("Parent exists", d, notNullValue());
    assertThat("Parent id does exist", d.getId(), notNullValue());
    assertThat("Parent code does exist", d.getCode(), notNullValue());
    assertThat("Parent name does exist", d.getName(), notNullValue());
    assertThat("Parent does not have parent", d.getParent(), nullValue());

    d = divRepo.findByCode("div_child_1");
    assertThat("Child exiss", d, notNullValue());
    assertThat("Child id does exist", d.getId(), notNullValue());
    assertThat("Child code does exist", d.getCode(), notNullValue());
    assertThat("Child name does exist", d.getName(), notNullValue());
    assertThat("Child does have parent", d.getParent(), notNullValue());
    d = d.getParent();
    assertThat("Parent id does exist", d.getId(), notNullValue());
    assertThat("Parent code does exist", d.getCode(), notNullValue());
    assertThat("Parent name does exist", d.getName(), notNullValue());
    assertThat("Parent does not have parent", d.getParent(), nullValue());


    d = divRepo.findByCode("div_grandchild_1");
    assertThat("Child exiss", d, notNullValue());
    assertThat("Child id does exist", d.getId(), notNullValue());
    assertThat("Child code does exist", d.getCode(), notNullValue());
    assertThat("Child name does exist", d.getName(), notNullValue());
    assertThat("Child does have parent", d.getParent(), notNullValue());
    d = d.getParent();
    assertThat("Parent id does exist", d.getId(), notNullValue());
    assertThat("Parent code does exist", d.getCode(), notNullValue());
    assertThat("Parent name does exist", d.getName(), notNullValue());
    assertThat("Parent does have parent", d.getParent(), notNullValue());
    d = d.getParent();
    assertThat("Grandparent id does exist", d.getId(), notNullValue());
    assertThat("Grandparent code does exist", d.getCode(), notNullValue());
    assertThat("Grandparent name does exist", d.getName(), notNullValue());
    assertThat("Grandparent does not have parent", d.getParent(), nullValue());

  }



  @Test
  public void testParentDoesNotExist() throws Exception {
    Response r = createEntity(adminUsername, adminPassword,"create-division-with-parent.json", "division");

    Map test = r.as(Map.class);

    assertThat("Status 400 is present", test.get("status"), equalTo(400));


    assertThat("division was created", divRepo.findAll().size(), is(0));
    System.out.println(r);
  }

  @Test
  public void testDelete() throws Exception {

    Division d = createDivision(1);

    Response r = given().auth().basic(adminUsername, adminPassword)
            .delete("/api/division/" + d.getId());

    System.out.println(r);
    assertThat("no division exists", divRepo.findAll().size(), is(0));

  }
}