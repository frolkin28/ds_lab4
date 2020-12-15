package com.example.test_client.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
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
