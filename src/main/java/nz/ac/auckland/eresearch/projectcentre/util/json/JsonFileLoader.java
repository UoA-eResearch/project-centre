package nz.ac.auckland.eresearch.projectcentre.util.json;

import nz.ac.auckland.eresearch.projectcentre.entity.Institution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by markus on 12/11/15.
 */
public class JsonFileLoader {


  public static List<Institution> loadInstitutions(String filename) throws IOException {

    String content = new String(Files.readAllBytes(Paths.get(filename)));

    System.out.println(content);
    return null;
  }
}
