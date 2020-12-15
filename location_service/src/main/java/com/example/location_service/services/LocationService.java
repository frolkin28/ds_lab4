package com.example.location_service.services;

import com.example.location_service.models.Location;
import com.example.location_service.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location add(Location location) {
        return locationRepository.save(location);
    }

    public Location getByTitle(String title) {
        return locationRepository.findByTitleContainingIgnoreCase(title);
    }

    public Location getById(UUID id) {
        return locationRepository.findById(id);
    }
}
