package com.ecmsp.gatewayservice.api.grpc.product.category;

import com.ecmsp.gatewayservice.api.rest.product.dto.category.CreateCategoryRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.CreateCategoryResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.DeleteCategoryRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.DeleteCategoryResponseDto;
import com.ecmsp.product.v1.category.v1.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryGrpcMapper {

    public CreateCategoryRequest toGrpcCreateCategoryRequest(CreateCategoryRequestDto request) {
        CreateCategoryType createCategoryType = switch (request.createCategoryType()) {
            case LEAF -> CreateCategoryType.CREATE_CATEGORY_TYPE_LEAF_UNSPECIFIED;
            case SPLIT -> CreateCategoryType.CREATE_CATEGORY_TYPE_SPLIT;
            case ALL_SPLIT -> CreateCategoryType.CREATE_CATEGORY_TYPE_ALL_SPLIT;
        };

        return CreateCategoryRequest.newBuilder()
                .setName(request.name())
                .setParentCategoryId(request.parentCategoryId().toString())
                .setChildCategoryId(request.childCategoryId().toString())
                .setCreateCategoryType(createCategoryType)
                .build();
    }

    public CreateCategoryResponseDto toCreateCategoryResponse(CreateCategoryResponse grpcResponse) {
        UUID categoryId = UUID.fromString(grpcResponse.getId());

        return CreateCategoryResponseDto.builder()
                .id(categoryId)
                .build();
    }

    public DeleteCategoryRequest toGrpcDeleteCategoryRequest(DeleteCategoryRequestDto request) {
        return DeleteCategoryRequest.newBuilder()
                .setId(request.id().toString())
                .build();
    }

    public DeleteCategoryResponseDto toDeleteCategoryResponse(DeleteCategoryResponse grpcResponse) {
        return new DeleteCategoryResponseDto();
    }
}