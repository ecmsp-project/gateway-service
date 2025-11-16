package com.ecmsp.gatewayservice.api.rest.order.returns.dto;

public record CreateReturnResponseDto(
        String returnId,
        String returnStatus
) {
}
