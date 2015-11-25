package nz.ac.auckland.eresearch.projectcentre.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import nz.ac.auckland.eresearch.projectcentre.entity.Division;
import nz.ac.auckland.eresearch.projectcentre.listeners.DivisionShadowTableHelper;
import nz.ac.auckland.eresearch.projectcentre.repositories.DivisionRepository;
import nz.ac.auckland.eresearch.projectcentre.util.auth.Authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class DivisionService extends BaseService<Division> {

  @Autowired
  private DivisionRepository repo;

  @Autowired
  private DivisionShadowTableHelper shadow;

  @PreAuthorize(Authz.AUTHENTICATED)
  @Cacheable(value = "DivisionCache", key = "#id")
  public Division findOne(Integer id) {
    return repo.findOne(id);
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Iterable<Division> findAll() {
    return repo.findAll();
  }

  @PreAuthorize(Authz.AUTHENTICATED)
  public Division findById(Integer id) {
    return repo.findOne(id);
 }

  @PreAuthorize(Authz.ADMIN)
  public Division create(Division entity) {
    Division newDiv = repo.save(entity);
    return newDiv;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#entity.getId()")
  public Division update(Division entity) throws Exception {
    Division div = repo.saveAndFlush(entity);
//    shadow.updateDivisionChildren(div);
    return div;
  }

  @PreAuthorize(Authz.ADMIN)
  @CacheEvict(value = "DivisionCache", key = "#id")
  public void delete(Integer id) {
    repo.delete(id);
  }

//  @PreAuthorize(Authz.AUTHENTICATED)
  public List<Division> filterOutNonExististingDivisions(Collection<String> allDivisionCodes) {

    if (allDivisionCodes == null || allDivisionCodes.size() == 0 ) {
      return Lists.newArrayList();
    }
    return repo.filterOutNonExistingDivisions(allDivisionCodes);

  }

  /**
   * Takes a list of division codes, filters out unknown ones, and returns a set of "top" {@link Division}s.
   *
   * "Top" divisions are those who don't have any children amongst the initiall provided list.
   *
   * @param divisionsToCheck the list of division codes
   * @return a set of top-level Divisions
   */
  public Set<Division> filterTopMostMemberDivisions(Collection<String> divisionsToCheck) {
    List<Division> filteredDivs = filterOutNonExististingDivisions(divisionsToCheck);
    return getTopMostMemberDivisions(filteredDivs);
  }

//  @PreAuthorize(Authz.AUTHENTICATED)
  /**
   * Takes a list of divisions, and returns a set of "top" {@link Division}s.
   *
   * "Top" divisions are those who don't have any children amongst the initiall provided list.
   *
   * @param divisionsToCheck the list of division codes
   * @return a set of top-level Divisions
   */
  public Set<Division> getTopMostMemberDivisions(Collection<Division> divisionsToCheck) {

    if ( divisionsToCheck.size() == 0 || divisionsToCheck.size() == 1 ) {
      return Sets.newHashSet(divisionsToCheck);
    }

    Set<Division> filtered = Sets.newHashSet(divisionsToCheck);
    for ( Division d1 : divisionsToCheck ) {
      for ( Division d2 : divisionsToCheck ) {
        if ( d1.equals(d2) ) {
          continue;
        }
        if ( isChildOf(d2, d1) ) {
          filtered.remove(d1);
          break;
        }
      }
    }

    return filtered;

  }

  public boolean isChildOf(Division child, Division parent) {

    Division temp = child;
    while ( temp.getParent() != null ) {
      if ( temp.getParent().equals(parent) ) {
        return true;
      }
      temp = temp.getParent();
    }

    return false;

  }

  public Division findByCode(String code) {
    return repo.findByCode(code);
  }
}

