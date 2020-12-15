package com.example.taxi_app.controllers;

import com.example.taxi_app.entities.Car;
import com.example.taxi_app.entities.Order;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String orderDomain = System.getenv("ORDER_SERVICE_HOST");
    private final String orderPort = System.getenv("HTTP_PORT");

    @GetMapping
    public ResponseEntity<String> getOrder(HttpServletRequest request) {
        String url = String.format("http://%s:%s/api/v1/order", this.orderDomain, this.orderPort);
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(HttpServletRequest request, @RequestBody Order order) {
        String url = String.format("http://%s:%s/api/v1/order", this.orderDomain, this.orderPort);
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
        return response;
    }
}
