package com.example.taxi_app.grpc;

import com.example.grpc.*;
import com.example.taxi_app.entities.Location;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class LocationGrpcService {
    private final ManagedChannel channel;
    private final LocationServiceGrpc.LocationServiceBlockingStub stub;
    private final String locationHost = System.getenv("LOCATION_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public LocationGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(locationHost, port)
                .usePlaintext()
                .build();
        this.stub = LocationServiceGrpc.newBlockingStub(channel);
    }

    public LocationResponse create(Location location) {
        LocationResponse locationResponse = stub.create(LocationRequest.newBuilder()
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setTitle(location.getTitle())
                .build());

        closeConnection();
        return locationResponse;
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
