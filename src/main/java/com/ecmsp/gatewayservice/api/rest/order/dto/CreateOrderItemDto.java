package com.ecmsp.gatewayservice.api.rest.order.dto;

public record CreateOrderItemDto(
        String itemId,
        String variantId,
        String name,
        int quantity,
        double price,
        String imageUrl,
        String description,
        boolean isReturnable
) {
}
