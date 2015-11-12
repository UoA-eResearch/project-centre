package nz.ac.auckland.eresearch.projectcentre.listeners;

import nz.ac.auckland.eresearch.projectcentre.entity.IHierarchyElement;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by markus on 10/11/15.
 */
public class HierarchyListener {

  @PrePersist
  @PreUpdate
  public void setLevelAndTop(IHierarchyElement entity) {

    final IHierarchyElement parent = entity.getParent();

    // set level
    if (parent == null) {
      entity.setLevel((short) 0);
    } else {
      entity.setLevel((short) (parent.getLevel() + 1));
    }

    // set top
    if (parent == null) {
      entity.setTop(entity);
    } else {
      entity.setTop(parent.getTop());
    }
  }
}
