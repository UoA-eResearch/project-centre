package nz.ac.auckland.eresearch.projectcentre.types.api;

import javax.validation.constraints.NotNull;


public class ProjectExternalReferenceGet extends ProjectExternalReferencePost {

  @NotNull
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
