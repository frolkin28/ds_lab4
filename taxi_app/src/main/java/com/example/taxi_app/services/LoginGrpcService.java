package com.example.taxi_app.services;

import com.example.grpc.*;
import com.example.taxi_app.entities.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginGrpcService {
    private final ManagedChannel channel;
    private final LoginServiceGrpc.LoginServiceBlockingStub stub;
    private final String userHost = System.getenv("USER_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    @Autowired
    public LoginGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(userHost, port)
                .usePlaintext()
                .build();
        this.stub = LoginServiceGrpc.newBlockingStub(channel);
    }

    public LoginResponse login(User user) {
        LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .build());

        closeConnection();
        return loginResponse;
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
