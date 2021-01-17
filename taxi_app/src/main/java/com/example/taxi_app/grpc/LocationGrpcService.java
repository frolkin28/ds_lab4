package com.example.taxi_app.grpc;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;


@GRpcService(interceptors = {AuthorizationInterceptor.class})
public class LocationGrpcService extends GatewayLocationServiceGrpc.GatewayLocationServiceImplBase {
    private final ManagedChannel channel;
    private final LocationServiceGrpc.LocationServiceBlockingStub stub;
    private final String locationHost = System.getenv("LOCATION_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public LocationGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(locationHost, port)
                .usePlaintext()
                .build();
        this.stub = LocationServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void create(LocationRequest request, StreamObserver<LocationResponse> responseObserver) {
        LocationResponse locationResponse = stub.create(request);
        responseObserver.onNext(locationResponse);
        responseObserver.onCompleted();
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
