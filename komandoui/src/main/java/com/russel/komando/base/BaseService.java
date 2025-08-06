package com.russel.komando.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class BaseService<T> {
    protected final RestTemplate restTemplate;

    public BaseService() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);

        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter);
    }
    //================================================================================//
    protected abstract String getApiUrl();
    protected abstract ParameterizedTypeReference<List<T>> getListType();
    protected abstract ParameterizedTypeReference<T> getSingleType();
    //================================================================================//
    public List<T> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<T>> response = restTemplate.exchange(
                getApiUrl(), HttpMethod.GET, entity, getListType()
        );
        return response.getBody();
    }
    //================================================================================//
    public T getById(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(
                getApiUrl() + "/" + id,
                HttpMethod.GET,
                entity,
                getSingleType()
        );

        return response.getBody();
    }
    //================================================================================//
    public void deleteById(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                getApiUrl() + "/" + id,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }
}
