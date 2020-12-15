package com.example.order_service.services;

import com.example.order_service.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {
    private final RestTemplate restTemplate;

    @Autowired
    public LocationService() {
        this.restTemplate = new RestTemplate();
    }

    public Location getById(String id) {
        String locationDomain = System.getenv("LOCATION_SERVICE_HOST");
        String locationPort = System.getenv("HTTP_PORT");
        String url = String.format("http://%s:%s/api/internal/location/%s", locationDomain, locationPort, id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Location> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, Location.class);
        return response.getBody();
    }

}
