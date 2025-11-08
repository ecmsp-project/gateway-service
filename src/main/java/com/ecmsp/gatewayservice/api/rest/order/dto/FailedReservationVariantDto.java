package com.ecmsp.gatewayservice.api.rest.order.dto;

public record FailedReservationVariantDto(
        String variantId,
        int requestedQuantity,
        int availableQuantity
) {
}
