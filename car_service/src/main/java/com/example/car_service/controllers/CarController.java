package com.example.car_service.controllers;

import com.example.car_service.models.Car;
import com.example.car_service.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("api/v1/car")
    public Iterable<Car> getAll() {
        return carService.getAll();
    }

    @GetMapping("api/internal/car/{id}")
    public ResponseEntity<Car> getById(@PathVariable(value = "id") UUID id) {
        Car car = carService.getById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("api/v1/car")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car saved_car = carService.add(car);
        return new ResponseEntity<>(saved_car, HttpStatus.CREATED);
    }

    @DeleteMapping("api/v1/car/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id ) {
        carService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}