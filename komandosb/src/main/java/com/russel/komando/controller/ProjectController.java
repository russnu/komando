package com.russel.komando.controller;

import com.russel.komando.model.Employee;
import com.russel.komando.model.Project;
import com.russel.komando.model.Task;
import com.russel.komando.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/projects")
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    //========================================================================================================//
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.fetchAllProjects();
    }
    //========================================================================================================//
    @GetMapping("/{id}")
    public Project getProject(@PathVariable Integer id) {
        return projectService.fetchProject(id);
    }
    //========================================================================================================//
    @GetMapping("/{id}/tasks")
    public List<Task> getProjectTasks(@PathVariable Integer id) {
        return projectService.fetchProjectTasks(id);
    }
    //========================================================================================================//
    @GetMapping("/{id}/employees")
    public List<Employee> getProjectEmployees(@PathVariable Integer id) {
        return projectService.fetchProjectEmployees(id);
    }
    //========================================================================================================//
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody Project project) {
        return projectService.create(project);
    }
    //========================================================================================================//
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Integer id, @RequestBody Project project) {
        project.setProjectId(id);
        return projectService.update(project);
    }
    //========================================================================================================//
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.delete(id);
    }
    //========================================================================================================//
}
