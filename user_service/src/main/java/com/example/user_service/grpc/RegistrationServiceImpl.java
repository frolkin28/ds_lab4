package com.example.user_service.grpc;


import com.example.grpc.UserRequest;
import com.example.grpc.UserResponse;
import com.example.grpc.RegistrationServiceGrpc.RegistrationServiceImplBase;

import com.example.user_service.services.enums.Role;
import com.example.user_service.exceptions.InvalidEmail;
import com.example.user_service.models.User;
import com.example.user_service.services.RegistrationService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


@GRpcService
public class RegistrationServiceImpl extends RegistrationServiceImplBase {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationServiceImpl(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void register(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        String message;
        Role role;
        User user;
        if (request.getRole().toString().equals(Role.DRIVER.toString())) {
            role = Role.DRIVER;
            user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), role, request.getCarId());
        }
        else {
            role = Role.USER;
            user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), role);
        }

        try {
            registrationService.registerUser(user);
            message = "User has been successfully registered";
        } catch (InvalidEmail e) {
            message = e.getMessage();
        } catch (DataIntegrityViolationException e) {
            message = "User already exists";
        }

        UserResponse response = UserResponse.newBuilder()
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

