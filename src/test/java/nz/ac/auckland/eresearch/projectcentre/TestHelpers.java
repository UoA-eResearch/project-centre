package nz.ac.auckland.eresearch.projectcentre;

import com.jayway.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by markus on 2/12/15.
 */
public class TestHelpers {

  public static Response createEntity(String adminUsername, String adminPassword, String filename, String type) throws IOException {

    File f = new File("seed-data/tests/" + filename);
    String content = new String(Files.readAllBytes(f.toPath()));

    Response r = given().auth().basic(adminUsername, adminPassword)
            .contentType("application/json")
            .body(content)
            .when()
            .post("/api/" + type);

    return r;

  }
}
