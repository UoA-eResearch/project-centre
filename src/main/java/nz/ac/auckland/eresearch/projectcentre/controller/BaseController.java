package nz.ac.auckland.eresearch.projectcentre.controller;

import com.google.common.collect.Lists;

import nz.ac.auckland.eresearch.projectcentre.service.BaseService;
import nz.ac.auckland.eresearch.projectcentre.validation.MyValidator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController<T, ID extends Serializable> {

  protected Logger log = LoggerFactory.getLogger(getClass());
  private BaseService<T> service;
  private Validator[] validators;

  public BaseController(BaseService<T> service, Validator... validators) {
    this.service = service;
    this.validators = validators;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public
  @ResponseBody
  List<T> getAll() {
    log.debug("GET all");
    Iterable<T> result = this.service.findAll();
    return Lists.newArrayList(result);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public
  @ResponseBody
  ResponseEntity<?> create(@RequestBody T domainObject, HttpServletRequest request) throws Exception {
    log.debug("POST");
    PropertyUtils.setProperty(domainObject, "id", null);
    new MyValidator(this.validators).validate(domainObject);
    domainObject = this.service.create(domainObject);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(this.createLocation(request, domainObject));
    return new ResponseEntity<T>(domainObject, headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  ResponseEntity<?> get(@PathVariable Integer id) {
    log.debug("GET id " + id);
    T entity = this.service.findOne(id);
    if (entity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<T>(entity, HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public
  @ResponseBody
  ResponseEntity<?> put(@PathVariable Integer id, @RequestBody T domainObject) throws Exception {
    log.debug("POST id " + id);
    BeanUtils.setProperty(domainObject, "id", id);
    new MyValidator(this.validators).validate(domainObject);
    T oldEntity = this.service.findOne(id);
    if (oldEntity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    } else {
      this.service.update(domainObject);
      return new ResponseEntity<Void>(HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public
  @ResponseBody
  ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody HashMap<String, Object> params) throws Exception {
    log.debug("PUT id " + id);
    params.put("id", id); // just in case somebody uses different id in body
    T oldEntity = this.service.findOne(id); // potentially cached, so overwrite in new instance
    if (oldEntity == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    } else {
      T newEntity = ((Class<T>) oldEntity.getClass()).newInstance();
      BeanUtils.copyProperties(newEntity, oldEntity); // copy from old to new
      BeanUtils.populate(newEntity, params); // apply requested changes
      new MyValidator(this.validators).validate(newEntity); // validate
      this.service.update(newEntity);
      return new ResponseEntity<Void>(HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public
  @ResponseBody
  ResponseEntity<?> delete(@PathVariable Integer id) {
    log.debug("DELETE id " + id);
    try {
      this.service.delete(id);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    } catch (EmptyResultDataAccessException e) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
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
