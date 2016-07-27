package nz.ac.auckland.eresearch.projectcentre.validation;

import nz.ac.auckland.eresearch.projectcentre.service.ServiceService;
import nz.ac.auckland.eresearch.projectcentre.types.entity.ServiceOwner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServiceOwnerValidator implements Validator {

  protected Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  ValidationUtil validationUtil;
  @Autowired
  ServiceService serviceService;

  @Override
  public boolean supports(Class<?> clazz) {
    return ServiceOwner.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object object, Errors errors) {
    ServiceOwner si = (ServiceOwner) object;
    validationUtil.checkNotEmpty(errors, new String[]{"ldapGroup", "serviceId"});
    if (!errors.hasFieldErrors()) {
      if (null == serviceService.findOne(si.getServiceId(), null)) {
        errors.rejectValue("serviceId", "Invalid service id");
      }
    }
  }
  
}
