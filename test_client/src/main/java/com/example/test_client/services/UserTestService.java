package com.example.test_client.services;

import com.example.test_client.entities.Location;
import com.example.test_client.entities.Order;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.grpc.*;

public class UserTestService {
    private BearerToken bearerToken;
    private final ManagedChannel channel;
    //    private final String url = "http://192.168.49.2:30985";
    private final String host = "localhost";
    private final int port = 9090;

    public UserTestService() {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public void testUserRegistration() {
        GatewayRegistrationServiceGrpc.GatewayRegistrationServiceBlockingStub stub;
        stub = GatewayRegistrationServiceGrpc.newBlockingStub(this.channel);

        UserResponse registrationResponse = stub.register(UserRequest.newBuilder()
                .setFirstName("first")
                .setLastName("second")
                .setEmail("first@example.com")
                .setPassword("password")
                .setRole(Role.USER)
                .build());

        System.out.println(registrationResponse);
    }

    public void closeConnection() {
        this.channel.shutdown();
    }


    public void testLogin() {
        GatewayLoginServiceGrpc.GatewayLoginServiceBlockingStub stub = GatewayLoginServiceGrpc
                .newBlockingStub(channel);
        LoginResponse response = stub.login(LoginRequest.newBuilder()
                .setEmail("first@example.com")
                .setPassword("password")
                .build());

        String token = response.getToken();
        System.out.println(token);
        this.bearerToken = new BearerToken(token);
    }

    public CarResponse testCarCreation() {
        GatewayCarServiceGrpc.GatewayCarServiceBlockingStub stub = GatewayCarServiceGrpc
                .newBlockingStub(channel)
                .withCallCredentials(this.bearerToken);

        CarResponse carResponse = stub.create(CarRequest.newBuilder()
                .setNumber("BB4058AC")
                .setDescription("Lada 2105")
                .build());

        System.out.println(carResponse);

        return carResponse;
    }

    public LocationResponse testCreateLocation(Location location) {
        GatewayLocationServiceGrpc.GatewayLocationServiceBlockingStub stub = GatewayLocationServiceGrpc
                .newBlockingStub(channel)
                .withCallCredentials(this.bearerToken);

        LocationResponse locationResponse = stub.create(LocationRequest.newBuilder()
                .setTitle(location.getTitle())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .build());

        System.out.println(locationResponse);
        return locationResponse;
    }

    public void testCreateOrder(Order order) {
        GatewayOrderServiceGrpc.GatewayOrderServiceBlockingStub stub = GatewayOrderServiceGrpc
                .newBlockingStub(channel)
                .withCallCredentials(this.bearerToken);

        CreateOrderResponse orderResponse = stub.create(CreateOrderRequest.newBuilder()
                .setStartId(order.getStartId())
                .setDestinationId(order.getDestinationId())
                .build());

        System.out.println(orderResponse);
    }
}

