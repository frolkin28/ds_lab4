package com.example.taxi_app.grpc;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;


@GRpcService(interceptors = {AuthorizationInterceptor.class})
public class CarGrpcService extends GatewayCarServiceGrpc.GatewayCarServiceImplBase {
    private final ManagedChannel channel;
    private final CarServiceGrpc.CarServiceBlockingStub stub;
    private final String carHost = System.getenv("CAR_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public CarGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(carHost, port)
                .usePlaintext()
                .build();
        this.stub = CarServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void create(CarRequest request, StreamObserver<CarResponse> responseObserver) {
        CarResponse carResponse = stub.create(request);
        responseObserver.onNext(carResponse);
        responseObserver.onCompleted();
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
