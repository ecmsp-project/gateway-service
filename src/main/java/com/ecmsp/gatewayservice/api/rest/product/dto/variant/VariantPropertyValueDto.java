package com.ecmsp.gatewayservice.api.rest.product.dto.variant;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VariantPropertyValueDto(
        UUID propertyId,
        String displayText
) {
}
