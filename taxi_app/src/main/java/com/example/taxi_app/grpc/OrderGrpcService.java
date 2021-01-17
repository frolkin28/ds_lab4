package com.example.taxi_app.grpc;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;


@GRpcService(interceptors = {AuthorizationInterceptor.class})
public class OrderGrpcService extends GatewayOrderServiceGrpc.GatewayOrderServiceImplBase {
    private final ManagedChannel channel;
    private final OrderServiceGrpc.OrderServiceBlockingStub stub;
    private final String orderHost = System.getenv("ORDER_SERVICE_HOST");
    private final int port = Integer.parseInt(System.getenv("GRPC_PORT"));

    public OrderGrpcService() {
        this.channel = ManagedChannelBuilder.forAddress(orderHost, port)
                .usePlaintext()
                .build();
        this.stub = OrderServiceGrpc.newBlockingStub(this.channel);
    }

    @Override
    public void create(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        String email = Constants.EMAIL_CONTEXT_KEY.get();
        CreateOrderResponse orderResponse = stub.create(CreateOrderRequest.newBuilder()
                .setStartId(request.getStartId())
                .setDestinationId(request.getDestinationId())
                .setEmail(email)
                .build());

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetOrderRequest request, StreamObserver<GetOrderResponse> responseObserver) {
        String email = Constants.EMAIL_CONTEXT_KEY.get();
        GetOrderResponse orderResponse = stub.get(GetOrderRequest.newBuilder()
                .setEmail(email)
                .build());

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }


    public void closeConnection() {
        this.channel.shutdown();
    }
}
