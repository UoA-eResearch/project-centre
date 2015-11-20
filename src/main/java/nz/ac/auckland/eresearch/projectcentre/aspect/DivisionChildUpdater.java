package nz.ac.auckland.eresearch.projectcentre.aspect;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.listeners.DivisionShadowTableHelper;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by markus on 20/11/15.
 */
@Aspect
@Component
public class DivisionChildUpdater {

  private ThreadLocal<Byte> flag = new ThreadLocal<>();

  @Autowired
  private DivisionShadowTableHelper shadow;
  @Autowired
  private DivisionRepository divrepo;

  @Around("execution(* nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository.save(*)) && args(div)")
  public Division recreateDivisionChildTable(ProceedingJoinPoint pjp, Division div) throws Throwable {

    if (flag.get() == null) {
      try {
        flag.set((byte) 1); // or 0, whatever
        // apply advice
        if ( div.getId() == null ) {
          div = (Division) pjp.proceed(new Object[]{div});
        }

        Division temp = div;
        shadow.updateDivisionChildren(temp);
        while ( temp.getParent() != null ) {
          temp = temp.getParent();
          shadow.updateDivisionChildren(temp);
          divrepo.save(temp);
        }

        return div;
      } finally {
        flag.remove();
      }
    } else {
      // we don't want to do recursion here.
      div = (Division) pjp.proceed(new Object[]{div});
      return div;
    }

  }
}
