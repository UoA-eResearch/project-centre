package nz.ac.auckland.eresearch.projectcentre.uoa;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Strings;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by markus on 23/11/15.
 */
public class UoAGroups {

  private String pathToExcelFile = null;

  public UoAGroups(String hierarchyLocation) {
    this.pathToExcelFile = hierarchyLocation;
  }

  public List<String> getUoAGroups() {

    List<List<String>> table = Lists.newArrayList();
    try {
      FileInputStream file = new FileInputStream(pathToExcelFile);
      XSSFWorkbook workbook = new XSSFWorkbook(file);

//Get first/desired sheet from the workbook
      XSSFSheet sheet = workbook.getSheetAt(0);

      //Iterate through each rows one by one
      Iterator<Row> rowIterator = sheet.iterator();

      Division root = new Division();
      // we don't need the first row
      Row row = rowIterator.next();

      while (rowIterator.hasNext()) {
        List<String> line = Lists.newArrayList();
        row = rowIterator.next();
        //For each row, iterate through all the columns
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          String cellValue = cell.getStringCellValue();
          if (Strings.isNullOrEmpty(cellValue)) {
            continue;
          }
          line.add(cellValue);
        }
        table.add(line);

      }
      file.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not open hierarchy Excel file "+pathToExcelFile, e);
    }

    Map<String, String> codes = Maps.newLinkedHashMap();
    codes.put("UOFAK", "University of Auckland");
    Map<String, String> parents = Maps.newLinkedHashMap();
    parents.put("UOFAK", null);
    for ( List<String> line : table ) {
      String parent = "UOFAK";
      for (int i=1; i<line.size(); i=i+2) {
        String code = line.get(i);
        if (Strings.isNullOrEmpty(parents.get(code)) ) {
          parents.put(code, parent);
          String name = line.get(i+1);
          codes.put(code, name);
        }

        parent = code;

      }
    }

    List<String> jsonStrings = Lists.newArrayList();

    for ( String c : parents.keySet() ) {
      String name = codes.get(c);
      String p = parents.get(c);
      String json = "{\"name\": \""+name+"\", \"code\":\""+c+"\"";
      if ( Strings.isNullOrEmpty(p)) {
        json = json +"}";
      } else {
        json = json + ", \"parentCode\": \""+p+"\"}";
      }
      jsonStrings.add(json);
    }

    return jsonStrings;
  }
}
