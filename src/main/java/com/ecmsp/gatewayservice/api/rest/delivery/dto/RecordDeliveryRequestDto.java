package com.ecmsp.gatewayservice.api.rest.delivery.dto;

import java.util.List;

public record RecordDeliveryRequestDto(List<DeliveryItemDto> items) {
}
