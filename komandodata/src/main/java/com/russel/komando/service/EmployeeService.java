package com.russel.komando.service;

import com.russel.komando.model.*;

import java.util.List;

public interface EmployeeService {
    List<Employee> fetchAllEmployees();
    Employee fetchEmployee(Integer id);
    List<Task> fetchEmployeeTasks(Integer id);
    List<Project> fetchEmployeeProjects(Integer id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Integer id);
}
