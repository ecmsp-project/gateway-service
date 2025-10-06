package com.ecmsp.gatewayservice.api.rest.order.returns.dto;

public record ItemToReturnDetails(String itemId, String variantId, Integer quantity, String reason) {
}

