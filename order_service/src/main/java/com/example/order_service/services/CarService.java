package com.example.order_service.services;

import com.example.order_service.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarService {
    private final RestTemplate restTemplate;

    @Autowired
    public CarService() {
        this.restTemplate = new RestTemplate();
    }

    public Car getById(String id) {
        String carDomain = System.getenv("CAR_SERVICE_HOST");
        String carPort = System.getenv("HTTP_PORT");
        String url = String.format("http://%s:%s/api/internal/car/%s", carDomain, carPort, id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Car> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, Car.class);
        return response.getBody();
    }

}
