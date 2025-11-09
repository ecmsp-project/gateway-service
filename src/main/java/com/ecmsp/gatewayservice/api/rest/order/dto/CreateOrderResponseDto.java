package com.ecmsp.gatewayservice.api.rest.order.dto;

import java.util.List;

public record CreateOrderResponseDto(
        boolean isSuccess,
        String orderId,
        List<String> reservedVariantIds,
        List<FailedReservationVariantDto> failedVariants
) {
}
