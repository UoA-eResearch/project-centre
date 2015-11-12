package nz.ac.auckland.eresearch.projectcentre.service;

public abstract class BaseService<T> {

  abstract public T findOne(Integer id);

  abstract public Iterable<T> findAll();

  abstract public T create(T entity);

  abstract public T update(T entity) throws Exception;

  abstract public void delete(Integer id);
}
