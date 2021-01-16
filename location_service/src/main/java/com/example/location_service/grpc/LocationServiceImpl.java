package com.example.location_service.grpc;

import com.example.grpc.LocationRequest;
import com.example.grpc.LocationResponse;
import com.example.grpc.LocationServiceGrpc.LocationServiceImplBase;
import com.example.location_service.models.Location;
import com.example.location_service.services.LocationService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class LocationServiceImpl extends LocationServiceImplBase {
    private final LocationService locationService;

    @Autowired
    public LocationServiceImpl(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void create(LocationRequest request, StreamObserver<LocationResponse> responseObserver) {
        Location location = new Location(request.getLatitude(), request.getLongitude(), request.getTitle());

        Location savedLocation = locationService.add(location);

        LocationResponse grpcResponse = LocationResponse.newBuilder()
                .setId(savedLocation.getId().toString())
                .setLatitude(savedLocation.getLatitude())
                .setLongitude(savedLocation.getLongitude())
                .setTitle(savedLocation.getTitle())
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}
