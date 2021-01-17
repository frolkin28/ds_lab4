package com.example.taxi_app.grpc;

import io.grpc.Context;
import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constants {
    public static final String JWT_SIGNING_KEY = System.getenv("API_SECRET_KEY");;
    public static final String BEARER_TYPE = "Bearer";

    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> EMAIL_CONTEXT_KEY = Context.key("email");
    public static final Context.Key<String> ROLE_CONTEXT_KEY = Context.key("role");

    private Constants() {
        throw new AssertionError();
    }
}