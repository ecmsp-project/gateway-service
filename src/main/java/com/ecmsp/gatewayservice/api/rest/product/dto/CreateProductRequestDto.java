package com.ecmsp.gatewayservice.api.rest.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreateProductRequestDto(
        UUID categoryId,
        String name,
        BigDecimal approximatePrice,
        BigDecimal deliveryPrice,
        String description
) {
}
