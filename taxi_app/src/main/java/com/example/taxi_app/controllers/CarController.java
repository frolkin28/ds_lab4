package com.example.taxi_app.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import com.example.taxi_app.entities.Car;

import java.util.Map;

@RestController
@RequestMapping("api/v1/car")
public class CarController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String carDomain = System.getenv("CAR_SERVICE_HOST");
    private final String carPort = System.getenv("HTTP_PORT");

    @PostMapping
    public ResponseEntity<Map> addCar(@RequestBody Car car, HttpServletRequest request) {

        String url = String.format("http://%s:%s/api/v1/car", this.carDomain, this.carPort);
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
        ResponseEntity<Map> response = this.restTemplate.postForEntity(url, entity, Map.class);
        return response;
    }

}