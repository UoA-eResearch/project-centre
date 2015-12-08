package nz.ac.auckland.eresearch.projectcentre.controller;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import nz.ac.auckland.eresearch.projectcentre.Application;
import nz.ac.auckland.eresearch.projectcentre.entity.Person;
import nz.ac.auckland.eresearch.projectcentre.repositories.IdentityRepository;
import nz.ac.auckland.eresearch.projectcentre.repositories.PersonRepository;
import nz.ac.auckland.eresearch.projectcentre.service.PersonService;
import nz.ac.auckland.eresearch.projectcentre.util.clRunners.CreateAdminAccount;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jayway.restassured.RestAssured.*;
import static nz.ac.auckland.eresearch.projectcentre.TestHelpers.createEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by markus on 12/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@IntegrationTest({"server.port=0"})
@ActiveProfiles("test,h2,minimal,dev")
@SpringApplicationConfiguration(classes = {Application.class})
public class PersonControllerIT {


  @Autowired
  private ObjectMapper om;
  @Autowired
  private JsonHelpers jsonHelpers;
  @Autowired
  private PersonService personServ;
  @Autowired
  private PersonRepository personRepo;
  @Autowired
  private IdentityRepository idRepo;
  @Autowired
  private CreateAdminAccount adminAccount;

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

    idRepo.deleteAll();
    personRepo.deleteAll();

    adminAccount.createAdminAccount();


  }


  @After
  public void tearDown() throws Exception {
  }


  private Person createPerson(int nr, boolean persist) {

    Person p = new Person();
    p.setFullName("First" + nr + " Last" + nr);
    p.setEmail("email" + nr + "@mail.com");
    p.setPhone("1234 ext. " + nr);
    p.setPreferredName("Pref First Last " + nr);


    if (persist) {
      personServ.create(p);
    }

    return p;
  }

  @Test
  public void testAll() throws Exception {

    List<Person> divs = IntStream.range(1, 10).mapToObj(nr -> createPerson(nr, true)).collect(Collectors.toList());

    Response r = given().auth().basic(adminUsername, adminPassword)
            .get("/api/person");


    Person[] personResult = r.as(Person[].class);

    assertThat("there are 10 elements", personResult.length, is(10));

    for (int i = 1; i < 10; i++) {
      Person p = personResult[i];
      if (p.getFullName().equals(adminUsername)) {
        continue;
      }
      assertThat("Person has id", personResult[i].getId(), notNullValue());
      assertThat("Person name ends with " + i, personResult[i].getFullName(), endsWith("Last" + i));
      assertThat("Person email is correct", personResult[i].getEmail(), is("email" + i + "@mail.com"));
      assertThat("Person phone is correct", personResult[i].getPhone(), is("1234 ext. " + i));
      assertThat("Person prefferred name is correct", personResult[i].getPreferredName(), is("Pref First Last " + i));
    }


  }

  @Test
  public void testGetMany() throws Exception {

    List<Person> divs = IntStream.range(1, 10).mapToObj(nr -> createPerson(nr, true)).collect(Collectors.toList());

    List<Response> responses = divs.stream().map(d ->
            given().auth().basic(adminUsername, adminPassword)
                    .get("/api/person/" + d.getId())).collect(Collectors.toList());


    responses.forEach(it -> {

              Integer id = it.jsonPath().getInt("id");
              String name = it.jsonPath().getString("fullName");
              String email = it.jsonPath().getString(("email"));

              assertThat("id exists", id, notNullValue());
              assertThat("name exists", name, notNullValue());
              assertThat("email exists", email, notNullValue());
            }
    );

  }

  @Test
  public void testCreate() throws Exception {

    Response r = createEntity(adminUsername, adminPassword, "create-person.json", "person");

    List<Person> persons = Lists.newArrayList(personRepo.findAll());

    assertThat("Exactly one person exists.", persons.size(), equalTo(2));

    Person p = persons.get(1); // we don't want the admin

    assertThat("Person has id", p.getId(), notNullValue());
    assertThat("Person has name", p.getFullName(), notNullValue());
    assertThat("Person has email", p.getEmail(), notNullValue());
    assertThat("Person has phone", p.getPhone(), notNullValue());
    assertThat("Person has startDate", p.getStartDate(), notNullValue());
    assertThat("Person has note", p.getNotes(), notNullValue());
    assertThat("Person has status", p.getStatusId(), notNullValue());
  }

  @Test
  public void testGet() throws Exception {

    Person p = createPerson(1, true);

    Response r = given().auth().basic(adminUsername, adminPassword)
            .get("/api/person/" + p.getId());

    Integer id = r.jsonPath().getInt("id");
    String email = r.jsonPath().getString("email");
    String name = r.jsonPath().getString("fullName");

    assertThat("id does exist", id, notNullValue());
    assertThat("email does exist", email, notNullValue());
    assertThat("name does exist", name, notNullValue());
  }

  @Test
  public void testCreateWithChildAffiliation() throws Exception {

    Response r1 = createEntity(adminUsername, adminPassword, "create-division.json", "division");

    Response r2 = createEntity(adminUsername, adminPassword, "create-division-with-parent.json", "division");

    Response r3 = createEntity(adminUsername, adminPassword, "create-person-with-single-affiliation.json", "person");

    Person p = personServ.findByEmail("email_affil@auckland.ac.nz");

    assertThat("person was created", p, notNullValue());
    assertThat("person affiliation exists", p.getAffiliations().size(), equalTo(1));


  }


}