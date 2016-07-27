package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.ProjectService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceInstanceStatusService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceSchemaService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceInstance;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServiceInstanceValidator implements Validator {

  protected Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  ProjectService projectService;
  @Autowired
  ServiceService serviceService;
  @Autowired
  ServiceSchemaService serviceSchemaService;
  @Autowired
  ServiceInstanceStatusService serviceInstanceStatusService;
  @Autowired
  JsonSchemaValidator jsonSchemaValidator;

  @Override
  public boolean supports(Class<?> clazz) {
    return ServiceInstance.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object object, Errors errors) {
    ServiceInstance si = (ServiceInstance) object;
    validationUtil.checkNotEmpty(errors, new String[]{"instance", "serviceId", 
        "schemaId", "projectId", "statusId"});
    if (!errors.hasFieldErrors()) {
      if (null == serviceService.findOne(si.getServiceId(), null)) {
        errors.rejectValue("serviceId", "Invalid service id");
      }
      if (null == projectService.findOne(si.getProjectId(), null)) {
        errors.rejectValue("projectId", "Invalid project id");
      }
      if (null == serviceSchemaService.findOne(si.getSchemaId(), null)) {
        errors.rejectValue("schemaId", "Invalid schema id");
      }
      if (null == serviceInstanceStatusService.findOne(si.getStatusId(), null)) {
        errors.rejectValue("statusId", "Invalid status id");
      }
      if (!errors.hasFieldErrors()) {
        this.validateAgainstSchema(si.getSchemaId(), si.getInstance(), errors);
      }
    }
  }
  
  private void validateAgainstSchema(Integer schemaId, String instance, Errors errors) {
    ServiceSchema ss = this.serviceSchemaService.findOne(schemaId, null);
    Boolean valid = false;
    try {      
      valid = this.jsonSchemaValidator.isJsonValid(ss.getSchemaString(), instance);
    } catch (Exception e) {
      log.warn("Exception during schema validation", e);
    }
    if (!valid) {
      errors.rejectValue("instance", "Validation of instance against schema failed");      
    }
  }

}
