package com.russel.komando.uiservice;

import com.russel.komando.uimodel.Employee;
import com.russel.komando.uimodel.Task;
import com.russel.komando.uimodel.TaskAssignment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TaskAssignmentService {
    private final String BASE_URL = "http://localhost:8080/api/task-assignments";
    private final RestTemplate restTemplate = new RestTemplate();
    //================================================================================//
    public void assignEmployeesToTask(Task task, List<Employee> employees) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Employee emp : employees) {
            TaskAssignment assignment = new TaskAssignment();
            assignment.setTask(task);
            assignment.setEmployee(emp);

            HttpEntity<TaskAssignment> entity = new HttpEntity<>(assignment, headers);

            restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<TaskAssignment>() {}
            );
        }
    }
    //================================================================================//
    public void assignEmployeeToTasks(Employee employee, List<Task> tasks) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Task task : tasks) {
            TaskAssignment assignment = new TaskAssignment();
            assignment.setTask(task);
            assignment.setEmployee(employee);

            HttpEntity<TaskAssignment> entity = new HttpEntity<>(assignment, headers);

            restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<TaskAssignment>() {}
            );
        }
    }
    //================================================================================//
    public void removeAssignmentsByTaskId(int taskId) {
        String url = BASE_URL + "/task/" + taskId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }
    //================================================================================//
    public void removeAssignmentsByEmployeeId(int employeeId) {
        String url = BASE_URL + "/employee/" + employeeId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }

}
