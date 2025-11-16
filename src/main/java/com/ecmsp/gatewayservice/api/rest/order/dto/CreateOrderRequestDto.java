package com.ecmsp.gatewayservice.api.rest.order.dto;

import java.util.List;

public record CreateOrderRequestDto(
        List<CreateOrderItemDto> items
) {
}
