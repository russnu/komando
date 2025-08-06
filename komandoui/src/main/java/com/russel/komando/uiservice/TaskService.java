package com.russel.komando.uiservice;

import com.russel.komando.base.BaseService;
import com.russel.komando.uimodel.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

public class TaskService extends BaseService<Task> {
    @Override
    protected String getApiUrl() {
        return "http://localhost:8080/api/tasks";
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<List<Task>> getListType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<Task> getSingleType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    public List<Task> getTasksByProjectId(int projectId) {
        String url = "http://localhost:8080/api/projects/" + projectId + "/tasks";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Task>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
    //================================================================================//
    public List<Task> getTasksByEmployeeId(int employeeId) {
        String url = "http://localhost:8080/api/employees/" + employeeId + "/tasks";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Task>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
    //================================================================================//
    public Task createTask(Task task) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> entity = new HttpEntity<>(task, headers);

        ResponseEntity<Task> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Task>() {}
        );

        return response.getBody();
    }
    //================================================================================//
    public Task updateTask(Task task) {
        String url = getApiUrl() + "/" + task.getTaskId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> entity = new HttpEntity<>(task, headers);

        ResponseEntity<Task> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<Task>() {}
        );

        return response.getBody();
    }
}
