package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record SalesDataPointDto(
        String date,
        int quantity,
        String totalRevenue
) {
}
