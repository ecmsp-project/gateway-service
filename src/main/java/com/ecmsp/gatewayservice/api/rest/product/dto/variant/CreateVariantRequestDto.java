package com.ecmsp.gatewayservice.api.rest.product.dto.variant;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record CreateVariantRequestDto(
        UUID productId,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        List<String> variantImages,
        List<VariantPropertyValueDto> variantPropertyValues
) {
}