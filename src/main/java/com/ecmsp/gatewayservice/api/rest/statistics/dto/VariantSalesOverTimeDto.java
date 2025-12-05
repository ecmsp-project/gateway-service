package com.ecmsp.gatewayservice.api.rest.statistics.dto;

import java.util.List;

public record VariantSalesOverTimeDto(
        String variantId,
        String productName,
        List<SalesDataPointDto> dataPoints,
        List<LinearRegressionLineDto> regressionLines
) {
}
