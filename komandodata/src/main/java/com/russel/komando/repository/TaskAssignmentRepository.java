package com.russel.komando.repository;

import com.russel.komando.entity.TaskAssignmentData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskAssignmentRepository extends CrudRepository<TaskAssignmentData, Integer> {
    List<TaskAssignmentData> findByTask_TaskId(int taskId);
    List<TaskAssignmentData> findByEmployee_EmployeeId(int employeeId);
    void deleteByTask_TaskId(Integer taskId);
    void deleteByEmployee_EmployeeId(Integer employeeId);
}
