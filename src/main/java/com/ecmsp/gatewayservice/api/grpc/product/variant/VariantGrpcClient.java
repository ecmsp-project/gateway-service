package com.ecmsp.gatewayservice.api.grpc.product.variant;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.product.v1.variant.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class VariantGrpcClient {

    @GrpcClient("product-service")
    VariantServiceGrpc.VariantServiceBlockingStub variantServiceStub;

    public CreateVariantResponse createVariant(CreateVariantRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return variantServiceStub
                .withCallCredentials(credentials)
                .createVariant(request);
    }

    public DeleteVariantResponse deleteVariant(DeleteVariantRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return variantServiceStub
                .withCallCredentials(credentials)
                .deleteVariant(request);
    }
}
