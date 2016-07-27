package nz.ac.auckland.eresearch.projectcentre.repositories;

import nz.ac.auckland.eresearch.projectcentre.types.entity.ProjectMember;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectMemberRepository extends CrudRepository<ProjectMember, Integer> {

  List<ProjectMember> findByPersonId(Integer personId);

  List<ProjectMember> findByProjectId(Integer projectId);

  ProjectMember findByIdAndProjectId(Integer id, Integer projectId);

}
