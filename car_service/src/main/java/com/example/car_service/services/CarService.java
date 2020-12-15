package com.example.car_service.services;

import com.example.car_service.models.Car;
import com.example.car_service.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car add(Car car) {
        return carRepository.save(car);
    }

    public Iterable<Car> getAll() {
        return carRepository.findAll();
    }

    public void remove(UUID id) {
        carRepository.deleteById(id);
    }

    public Car getById(UUID id) { return carRepository.findById(id); }
}
