package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record GetVariantSalesOverTimeRequestDto(
        String variantId,
        String fromDate,
        String toDate,
        Integer trendDays
) {
}
