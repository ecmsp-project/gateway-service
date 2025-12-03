package com.ecmsp.gatewayservice.api.grpc.product.property;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.product.v1.property.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class PropertyGrpcClient {

    @GrpcClient("product-service")
    PropertyServiceGrpc.PropertyServiceBlockingStub propertyServiceStub;

    public CreatePropertyResponse createProperty(CreatePropertyRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return propertyServiceStub
                .withCallCredentials(credentials)
                .createProperty(request);
    }

    public DeletePropertyResponse deleteProperty(DeletePropertyRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return propertyServiceStub
                .withCallCredentials(credentials)
                .deleteProperty(request);
    }
}