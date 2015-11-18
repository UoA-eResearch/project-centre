package nz.ac.auckland.eresearch.projectcentre.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * Created by markus on 16/11/15.
 */
@Component
public class JsonDeserializationHelper {

  @Autowired
  private ObjectMapper om;

  private Repositories repositories = null;

  @Autowired
  public JsonDeserializationHelper(WebApplicationContext appContext) {
    repositories = new Repositories(appContext);
  }

  private <T> CrudRepository<T, Serializable> getCrudRepository(T cls) {
    Object inv = repositories.getRepositoryFor(cls.getClass());
    return (CrudRepository<T, Serializable>) inv;

  }

  public <T> T save(T entity) {
    return getCrudRepository(entity).save(entity);
  }

  public <T> T findOne(Class<T> cls, Serializable id) {
    return (T) getCrudRepository(cls).findOne(id);
  }


}
