package com.example.taxi_app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private UUID id;
    private float cost;
    private String startId;
    private String destinationId;
    private String driverId;
    private String customerId;

    public Order(String startId, String destinationId) {
        this.startId = startId;
        this.destinationId = destinationId;
    }
}
