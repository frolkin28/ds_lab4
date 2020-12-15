package com.example.order_service.entities;

import com.example.order_service.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private UUID id;

    private float cost;

    private Location start;

    private Location destination;

    private User driver;

    private User customer;

    private Car car;

    public OrderResponse(Location start, Location destination, Order order, User driver, User customer, Car car) {
        this.id = order.getId();
        this.cost = order.getCost();
        this.start = start;
        this.destination = destination;
        this.driver = driver;
        this.customer = customer;
        this.car = car;
    }
}