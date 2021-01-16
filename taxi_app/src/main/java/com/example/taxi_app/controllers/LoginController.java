package com.example.taxi_app.controllers;

import com.example.taxi_app.entities.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String userDomain = System.getenv("USER_SERVICE_HOST");
    private final String userPort = System.getenv("HTTP_PORT");

    @PostMapping
    public HttpEntity<Map> login(@RequestBody User userMap) {

        String url = String.format("http://%s:%s/login", this.userDomain, this.userPort);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(userMap, headers);
        ResponseEntity<Map> response = this.restTemplate.postForEntity(url, entity, Map.class);
        return response;

    }
}
