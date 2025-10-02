package com.ecmsp.gatewayservice.api.grpc;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class UserContextGrpcWrapper extends CallCredentials {

    private final String userId;
    private final String login;

    public UserContextGrpcWrapper(String userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("X-User-ID", Metadata.ASCII_STRING_MARSHALLER), userId);
        metadata.put(Metadata.Key.of("X-Login", Metadata.ASCII_STRING_MARSHALLER), login);
        applier.apply(metadata);
    }
}
