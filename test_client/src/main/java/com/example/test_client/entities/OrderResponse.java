package com.example.test_client.entities;

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

}