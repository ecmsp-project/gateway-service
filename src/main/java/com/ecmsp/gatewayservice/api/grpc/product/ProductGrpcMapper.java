package com.ecmsp.gatewayservice.api.grpc.product;

import com.ecmsp.gatewayservice.api.rest.product.dto.CreateProductRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.CreateProductResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.DeleteProductRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.DeleteProductResponseDto;
import com.ecmsp.product.v1.CreateProductRequest;
import com.ecmsp.product.v1.CreateProductResponse;
import com.ecmsp.product.v1.DeleteProductRequest;
import com.ecmsp.product.v1.DeleteProductResponse;
import com.google.type.Decimal;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductGrpcMapper {

    public CreateProductRequest toGrpcCreateProductRequest(CreateProductRequestDto request) {
        Decimal approximatePrice = Decimal.newBuilder()
                .setValue(request.approximatePrice().toString())
                .build();

        Decimal deliveryPrice = Decimal.newBuilder()
                .setValue(request.deliveryPrice().toString())
                .build();

        return CreateProductRequest.newBuilder()
                .setCategoryId(request.categoryId().toString())
                .setName(request.name())
                .setApproximatePrice(approximatePrice)
                .setDeliveryPrice(deliveryPrice)
                .setDescription(request.description())
                .build();
    }

    public CreateProductResponseDto toCreateProductResponse(CreateProductResponse grpcResponse) {
        UUID productId = UUID.fromString(grpcResponse.getId());

        return CreateProductResponseDto.builder()
                .id(productId)
                .build();
    }

    public DeleteProductRequest toGrpcDeleteProductRequest(DeleteProductRequestDto request) {
        return DeleteProductRequest.newBuilder()
                .setId(request.id().toString())
                .build();
    }

    public DeleteProductResponseDto toDeleteProductResponse(DeleteProductResponse grpcResponse) {
        return new DeleteProductResponseDto();
    }
}
