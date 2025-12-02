package com.ecmsp.gatewayservice.api.grpc.product.property;

import com.ecmsp.gatewayservice.api.rest.product.dto.property.CreatePropertyRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.CreatePropertyResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.DeletePropertyRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.DeletePropertyResponseDto;
import com.ecmsp.product.v1.property.v1.*;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PropertyGrpcMapper {

    public CreatePropertyRequest toGrpcCreatePropertyRequest(CreatePropertyRequestDto request) {
        PropertyRole propertyRole = switch (request.role()) {
            case SELECTABLE -> PropertyRole.PROPERTY_ROLE_SELECTABLE;
            case REQUIRED -> PropertyRole.PROPERTY_ROLE_REQUIRED;
            case INFO -> PropertyRole.PROPERTY_ROLE_INFO_UNSPECIFIED;
        };

        return CreatePropertyRequest.newBuilder()
                .setCategoryId(request.categoryId().toString())
                .setName(request.name())
                .setUnit(request.unit())
                .setDataType(request.dataType())
                .setRole(propertyRole)
                .addAllDefaultPropertyOptionValues(
                        request.defaultPropertyOptionValues().stream()
                                .map(option -> DefaultPropertyOptionValue.newBuilder()
                                        .setDisplayText(option)
                                        .build())
                                .toList()
                )
                .build();
    }

    public CreatePropertyResponseDto toCreatePropertyResponse(CreatePropertyResponse grpcResponse) {
        UUID propertyId = UUID.fromString(grpcResponse.getId());

        return CreatePropertyResponseDto.builder()
                .id(propertyId)
                .build();
    }

    public DeletePropertyRequest toGrpcDeletePropertyRequest(DeletePropertyRequestDto request) {
        return DeletePropertyRequest.newBuilder()
                .setId(request.id().toString())
                .build();
    }

    public DeletePropertyResponseDto toDeletePropertyResponse(DeletePropertyResponse grpcResponse) {
        return new DeletePropertyResponseDto();
    }
}