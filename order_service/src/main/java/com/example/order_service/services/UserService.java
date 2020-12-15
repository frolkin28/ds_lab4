package com.example.order_service.services;

import com.example.order_service.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final RestTemplate restTemplate;
    private final String userDomain = System.getenv("USER_SERVICE_HOST");
    private final String userPort = System.getenv("HTTP_PORT");

    @Autowired
    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public User getByEmail(String email) {
        String url = String.format("http://%s:%s/api/internal/user/email/%s", userDomain, userPort, email);
        return makeRequest(url);
    }

    public User getById(String id) {
        String url = String.format("http://%s:%s/api/internal/user/id/%s", userDomain, userPort, id);
        return makeRequest(url);
    }

    public User makeRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<User> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

}
