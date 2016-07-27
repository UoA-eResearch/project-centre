package nz.ac.auckland.eresearch.projectcentre.util;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class LocationUtil {

  public URI createLocation(HttpServletRequest request, Integer id) throws Exception {
    StringBuilder url = new StringBuilder(request.getRequestURL().toString());
    if (!url.toString().endsWith("/")) {
      url.append("/");
    }
    url.append(id);
    return new URI(url.toString());

  }

}
