package com.example.order_service.services;

import com.example.order_service.entities.Location;
import org.springframework.stereotype.Service;

import java.lang.Math;

@Service
public class CostService {
    private static final float price_per_m = 0.01f;
    private static final int earth_radius = 6371;

    public float calculate(Location from, Location destination) {
        double latDistance = Math.toRadians(destination.getLatitude() - from.getLongitude());
        double lonDistance = Math.toRadians(destination.getLongitude() - from.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(from.getLatitude()))
                * Math.cos(Math.toRadians(destination.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * earth_radius;

        return (float)(distance * price_per_m);
    }
}