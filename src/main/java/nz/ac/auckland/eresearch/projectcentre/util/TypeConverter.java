package nz.ac.auckland.eresearch.projectcentre.util;

import java.util.Map;

public interface TypeConverter<T,TGET,TPUT,TPOST> {

  public TGET entity2Get(T t, Map<String, Integer> idMap) throws Exception;
  public TPUT entity2Put(T t, Map<String, Integer> idMap) throws Exception;
  public T put2Entity(TPUT t, Map<String, Integer> idMap) throws Exception;
  public T post2Entity(TPOST t, Map<String, Integer> idMap) throws Exception;
  
}
