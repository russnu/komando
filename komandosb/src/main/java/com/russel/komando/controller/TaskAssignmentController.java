package com.russel.komando.controller;

import com.russel.komando.model.TaskAssignment;
import com.russel.komando.service.TaskAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/task-assignments")
@RestController
public class TaskAssignmentController {
    @Autowired
    private TaskAssignmentService taskAssignmentService;
    //================================================================================//
    @PostMapping
    public TaskAssignment assign(@RequestBody TaskAssignment assignment) {
        return taskAssignmentService.create(assignment);
    }
    //================================================================================//
    @DeleteMapping("/task/{taskId}")
    public void removeByTask(@PathVariable Integer taskId) {
        taskAssignmentService.deleteByTask(taskId);
    }
    //================================================================================//
    @DeleteMapping("/employee/{employeeId}")
    public void removeByEmployee(@PathVariable Integer employeeId) {
        taskAssignmentService.deleteByEmployee(employeeId);
    }
}
