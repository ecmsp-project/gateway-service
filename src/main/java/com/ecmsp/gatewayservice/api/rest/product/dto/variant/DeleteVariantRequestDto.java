package com.ecmsp.gatewayservice.api.rest.product.dto.variant;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DeleteVariantRequestDto(
        UUID id
) {
}
