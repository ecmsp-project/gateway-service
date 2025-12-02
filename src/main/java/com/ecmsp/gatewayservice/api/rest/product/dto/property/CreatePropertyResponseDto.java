package com.ecmsp.gatewayservice.api.rest.product.dto.property;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreatePropertyResponseDto(
        UUID id
) {
}
