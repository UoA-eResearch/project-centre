package nz.ac.auckland.eresearch.projectcentre.entity;

/**
 * Created by markus on 10/11/15.
 */
public interface IHierarchyElement {

  IHierarchyElement getParent();

  Short getLevel();

  void setLevel(Short level);

  IHierarchyElement getTop();

  void setTop(IHierarchyElement top);
}
