package com.ecmsp.gatewayservice.api.grpc.product.variant;

import com.ecmsp.gatewayservice.api.rest.product.dto.variant.CreateVariantRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.CreateVariantResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.DeleteVariantRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.DeleteVariantResponseDto;
import com.ecmsp.product.v1.variant.v1.CreateVariantRequest;
import com.ecmsp.product.v1.variant.v1.CreateVariantResponse;
import com.ecmsp.product.v1.variant.v1.DeleteVariantRequest;
import com.ecmsp.product.v1.variant.v1.DeleteVariantResponse;
import com.ecmsp.product.v1.variant.v1.VariantPropertyValue;
import com.google.type.Decimal;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class VariantGrpcMapper {

    public CreateVariantRequest toGrpcCreateVariantRequest(CreateVariantRequestDto request) {
        Decimal price = Decimal.newBuilder()
                .setValue(request.price().toString())
                .build();

        return CreateVariantRequest.newBuilder()
                .setProductId(request.productId().toString())
                .setPrice(price)
                .setStockQuantity(request.stockQuantity())
                .setDescription(request.description())
                .addAllVariantImages(request.variantImages())
                .addAllVariantPropertyValues(
                        request.variantPropertyValues().stream()
                                .map(dto -> VariantPropertyValue.newBuilder()
                                        .setPropertyId(dto.propertyId().toString())
                                        .setDisplayText(dto.displayText())
                                        .build())
                                .toList()
                )
                .build();
    }

    public CreateVariantResponseDto toCreateVariantResponse(CreateVariantResponse grpcResponse) {
        UUID variantId = UUID.fromString(grpcResponse.getId());

        return CreateVariantResponseDto.builder()
                .id(variantId)
                .build();
    }

    public DeleteVariantRequest toGrpcDeleteVariantRequest(DeleteVariantRequestDto request) {
        return DeleteVariantRequest.newBuilder()
                .setId(request.id().toString())
                .build();
    }

    public DeleteVariantResponseDto toDeleteVariantResponse(DeleteVariantResponse grpcResponse) {
        return new DeleteVariantResponseDto();
    }
}
