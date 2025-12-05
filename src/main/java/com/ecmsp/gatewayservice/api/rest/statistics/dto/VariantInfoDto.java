package com.ecmsp.gatewayservice.api.rest.statistics.dto;

public record VariantInfoDto(
        String variantId,
        String productId,
        String productName,
        boolean hasSalesData,
        boolean hasStockData,
        String lastSaleDate,
        Integer currentStock
) {
}
