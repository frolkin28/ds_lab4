package com.example.taxi_app.grpc;

import io.grpc.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthorizationInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String value = metadata.get(Constants.AUTHORIZATION_METADATA_KEY);

        Status status;
        if (value == null) {
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!value.startsWith(Constants.BEARER_TYPE)) {
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            try {
                String token = value.substring(Constants.BEARER_TYPE.length()).trim();
                Claims claims = Jwts.parser().setSigningKey(Constants.JWT_SIGNING_KEY)
                        .parseClaimsJws(token).getBody();
                String email = claims.get("email").toString();
                String role = claims.get("role").toString();

                Context ctx = Context.current().withValue(Constants.EMAIL_CONTEXT_KEY, email);
                ctx = ctx.withValue(Constants.ROLE_CONTEXT_KEY, role);
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            } catch (Exception e) {
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
        }

        serverCall.close(status, metadata);
        return new ServerCall.Listener<>() {};
    }
}
