package com.example.location_service.controllers;

import com.example.location_service.models.Location;
import com.example.location_service.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/api/v1/location/{title}")
    public Location getLocationByTitle(@PathVariable(value = "title") String title){
        return locationService.getByTitle(title);
    }

    @GetMapping("/api/internal/location/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable(value = "id") UUID id) {
        Location location = locationService.getById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping("/api/v1/location")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location saved_location = locationService.add(location);
        return new ResponseEntity<>(saved_location, HttpStatus.CREATED);
    }

}