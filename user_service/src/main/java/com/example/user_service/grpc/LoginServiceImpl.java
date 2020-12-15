package com.example.user_service.grpc;

import com.example.grpc.LoginResponse;
import com.example.grpc.LoginRequest;
import com.example.grpc.LoginServiceGrpc.LoginServiceImplBase;
import com.example.user_service.models.User;
import com.example.user_service.services.TokenService;
import com.example.user_service.services.UserService;
import com.example.user_service.services.enums.Role;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class LoginServiceImpl extends LoginServiceImplBase {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public LoginServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String email = request.getEmail();
        String password = request.getPassword();

        User validatedUser = userService.validate(email, password);
        String response;

        if (validatedUser != null) {
            Role role = validatedUser.getRole();
            response = tokenService.generateJWTToken(validatedUser.getEmail(), role);
        } else {
            response = "invalid credentials";
        }

        LoginResponse grpcResponse = LoginResponse.newBuilder()
                .setToken(response)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();

    }
}