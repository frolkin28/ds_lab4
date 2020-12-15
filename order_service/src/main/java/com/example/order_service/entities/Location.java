package com.example.order_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String id;

    private float latitude;
    private float longitude;

    private String title;
}
