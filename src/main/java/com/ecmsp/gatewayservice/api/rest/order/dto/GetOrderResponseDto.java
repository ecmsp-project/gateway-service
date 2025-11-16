package com.ecmsp.gatewayservice.api.rest.order.dto;

import java.util.List;

public record GetOrderResponseDto(String orderId, String orderStatus, String date, List<OrderItemDetailsDto> items, String clientId) {
}
