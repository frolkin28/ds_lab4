package com.example.taxi_app.grpc;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class RegistrationGrpcService extends GatewayRegistrationServiceGrpc.GatewayRegistrationServiceImplBase {
    private final ManagedChannel channel;
    private final RegistrationServiceGrpc.RegistrationServiceBlockingStub stub;
    private final String userHost = System.getenv("USER_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));


    @Autowired
    public RegistrationGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(userHost, port)
                .usePlaintext()
                .build();
        this.stub = RegistrationServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void register(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse registrationResponse = stub.register(request);

        responseObserver.onNext(registrationResponse);
        responseObserver.onCompleted();
        closeConnection();
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
