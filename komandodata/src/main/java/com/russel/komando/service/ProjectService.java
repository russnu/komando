package com.russel.komando.service;

import com.russel.komando.model.Employee;
import com.russel.komando.model.Project;
import com.russel.komando.model.Task;

import java.util.List;

public interface ProjectService {
    List<Project> fetchAllProjects();
    Project fetchProject(Integer id);
    List<Task> fetchProjectTasks(Integer id);
    List<Employee> fetchProjectEmployees(Integer id);
    Project create(Project project);
    Project update(Project project);
    void delete(Integer id);
}
