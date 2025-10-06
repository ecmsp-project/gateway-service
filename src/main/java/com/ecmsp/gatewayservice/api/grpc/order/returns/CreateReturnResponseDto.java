package com.ecmsp.gatewayservice.api.grpc.order.returns;

public record CreateReturnResponseDto(
        String returnId,
        String returnStatus
) {
}
