package com.example.taxi_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Car implements Serializable {
    private UUID id;
    private String number;
    private String description;

    public Car(String number, String description) {
        this.number = number;
        this.description = description;
    }
}
