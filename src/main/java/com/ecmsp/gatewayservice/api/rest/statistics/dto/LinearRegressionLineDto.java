package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record LinearRegressionLineDto(
        double slope,
        double intercept,
        String validFrom,
        String validTo,
        String estimatedDepletionDate,
        double rSquared
) {
}
