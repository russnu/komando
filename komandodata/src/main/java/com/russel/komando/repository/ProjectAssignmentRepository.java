package com.russel.komando.repository;

import com.russel.komando.entity.ProjectAssignmentData;
import com.russel.komando.entity.TaskAssignmentData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectAssignmentRepository extends CrudRepository<ProjectAssignmentData, Integer> {
    List<ProjectAssignmentData> findByEmployee_EmployeeId(int employeeId);
    List<ProjectAssignmentData> findByProject_ProjectId(int projectId);
    void deleteByProject_ProjectId(Integer projectId);
    void deleteByEmployee_EmployeeId(Integer employeeId);
}
