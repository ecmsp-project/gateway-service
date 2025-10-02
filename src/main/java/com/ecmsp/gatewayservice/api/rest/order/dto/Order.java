package com.ecmsp.gatewayservice.api.rest.order.dto;

import java.util.List;

public record Order(String orderId, String clientId, String orderStatus, String date, List<OrderItem> items) {
}
