package com.example.taxi_app.grpc;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class LoginGrpcService extends GatewayLoginServiceGrpc.GatewayLoginServiceImplBase {
    private final ManagedChannel channel;
    private final LoginServiceGrpc.LoginServiceBlockingStub stub;
    private final String userHost = System.getenv("USER_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public LoginGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(userHost, port)
                .usePlaintext()
                .build();
        this.stub = LoginServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        LoginResponse loginResponse = stub.login(request);

        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();
        closeConnection();
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
