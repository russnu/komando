package com.russel.komando.repository;
import com.russel.komando.entity.ProjectData;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<ProjectData, Integer> {
}
