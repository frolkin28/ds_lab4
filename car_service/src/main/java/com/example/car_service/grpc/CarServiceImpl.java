package com.example.car_service.grpc;

import com.example.car_service.models.Car;
import com.example.car_service.services.CarService;
import com.example.grpc.CarRequest;
import com.example.grpc.CarResponse;
import com.example.grpc.CarServiceGrpc.CarServiceImplBase;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class CarServiceImpl extends CarServiceImplBase {
    private final CarService carService;

    @Autowired
    public CarServiceImpl(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void create(CarRequest request, StreamObserver<CarResponse> responseObserver) {
        Car requestCar = new Car(request.getNumber(), request.getDescription());
        Car savedCar = carService.add(requestCar);

        CarResponse grpcResponse = CarResponse.newBuilder()
                .setId(savedCar.getId().toString())
                .setNumber(savedCar.getNumber())
                .setDescription(savedCar.getDescription())
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}
