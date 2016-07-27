package nz.ac.auckland.eresearch.projectcentre.service;

import java.util.Map;

public abstract class BaseService2<T> {

  abstract public T findOne(Integer id, Map<String, Integer> idMap) throws Exception;
  abstract public Iterable<T> findAll(Map<String, Integer> idMap) throws Exception;
  abstract public T create(T entity) throws Exception;
  abstract public T update(T entity) throws Exception;
  abstract public void delete(Integer id) throws Exception;
  
}
