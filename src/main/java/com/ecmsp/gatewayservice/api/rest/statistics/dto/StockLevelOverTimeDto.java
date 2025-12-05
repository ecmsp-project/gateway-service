package com.ecmsp.gatewayservice.api.rest.statistics.dto;

import java.util.List;

public record StockLevelOverTimeDto(
        String variantId,
        String productName,
        List<StockDataPointDto> dataPoints,
        List<LinearRegressionLineDto> regressionLines,
        LinearRegressionLineDto trendLine
) {
}
