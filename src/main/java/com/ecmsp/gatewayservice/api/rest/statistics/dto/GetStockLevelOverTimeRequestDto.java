package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record GetStockLevelOverTimeRequestDto(
        String variantId,
        String fromDate,
        String toDate,
        Integer trendDays
) {
}
