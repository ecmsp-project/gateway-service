package com.ecmsp.gatewayservice.api.rest.order.dto;

public record OrderItemDetailsDto(
        String itemId,
        String variantId,
        int quantity,
        double price,
        String imageUrl,
        String description,
        boolean isReturnable
) {
}
