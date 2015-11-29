package nz.ac.auckland.eresearch.projectcentre.listeners;

import com.google.common.collect.Lists;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by markus on 19/11/15.
 */
@Component
public class DivisionShadowTableHelper {

  @Autowired
  private DivisionRepository divrepo;

  public Division updateDivisionChildren(Division entity){

    List<Division> divs_to_update;

    List<Division> childs = getChilds(entity, Lists.newArrayList());
    Set<Integer> childId = childs.stream().map(c -> c.getId()).collect(Collectors.toSet());

    entity.setChildId(childId);

    return entity;

  }

  private List<Division> getChilds(Division parent, List<Division> allChilds) {

    List<Division> childs = divrepo.findByParentId(parent.getId());
    if ( childs.size() == 0 ) {
      return allChilds;
    }
    for ( Division child : childs ) {
      allChilds.add(child);
      getChilds(child, allChilds);
    }

    return allChilds;

  }

}
