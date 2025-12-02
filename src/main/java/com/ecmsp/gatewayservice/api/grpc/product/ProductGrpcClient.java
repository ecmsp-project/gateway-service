package com.ecmsp.gatewayservice.api.grpc.product;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.product.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class ProductGrpcClient {

    @GrpcClient("product-service")
    ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    public CreateProductResponse createProduct(CreateProductRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return productServiceStub
                .withCallCredentials(credentials)
                .createProduct(request);
    }

    public DeleteProductResponse deleteProduct(DeleteProductRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return productServiceStub
                .withCallCredentials(credentials)
                .deleteProduct(request);
    }
}
