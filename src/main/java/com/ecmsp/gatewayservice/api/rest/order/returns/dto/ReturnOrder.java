package com.ecmsp.gatewayservice.api.rest.order.returns.dto;

import com.ecmsp.order.v1.returns.v1.ReturnStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReturnOrder(
        String returnId,
        String orderId,
        List<ItemToReturnDetails> itemsToReturn,
        ReturnStatus status,
        LocalDateTime createdAt
) {
}
