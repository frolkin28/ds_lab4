package com.example.location_service.repositories;

import com.example.location_service.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface LocationRepository extends CrudRepository<Location, String> {

    Location findByTitleContainingIgnoreCase(String title);

    Location findById(UUID id);
}
