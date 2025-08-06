package com.russel.komando.serviceimpl;

import com.russel.komando.entity.*;
import com.russel.komando.exception.NotFound;
import com.russel.komando.model.*;
import com.russel.komando.repository.EmployeeRepository;
import com.russel.komando.repository.ProjectAssignmentRepository;
import com.russel.komando.repository.TaskAssignmentRepository;
import com.russel.komando.service.EmployeeService;
import com.russel.komando.service.TaskService;
import com.russel.komando.util.EmployeeMapper;
import com.russel.komando.util.ProjectMapper;
import com.russel.komando.util.StatusMapper;
import com.russel.komando.util.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;
    @Autowired
    private TaskService taskService;
    //========================================================================================================//
    @Override
    public List<Employee> fetchAllEmployees() {
        List<EmployeeData> employeeDataList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeDataList::add);

        List<Employee> employees = new ArrayList<>();
        for (EmployeeData data : employeeDataList) {
            Employee employee = EmployeeMapper.toModel(data);
            employees.add(employee);
        }
        return employees;
    }
    //========================================================================================================//
    @Override
    public Employee fetchEmployee(Integer id) {
        EmployeeData data = employeeRepository.findById(id).orElseThrow(() -> new NotFound("Employee with ID " + id + " not found."));
        if (data == null) return null;

        Employee employee = EmployeeMapper.toModel(data);

        return employee;
    }
    //========================================================================================================//
    @Override
    public List<Task> fetchEmployeeTasks(Integer employeeId) {
        List<TaskAssignmentData> assignmentDataList = taskAssignmentRepository.findByEmployee_EmployeeId(employeeId);
        List<Task> tasks = new ArrayList<>();

        for(TaskAssignmentData assignment : assignmentDataList){
            List<Employee> assignedEmployees = taskService.fetchTaskEmployees(assignment.getTask().getTaskId());
            Task task = TaskMapper.toModel(assignment.getTask(), assignedEmployees);
            tasks.add(task);
        }

        return tasks;
    }
    //========================================================================================================//
    @Override
    public List<Project> fetchEmployeeProjects(Integer employeeId) {
        List<ProjectAssignmentData> assignmentDataList = projectAssignmentRepository.findByEmployee_EmployeeId(employeeId);
        List<Project> projects = new ArrayList<>();

        for(ProjectAssignmentData assignment : assignmentDataList){
            Project project = ProjectMapper.toModel(assignment.getProject());
            projects.add(project);
        }

        return projects;
    }
    //========================================================================================================//
    @Override
    public Employee create(Employee employee) {
        EmployeeData data = new EmployeeData();
        data.setFirstName(employee.getFirstName());
        data.setLastName(employee.getLastName());
        data.setEmployeeTitle(employee.getEmployeeTitle());

        EmployeeData saved = employeeRepository.save(data);
        employee.setEmployeeId(saved.getEmployeeId());
        return employee;
    }
    //========================================================================================================//
    @Override
    public Employee update(Employee employee) {
        EmployeeData data = employeeRepository.findById(employee.getEmployeeId()).orElseThrow(() -> new NotFound("Employee with ID " + employee.getEmployeeId() + " not found."));
        if (data == null) return null;

        data.setFirstName(employee.getFirstName());
        data.setLastName(employee.getLastName());
        data.setEmployeeTitle(employee.getEmployeeTitle());

        employeeRepository.save(data);
        return employee;
    }
    //========================================================================================================//
    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
    //========================================================================================================//
}
