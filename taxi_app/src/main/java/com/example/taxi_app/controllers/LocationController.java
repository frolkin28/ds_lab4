package com.example.taxi_app.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.example.taxi_app.entities.Location;

@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String locationDomain = System.getenv("LOCATION_SERVICE_HOST");
    private final String locationPort = System.getenv("HTTP_PORT");


    @GetMapping("{title}")
    public ResponseEntity<String> getLocationByTitle(@PathVariable(value = "title") String title, HttpServletRequest request) {
        String url = String.format("http://%s:%s/api/v1/location/%s", this.locationDomain, this.locationDomain, title);
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    @PostMapping
    public ResponseEntity<Map> createLocation(@RequestBody Location location, HttpServletRequest request) {

        String url = String.format("http://%s:%s/api/v1/location", this.locationDomain, this.locationPort);
        HttpHeaders headers = new HttpHeaders();
        String token = request.getHeader("Authorization");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
        ResponseEntity<Map> response = this.restTemplate.postForEntity(url, entity, Map.class);
        return response;
    }

}