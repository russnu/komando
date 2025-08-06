package com.russel.komando.uiservice;

import com.russel.komando.uimodel.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ProjectAssignmentService {
    private final String BASE_URL = "http://localhost:8080/api/project-assignments";
    private final RestTemplate restTemplate = new RestTemplate();
    //================================================================================//
    public void assignEmployeesToProject(Project project, List<Employee> employees) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Employee emp : employees) {
            ProjectAssignment assignment = new ProjectAssignment();
            assignment.setProject(project);
            assignment.setEmployee(emp);

            HttpEntity<ProjectAssignment> entity = new HttpEntity<>(assignment, headers);

            restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<TaskAssignment>() {}
            );
        }
    }
    //================================================================================//
    public void assignEmployeeToProjects(Employee employee, List<Project> projects) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Project proj : projects) {
            ProjectAssignment assignment = new ProjectAssignment();
            assignment.setProject(proj);
            assignment.setEmployee(employee);

            HttpEntity<ProjectAssignment> entity = new HttpEntity<>(assignment, headers);

            restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<TaskAssignment>() {}
            );
        }
    }
    //================================================================================//
    public void removeAssignmentsByProjectId(int projectId) {
        String url = BASE_URL + "/project/" + projectId;

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
