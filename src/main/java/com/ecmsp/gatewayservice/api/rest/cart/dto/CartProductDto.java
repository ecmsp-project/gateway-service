package com.ecmsp.gatewayservice.api.rest.cart.dto;

import java.util.UUID;

public record CartProductDto(UUID productId, Integer quantity) {
}
