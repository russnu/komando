package com.russel.komando.controller;

import com.russel.komando.model.ProjectAssignment;
import com.russel.komando.model.TaskAssignment;
import com.russel.komando.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/project-assignments")
@RestController
public class ProjectAssignmentController {
    @Autowired
    private ProjectAssignmentService projectAssignmentService;
    //================================================================================//
    @PostMapping
    public ProjectAssignment assign(@RequestBody ProjectAssignment assignment) {
        return projectAssignmentService.create(assignment);
    }
    //================================================================================//
    @DeleteMapping("/project/{projectId}")
    public void removeByProject(@PathVariable Integer projectId) {
        projectAssignmentService.deleteByProject(projectId);
    }
    //================================================================================//
    @DeleteMapping("/employee/{employeeId}")
    public void removeByEmployee(@PathVariable Integer employeeId) {
        projectAssignmentService.deleteByEmployee(employeeId);
    }
}
