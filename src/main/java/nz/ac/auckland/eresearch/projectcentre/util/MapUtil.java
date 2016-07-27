package nz.ac.auckland.eresearch.projectcentre.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

  private Map<String, Integer> map;
  
  public MapUtil(String s, Integer i) {
    this.map = new HashMap<String, Integer>();
    this.map.put(s, i);
  }
  
  public MapUtil add(String s, Integer i) {
    this.map.put(s, i);
    return this;
  }
  
  public Map<String, Integer> create() {
    return this.map;
  }
}
