package com.example.order_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@DynamicUpdate
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private float cost;

    private String startId;

    private String destinationId;

    private String driverId;

    private String customerId;

    public Order(String startId, String destinationId, float cost, String customerId) {
        super();
        this.startId = startId;
        this.destinationId = destinationId;
        this.cost = cost;
        this.customerId = customerId;
    }

    public Order(String startId, String destinationId) {
        super();
        this.startId = startId;
        this.destinationId = destinationId;
    }
}
