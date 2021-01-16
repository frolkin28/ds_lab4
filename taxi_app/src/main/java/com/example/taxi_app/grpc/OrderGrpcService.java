package com.example.taxi_app.grpc;

import com.example.grpc.*;
import com.example.taxi_app.entities.Order;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderGrpcService {
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

    public CreateOrderResponse create(Order order, String email) {
        CreateOrderResponse orderResponse = stub.create(CreateOrderRequest.newBuilder()
                .setStartId(order.getStartId())
                .setDestinationId(order.getDestinationId())
                .setEmail(email)
                .build());

        closeConnection();
        return orderResponse;
    }

    public GetOrderResponse get(String email) {
        GetOrderResponse orderResponse = stub.get(GetOrderRequest.newBuilder()
                .setEmail(email)
                .build());

        closeConnection();
        return orderResponse;
    }

    public void closeConnection() {
        this.channel.shutdown();
    }
}
