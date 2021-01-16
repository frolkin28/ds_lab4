package com.example.test_client.services;

import com.example.test_client.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DriverTestService {
    private String token;
    private final RestTemplate restTemplate;
//    private final String url = "http://192.168.49.2:30985";
    private final String url = "http://127.0.0.1:8090";
    private final ObjectMapper objectMapper;
    private final HttpHeaders headers;

    public DriverTestService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public void testDriverRegistration(UUID carId) {
        try {
            User driver = new User("first", "second", "second@example.com", "testpassword", Role.DRIVER, carId);
            String encodedDriver = this.objectMapper.writeValueAsString(driver);
            HttpEntity<String> httpRequest = new HttpEntity<>(encodedDriver, this.headers);
            ResponseEntity<String> response = this.restTemplate.postForEntity(this.url + "/register", httpRequest, String.class);
            System.out.println("Testing driver registration");
            System.out.println(response.getBody());
        }
        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testLoginDriver() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", "second@example.com");
        map.put("password", "testpassword");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<TokenResponse> response = this.restTemplate.postForEntity(this.url + "/login", entity, TokenResponse.class);
        this.token = response.getBody().getToken();
        System.out.println("Testing driver login");
        System.out.println(this.token);
    }

    public void testGetOrder() {
        headers.set("Authorization", this.token);
        HttpEntity<Order> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url + "/api/v1/order", HttpMethod.GET, entity, String.class);
        System.out.println("Testing order getting");
//        System.out.println(response.getBody());
    }
}
