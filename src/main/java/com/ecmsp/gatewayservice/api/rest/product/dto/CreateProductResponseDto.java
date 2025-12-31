package com.ecmsp.gatewayservice.api.rest.product.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateProductResponseDto(
        UUID id
) {
}
