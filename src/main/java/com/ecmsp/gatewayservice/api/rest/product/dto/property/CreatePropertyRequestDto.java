package com.ecmsp.gatewayservice.api.rest.product.dto.property;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CreatePropertyRequestDto(
        UUID categoryId,
        String name,
        String unit,
        String dataType, // only TEXT is possible
        PropertyRole role,
        List<String> defaultPropertyOptionValues // as a display text, for example: M, L, XL or Red, Blue, Green
) {
}
