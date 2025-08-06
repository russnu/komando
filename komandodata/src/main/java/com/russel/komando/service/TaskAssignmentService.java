package com.russel.komando.service;

import com.russel.komando.model.TaskAssignment;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TaskAssignmentService {

    TaskAssignment create(TaskAssignment taskAssignment);
    void deleteByTask(Integer taskId);
    void deleteByEmployee(Integer employeeId);
}

