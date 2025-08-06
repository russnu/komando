package com.russel.komando.controller;

import com.russel.komando.model.*;
import com.russel.komando.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //========================================================================================================//
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.fetchAllEmployees();
    }
    //========================================================================================================//
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Integer id) {
        return employeeService.fetchEmployee(id);
    }
    //========================================================================================================//
    @GetMapping("/{id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable Integer id) {
        return employeeService.fetchEmployeeTasks(id);
    }
    //========================================================================================================//
    @GetMapping("/{id}/projects")
    public List<Project> getEmployeeProjects(@PathVariable Integer id) {
        return employeeService.fetchEmployeeProjects(id);
    }
    //========================================================================================================//
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }
    //========================================================================================================//
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
    //========================================================================================================//
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.delete(id);
    }
    //========================================================================================================//
}
