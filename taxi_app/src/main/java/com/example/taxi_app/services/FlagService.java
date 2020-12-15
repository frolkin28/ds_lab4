package com.example.taxi_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlagService {
    public int REST_COMMUNICATION;
    public int GRPC_COMMUNICATION;

    @Autowired
    public FlagService() {
        this.REST_COMMUNICATION = Integer.parseInt(System.getenv("REST_COMMUNICATION"));
        this.GRPC_COMMUNICATION = Integer.parseInt(System.getenv("GRPC_COMMUNICATION"));
    }

    public boolean getGRPC_Flag() {
        return GRPC_COMMUNICATION == 1;
    }

    public boolean getREST_Flag() {
        return REST_COMMUNICATION == 1;
    }
}
