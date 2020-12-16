package com.example.taxi_app.services;

import com.example.grpc.*;
import com.example.taxi_app.entities.Car;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CarGrpcService {
    private final ManagedChannel channel;
    private final CarServiceGrpc.CarServiceBlockingStub stub;
    private final String carHost = System.getenv("CAR_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public CarGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(carHost, port)
                .usePlaintext()
                .build();
        this.stub = CarServiceGrpc.newBlockingStub(channel);
    }

    public CarResponse create(Car car) {
        CarResponse carResponse = stub.create(CarRequest.newBuilder()
                .setNumber(car.getNumber())
                .setDescription(car.getDescription())
                .build());

        closeConnection();
        return carResponse;
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
