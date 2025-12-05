package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record StockDataPointDto(
        String date,
        int stockLevel
) {
}
