package com.ecmsp.gatewayservice.api.grpc.product.category;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.product.v1.category.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class CategoryGrpcClient {

    @GrpcClient("product-service")
    CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceStub;

    public CreateCategoryResponse createCategory(CreateCategoryRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return categoryServiceStub
                .withCallCredentials(credentials)
                .createCategory(request);
    }

    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return categoryServiceStub
                .withCallCredentials(credentials)
                .deleteCategory(request);
    }
}