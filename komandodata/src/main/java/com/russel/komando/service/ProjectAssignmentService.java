package com.russel.komando.service;

import com.russel.komando.model.ProjectAssignment;
import java.util.List;

public interface ProjectAssignmentService {
    ProjectAssignment create(ProjectAssignment projectAssignment);
    void deleteByProject(Integer projectId);
    void deleteByEmployee(Integer employeeId);

    //    List<ProjectAssignment> fetchAll();
    //    ProjectAssignment fetch(Integer id);
    //    ProjectAssignment update(ProjectAssignment projectAssignment);

}
