package com.ecmsp.gatewayservice.api.rest.order.returns.dto;

import java.util.List;

public record ReturnToCreate(
        String orderId,
        List<ItemToReturnDetails> itemsToReturn
) {}
