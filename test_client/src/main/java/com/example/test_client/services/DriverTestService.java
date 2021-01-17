package com.example.test_client.services;

import com.example.grpc.*;
import com.example.test_client.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.*;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DriverTestService {
    private BearerToken bearerToken;
    private final ManagedChannel channel;
    //    private final String url = "http://192.168.49.2:30985";
    private final String host = "localhost";
    private final int port = 9090;

    public DriverTestService() {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public void testDriverRegistration(String carId) {
        GatewayRegistrationServiceGrpc.GatewayRegistrationServiceBlockingStub stub;
        stub = GatewayRegistrationServiceGrpc.newBlockingStub(this.channel);

        UserResponse registrationResponse = stub.register(UserRequest.newBuilder()
                .setFirstName("first")
                .setLastName("second")
                .setEmail("second@example.com")
                .setPassword("testpassword")
                .setRole(com.example.grpc.Role.DRIVER)
                .setCarId(carId)
                .build());

        System.out.println(registrationResponse);
    }

    public void testLoginDriver() {
        GatewayLoginServiceGrpc.GatewayLoginServiceBlockingStub stub = GatewayLoginServiceGrpc
                .newBlockingStub(channel);
        LoginResponse response = stub.login(LoginRequest.newBuilder()
                .setEmail("second@example.com")
                .setPassword("testpassword")
                .build());

        String token = response.getToken();
        System.out.println(token);
        this.bearerToken = new BearerToken(token);
    }

    public void testGetOrder() {
        GatewayOrderServiceGrpc.GatewayOrderServiceBlockingStub stub = GatewayOrderServiceGrpc
                .newBlockingStub(channel)
                .withCallCredentials(this.bearerToken);

        GetOrderResponse orderResponse = stub.get(GetOrderRequest.newBuilder()
                .build());

        System.out.println(orderResponse);
    }
}
