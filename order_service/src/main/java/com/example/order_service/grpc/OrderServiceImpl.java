package com.example.order_service.grpc;

import com.example.grpc.CreateOrderRequest;
import com.example.grpc.CreateOrderResponse;
import com.example.grpc.GetOrderRequest;
import com.example.grpc.GetOrderResponse;
import com.example.grpc.UserRequest;
import com.example.grpc.LocationResponse;
import com.example.grpc.CarResponse;
import com.example.grpc.OrderServiceGrpc.OrderServiceImplBase;
import com.example.order_service.entities.OrderResponse;
import com.example.order_service.entities.User;
import com.example.order_service.models.Order;
import com.example.order_service.services.OrderService;
import com.example.order_service.services.UserService;
import com.example.order_service.services.enums.Role;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class OrderServiceImpl extends OrderServiceImplBase {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public void create(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        String email = request.getEmail();

        Order order = new Order(request.getStartId(), request.getDestinationId());

        if (email != null) {
            User user = userService.getByEmail(email);
            Order createdOrder = orderService.createOrder(order, user.getId());


            CreateOrderResponse grpcResponse = CreateOrderResponse.newBuilder()
                    .setId(createdOrder.getId().toString())
                    .setCost(createdOrder.getCost())
                    .setCustomerId(createdOrder.getCustomerId())
                    .setStartId(createdOrder.getStartId())
                    .setDestinationId(createdOrder.getDestinationId())
                    .build();

            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void get(GetOrderRequest request, StreamObserver<GetOrderResponse> responseObserver) {
        String email = (String) request.getEmail();
        System.out.println("Email: ");
        System.out.println(email);
        User driver = userService.getByEmail(email);
        Role role = driver.getRole();

        if (role == Role.DRIVER) {
            OrderResponse order = orderService.getOrder(driver);

            UserRequest customer = UserRequest.newBuilder()
                    .setId(order.getCustomer().getId())
                    .setFirstName(order.getCustomer().getFirstName())
                    .setLastName(order.getCustomer().getLastName())
                    .setEmail(order.getCustomer().getEmail())
                    .setRole(com.example.grpc.Role.USER)
                    .build();

            UserRequest foundDriver = UserRequest.newBuilder()
                    .setId(driver.getId())
                    .setFirstName(driver.getFirstName())
                    .setLastName(driver.getLastName())
                    .setEmail(driver.getEmail())
                    .setRole(com.example.grpc.Role.DRIVER)
                    .build();

            LocationResponse start = LocationResponse.newBuilder()
                    .setId(order.getStart().getId())
                    .setLatitude(order.getStart().getLatitude())
                    .setLongitude(order.getStart().getLongitude())
                    .setTitle(order.getStart().getTitle())
                    .build();

            LocationResponse destination = LocationResponse.newBuilder()
                    .setId(order.getDestination().getId())
                    .setLatitude(order.getDestination().getLatitude())
                    .setLongitude(order.getDestination().getLongitude())
                    .setTitle(order.getDestination().getTitle())
                    .build();

            CarResponse car = CarResponse.newBuilder()
                    .setId(order.getCar().getId())
                    .setNumber(order.getCar().getNumber())
                    .setDescription(order.getCar().getDescription())
                    .build();

            GetOrderResponse grpcResponse = GetOrderResponse.newBuilder()
                    .setId(order.getId().toString())
                    .setCost(order.getCost())
                    .setCustomer(customer)
                    .setDriver(foundDriver)
                    .setStart(start)
                    .setDestination(destination)
                    .setCar(car)
                    .build();

            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();
        }

    }
}
