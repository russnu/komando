package com.russel.komando.uiservice;

import com.russel.komando.base.BaseService;
import com.russel.komando.uimodel.Status;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

public class StatusService extends BaseService<Status> {
    @Override
    protected String getApiUrl() {
        return "http://localhost:8080/api/statuses";
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<List<Status>> getListType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    @Override
    protected ParameterizedTypeReference<Status> getSingleType() {
        return new ParameterizedTypeReference<>() {};
    }
    //================================================================================//
    public Status createStatus(Status status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Status> entity = new HttpEntity<>(status, headers);

        ResponseEntity<Status> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }
    //================================================================================//
    public Status findByTitle(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Status> response = restTemplate.exchange(
                    getApiUrl() + "/title/" + title,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            // If status is not found, return null instead of throwing
            return null;
        }
    }
}
