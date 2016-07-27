package nz.ac.auckland.eresearch.projectcentre.controller;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nz.ac.auckland.eresearch.projectcentre.util.BaseService;
import nz.ac.auckland.eresearch.projectcentre.util.NullAwareBeanUtilsBean;
import nz.ac.auckland.eresearch.projectcentre.util.TypeConverter;
import nz.ac.auckland.eresearch.projectcentre.validation.MyValidator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class BaseController<T, TGET, TPUT, TPOST> {

  protected Logger log = LoggerFactory.getLogger(getClass());
  private BaseService<T> service;
  private TypeConverter<T, TGET, TPUT, TPOST> converter;
  private Validator[] validators;

  public BaseController(BaseService<T> service, TypeConverter<T, TGET, TPUT, TPOST> converter, 
      Validator... validators) {
    this.service = service;
    this.converter = converter;
    this.validators = validators;
  }

  public ResponseEntity<List<TGET>> getAll(Map<String, Integer> idMap) throws Exception {
    log.debug("GET all");
    return new ResponseEntity<List<TGET>>(this.getAllRaw(idMap), HttpStatus.OK);
  }

  public ResponseEntity<Void> create(TPOST in, Map<String, Integer> idMap, HttpServletRequest request) throws Exception {
    T entity = this.createWithoutResponse(in, idMap, request);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(this.createLocation(request, entity));
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  public T createWithoutResponse(TPOST in, Map<String, Integer> idMap, HttpServletRequest request) throws Exception {
    log.debug("POST");
    T entity = this.converter.post2Entity(in, idMap);
    new MyValidator(validators).validate(entity);
    entity = this.service.create(entity);
    return entity;
  }

  public ResponseEntity<TGET> get(Map<String, Integer> idMap) throws Exception {
    log.debug("GET");
    TGET result = this.getRaw(idMap);
    if (result == null) {
      return new ResponseEntity<TGET>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<TGET>(result, HttpStatus.OK);
  }

  public @ResponseBody ResponseEntity<Void> put(Map<String, Integer> idMap, TPUT in) throws Exception {
    log.debug("PUT");
    T oldEntity = this.service.findOne(idMap.get("id"), idMap);
    if (oldEntity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    } 
    T updatedEntity = this.converter.put2Entity(in, idMap);
    new MyValidator(validators).validate(updatedEntity);
    this.service.update(updatedEntity);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public ResponseEntity<Void> patch(Map<String, Integer> idMap, TPUT in) throws Exception {
    log.debug("PATCH");
    T oldEntity = this.service.findOne(idMap.get("id"), idMap);
    if (oldEntity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
    TPUT tput = converter.entity2Put(oldEntity, idMap);
    new NullAwareBeanUtilsBean().copyProperties(tput, in);
    T updatedEntity = converter.put2Entity(tput, idMap);
    new MyValidator(this.validators).validate(updatedEntity);
    this.service.update(updatedEntity);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public @ResponseBody ResponseEntity<Void> delete(Map<String, Integer> idMap) throws Exception {
    log.debug("DELETE");
    Integer id = idMap.get("id");
    T entity = this.service.findOne(id, idMap);
    if (entity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);      
    }
    this.service.delete(id);
    //TGET object = this.converter.entity2Get(entity, idMap);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public TGET getRaw(Map<String, Integer> idMap) throws Exception {
    T entity = this.service.findOne(idMap.get("id"), idMap);
    if (entity != null) {
      return this.converter.entity2Get(entity, idMap);      
    }
    return null;
  }

  public List<TGET> getAllRaw(Map<String, Integer> idMap) throws Exception {
    Iterable<T> objects = this.service.findAll(idMap);
    List<TGET> result = new LinkedList<TGET>();
    for (T t: objects) {
      result.add(this.converter.entity2Get(t, idMap));
    }
    return result;
  }

  private URI createLocation(HttpServletRequest request, T newObject) throws Exception {
    StringBuilder url = new StringBuilder(request.getRequestURL().toString());
    if (!url.toString().endsWith("/")) {
      url.append("/");
    }
    url.append(BeanUtils.getSimpleProperty(newObject, "id"));
    return new URI(url.toString());
  }

}
