package com.example.taxi_app.services;

import com.example.grpc.RegistrationRequest;
import com.example.grpc.RegistrationResponse;
import com.example.grpc.RegistrationServiceGrpc;
import com.example.grpc.Role;
import com.example.taxi_app.entities.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationGRPCService {
    private final ManagedChannel channel;
    private final RegistrationServiceGrpc.RegistrationServiceBlockingStub stub;
    private final String userHost = System.getenv("USER_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    @Autowired
    public RegistrationGRPCService() {
        this.channel = ManagedChannelBuilder.forAddress(userHost, port)
                .usePlaintext()
                .build();
        this.stub = RegistrationServiceGrpc.newBlockingStub(channel);
    }

    public RegistrationResponse register(User user) {
        Role role;

        if (user.getRole() == com.example.taxi_app.entities.Role.DRIVER) {
            role = Role.DRIVER;
        }
        else {
            role = Role.USER;
        }

        RegistrationResponse registrationResponse = stub.register(RegistrationRequest.newBuilder()
                .setFirstName(user.getFirstName())
                .setLastName(user.getFirstName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(role)
                .build());

        closeConnection();
        return registrationResponse;
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
