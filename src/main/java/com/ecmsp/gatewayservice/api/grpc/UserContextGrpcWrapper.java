package com.ecmsp.gatewayservice.api.grpc;

import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class UserContextGrpcWrapper extends CallCredentials {

    private final String userId;
    private final String login;

    public UserContextGrpcWrapper(UserContextWrapper userContextWrapper) {
        this.userId = userContextWrapper.getUserId();
        this.login = userContextWrapper.getLogin();
    }


    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("X-User-ID", Metadata.ASCII_STRING_MARSHALLER), userId);
        metadata.put(Metadata.Key.of("X-Login", Metadata.ASCII_STRING_MARSHALLER), login);
        applier.apply(metadata);
    }
}
