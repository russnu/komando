package com.russel.komando.uiservice;

import com.russel.komando.base.BaseService;
import com.russel.komando.uimodel.Project;
import com.russel.komando.uimodel.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

public class ProjectService extends BaseService<Project> {

    @Override
    protected String getApiUrl() {
        return "http://localhost:8080/api/projects";
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<List<Project>> getListType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<Project> getSingleType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    public List<Project> getProjectsByEmployeeId(int employeeId) {
        String url = "http://localhost:8080/api/employees/" + employeeId + "/projects";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Project>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
    //================================================================================//
    public Project createProject(Project project) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Project> entity = new HttpEntity<>(project, headers);

        ResponseEntity<Project> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Project>() {}
        );

        return response.getBody();
    }
    //================================================================================//
    public Project updateProject(Project project) {
        String url = getApiUrl() + "/" + project.getProjectId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Project> entity = new HttpEntity<>(project, headers);

        ResponseEntity<Project> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<Project>() {}
        );

        return response.getBody();
    }
}
