package nz.ac.auckland.eresearch.projectcentre.util;

public interface IdNameTypeService<T> extends BaseService<T> {
  
  public T findByName(String name);

}
