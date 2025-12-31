package com.ecmsp.gatewayservice.api.grpc.statistics;

import com.ecmsp.gatewayservice.api.rest.statistics.dto.*;
import com.ecmsp.statistics.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticsGrpcMapper {

    public GetAvailableVariantsRequest toGrpcGetAvailableVariantsRequest() {
        return GetAvailableVariantsRequest.newBuilder().build();
    }

    public GetAvailableVariantsResponseDto toGetAvailableVariantsResponse(GetAvailableVariantsResponse grpcResponse) {
        List<VariantInfoDto> variants = grpcResponse.getVariantsList().stream()
                .map(this::toVariantInfoDto)
                .toList();

        return new GetAvailableVariantsResponseDto(variants);
    }

    public GetVariantSalesOverTimeRequest toGrpcGetVariantSalesOverTimeRequest(GetVariantSalesOverTimeRequestDto requestDto) {
        GetVariantSalesOverTimeRequest.Builder builder = GetVariantSalesOverTimeRequest.newBuilder()
                .setVariantId(requestDto.variantId())
                .setFromDate(requestDto.fromDate())
                .setToDate(requestDto.toDate());

        if (requestDto.trendDays() != null) {
            builder.setTrendDays(requestDto.trendDays());
        }

        return builder.build();
    }

    public GetVariantSalesOverTimeResponseDto toGetVariantSalesOverTimeResponse(GetVariantSalesOverTimeResponse grpcResponse) {
        VariantSalesOverTimeDto salesData = toVariantSalesOverTimeDto(grpcResponse.getSalesData());
        return new GetVariantSalesOverTimeResponseDto(salesData);
    }

    public GetStockLevelOverTimeRequest toGrpcGetStockLevelOverTimeRequest(GetStockLevelOverTimeRequestDto requestDto) {
        GetStockLevelOverTimeRequest.Builder builder = GetStockLevelOverTimeRequest.newBuilder()
                .setVariantId(requestDto.variantId())
                .setFromDate(requestDto.fromDate())
                .setToDate(requestDto.toDate());

        if (requestDto.trendDays() != null) {
            builder.setTrendDays(requestDto.trendDays());
        }

        return builder.build();
    }

    public GetStockLevelOverTimeResponseDto toGetStockLevelOverTimeResponse(GetStockLevelOverTimeResponse grpcResponse) {
        StockLevelOverTimeDto stockData = toStockLevelOverTimeDto(grpcResponse.getStockData());
        return new GetStockLevelOverTimeResponseDto(stockData);
    }

    private VariantInfoDto toVariantInfoDto(VariantInfo grpcVariantInfo) {
        return new VariantInfoDto(
                grpcVariantInfo.getVariantId(),
                grpcVariantInfo.getProductId(),
                grpcVariantInfo.getProductName(),
                grpcVariantInfo.getHasSalesData(),
                grpcVariantInfo.getHasStockData(),
                grpcVariantInfo.getLastSaleDate().isEmpty() ? null : grpcVariantInfo.getLastSaleDate(),
                grpcVariantInfo.hasCurrentStock() ? grpcVariantInfo.getCurrentStock() : null
        );
    }

    private VariantSalesOverTimeDto toVariantSalesOverTimeDto(VariantSalesOverTime grpcSalesData) {
        List<SalesDataPointDto> dataPoints = grpcSalesData.getDataPointsList().stream()
                .map(this::toSalesDataPointDto)
                .toList();

        List<LinearRegressionLineDto> regressionLines = grpcSalesData.getRegressionLinesList().stream()
                .map(this::toLinearRegressionLineDto)
                .toList();

        return new VariantSalesOverTimeDto(
                grpcSalesData.getVariantId(),
                grpcSalesData.getProductName().isEmpty() ? null : grpcSalesData.getProductName(),
                dataPoints,
                regressionLines
        );
    }

    private StockLevelOverTimeDto toStockLevelOverTimeDto(StockLevelOverTime grpcStockData) {
        List<StockDataPointDto> dataPoints = grpcStockData.getDataPointsList().stream()
                .map(this::toStockDataPointDto)
                .toList();

        List<LinearRegressionLineDto> regressionLines = grpcStockData.getRegressionLinesList().stream()
                .map(this::toLinearRegressionLineDto)
                .toList();

        LinearRegressionLineDto trendLine = grpcStockData.hasTrendLine() ?
                toLinearRegressionLineDto(grpcStockData.getTrendLine()) : null;

        return new StockLevelOverTimeDto(
                grpcStockData.getVariantId(),
                grpcStockData.getProductName().isEmpty() ? null : grpcStockData.getProductName(),
                dataPoints,
                regressionLines,
                trendLine
        );
    }

    private SalesDataPointDto toSalesDataPointDto(SalesDataPoint grpcDataPoint) {
        return new SalesDataPointDto(
                grpcDataPoint.getDate(),
                grpcDataPoint.getQuantity(),
                grpcDataPoint.getTotalRevenue().getValue()
        );
    }

    private StockDataPointDto toStockDataPointDto(StockDataPoint grpcDataPoint) {
        return new StockDataPointDto(
                grpcDataPoint.getDate(),
                grpcDataPoint.getStockLevel()
        );
    }

    private LinearRegressionLineDto toLinearRegressionLineDto(LinearRegressionLine grpcLine) {
        return new LinearRegressionLineDto(
                grpcLine.getSlope(),
                grpcLine.getIntercept(),
                grpcLine.getValidFrom().isEmpty() ? null : grpcLine.getValidFrom(),
                grpcLine.getValidTo().isEmpty() ? null : grpcLine.getValidTo(),
                grpcLine.getEstimatedDepletionDate().isEmpty() ? null : grpcLine.getEstimatedDepletionDate(),
                grpcLine.getRSquared()
        );
    }
}
