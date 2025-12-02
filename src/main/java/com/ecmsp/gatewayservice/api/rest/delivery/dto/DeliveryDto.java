package com.ecmsp.gatewayservice.api.rest.delivery.dto;

import java.util.List;

public record DeliveryDto(String deliveryId, List<DeliveryItemDto> items, String recordedAt) {
}
