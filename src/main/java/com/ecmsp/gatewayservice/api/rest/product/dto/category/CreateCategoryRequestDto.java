package com.ecmsp.gatewayservice.api.rest.product.dto.category;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateCategoryRequestDto(
        String name,
        UUID parentCategoryId,
        UUID childCategoryId,
        CreateCategoryType createCategoryType
) {
}
