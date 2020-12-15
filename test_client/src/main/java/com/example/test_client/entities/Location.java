package com.example.test_client.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Location implements Serializable {
    private UUID id;
    private float latitude;
    private float longitude;
    private String title;

    public Location(float latitude, float longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }
}
