package com.example.test_client.services;

import com.example.test_client.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class UserTestService {
    private String token;
    private final RestTemplate restTemplate;
//    private final String url = "http://192.168.49.2:30985";
    private final String url = "http://127.0.0.1:8090";
    private final ObjectMapper objectMapper;
    private final HttpHeaders headers;

    public UserTestService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public void testUserRegistration() {
        try {
            User user = new User("first", "second", "first@example.com", "password", Role.USER);
            String encodedUser = this.objectMapper.writeValueAsString(user);
            HttpEntity<String> httpRequest = new HttpEntity<>(encodedUser, this.headers);
            ResponseEntity<String> response = this.restTemplate.postForEntity(this.url + "/register", httpRequest, String.class);
            System.out.println("Testing user registration");
            System.out.println(response.getBody());
        }
        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testLogin() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", "first@example.com");
        map.put("password", "password");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<TokenResponse> response = this.restTemplate.postForEntity(this.url + "/login", entity, TokenResponse.class);
        System.out.println(response.getBody());
        this.token = response.getBody().getToken();
        System.out.println("Testing user login");
        System.out.println(this.token);
    }

    public Car testCarCreation() {
        Car car = new Car("BB4058AC", "Lada 2105");
        this.headers.set("Authorization", this.token);
        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
        ResponseEntity<Car> response = this.restTemplate.postForEntity(url + "/api/v1/car", entity, Car.class);
        System.out.println("Testing car creation");
        System.out.println(response.getBody());
        return response.getBody();
    }

    public Location testCreateLocation(Location location) {
        headers.set("Authorization", this.token);
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
        ResponseEntity<Location> response = this.restTemplate.postForEntity(url + "/api/v1/location", entity, Location.class);
        System.out.println("Testing location creation");
        System.out.println(response.getBody());
        return response.getBody();
    }

    public void testCreateOrder(Order order) {
        headers.set("Authorization", this.token);
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        ResponseEntity<Order> response = this.restTemplate.postForEntity(url + "/api/v1/order", entity, Order.class);
        System.out.println("Testing order creation");
        System.out.println(response.getBody());
    }
}
