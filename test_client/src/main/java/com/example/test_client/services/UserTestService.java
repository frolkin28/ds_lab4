package com.example.test_client.services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.grpc.*;

public class UserTestService {
    private String token;
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
        GatewayLoginServiceGrpc.GatewayLoginServiceBlockingStub stub = GatewayLoginServiceGrpc.newBlockingStub(channel);
        LoginResponse response = stub.login(LoginRequest.newBuilder()
                .setEmail("first@example.com")
                .setPassword("password")
                .build());

        this.token = response.getToken();
        System.out.println(this.token);
    }

//    public Car testCarCreation() {
//        Car car = new Car("BB4058AC", "Lada 2105");
//        this.headers.set("Authorization", this.token);
//        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
//        ResponseEntity<Car> response = this.restTemplate.postForEntity(url + "/api/v1/car", entity, Car.class);
//        System.out.println("Testing car creation");
//        System.out.println(response.getBody());
//        return response.getBody();
//    }
//
//    public Location testCreateLocation(Location location) {
//        headers.set("Authorization", this.token);
//        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
//        ResponseEntity<Location> response = this.restTemplate.postForEntity(url + "/api/v1/location", entity, Location.class);
//        System.out.println("Testing location creation");
//        System.out.println(response.getBody());
//        return response.getBody();
//    }
//
//    public void testCreateOrder(Order order) {
//        headers.set("Authorization", this.token);
//        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
//        ResponseEntity<Order> response = this.restTemplate.postForEntity(url + "/api/v1/order", entity, Order.class);
//        System.out.println("Testing order creation");
//        System.out.println(response.getBody());
//    }
}

