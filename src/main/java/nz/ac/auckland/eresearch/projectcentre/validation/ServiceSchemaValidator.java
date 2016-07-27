package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceSchemaService;
import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServiceSchemaValidator implements Validator {

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  ServiceService service;
  @Autowired
  ServiceSchemaService serviceSchemaService;

  @Override
  public boolean supports(Class<?> clazz) {
    return ServiceSchema.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object serviceSchema, Errors errors) {
    ServiceSchema ss = (ServiceSchema) serviceSchema;
    validationUtil.checkNotEmpty(errors, new String[]{"schemaString", "schemaVersion", "serviceId"});
    if (!errors.hasFieldErrors()) {
      if (null == service.findOne(ss.getServiceId(), null)) {
        errors.rejectValue("serviceId", "Invalid service id");
      }
      if (ss.getId() == null &&
          null != serviceSchemaService.findByServiceIdAndSchemaVersion(ss.getServiceId(),
          ss.getSchemaVersion())) {
        errors.rejectValue("serviceId", "schema version already exists for this service id");
      }
      // TODO: validate schema against meta schema
    }
  }

}
