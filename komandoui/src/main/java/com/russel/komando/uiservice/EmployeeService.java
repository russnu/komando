package com.russel.komando.uiservice;

import com.russel.komando.base.BaseService;
import com.russel.komando.uimodel.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

public class EmployeeService extends BaseService<Employee> {

    @Override
    protected String getApiUrl() {
        return "http://localhost:8080/api/employees";
    }

    @Override
    protected ParameterizedTypeReference<List<Employee>> getListType() {
        return new ParameterizedTypeReference<>() {};
    }

    @Override
    protected ParameterizedTypeReference<Employee> getSingleType() {
        return new ParameterizedTypeReference<>() {};
    }

    public List<Employee> getEmployeesByProjectId(int projectId) {
        String url = "http://localhost:8080/api/projects/" + projectId + "/employees";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
