package com.ecmsp.gatewayservice.api.rest.statistics;

import com.ecmsp.gatewayservice.api.grpc.statistics.StatisticsGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.statistics.StatisticsGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.statistics.dto.*;
import com.ecmsp.statistics.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsGrpcClient statisticsGrpcClient;
    private final StatisticsGrpcMapper statisticsGrpcMapper;

    public StatisticsController(StatisticsGrpcClient statisticsGrpcClient,
                                StatisticsGrpcMapper statisticsGrpcMapper) {
        this.statisticsGrpcClient = statisticsGrpcClient;
        this.statisticsGrpcMapper = statisticsGrpcMapper;
    }

    @GetMapping("/variants")
    public ResponseEntity<GetAvailableVariantsResponseDto> getAvailableVariants(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetAvailableVariantsRequest grpcRequest = statisticsGrpcMapper.toGrpcGetAvailableVariantsRequest();
        GetAvailableVariantsResponse grpcResponse = statisticsGrpcClient.getAvailableVariants(grpcRequest, wrapper);
        GetAvailableVariantsResponseDto response = statisticsGrpcMapper.toGetAvailableVariantsResponse(grpcResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/variants/{variantId}/sales")
    public ResponseEntity<GetVariantSalesOverTimeResponseDto> getVariantSalesOverTime(
            @PathVariable String variantId,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam(required = false) Integer trendDays,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetVariantSalesOverTimeRequestDto requestDto = new GetVariantSalesOverTimeRequestDto(
                variantId, fromDate, toDate, trendDays
        );
        GetVariantSalesOverTimeRequest grpcRequest = statisticsGrpcMapper.toGrpcGetVariantSalesOverTimeRequest(requestDto);
        GetVariantSalesOverTimeResponse grpcResponse = statisticsGrpcClient.getVariantSalesOverTime(grpcRequest, wrapper);
        GetVariantSalesOverTimeResponseDto response = statisticsGrpcMapper.toGetVariantSalesOverTimeResponse(grpcResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/variants/{variantId}/stock")
    public ResponseEntity<GetStockLevelOverTimeResponseDto> getStockLevelOverTime(
            @PathVariable String variantId,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam(required = false) Integer trendDays,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetStockLevelOverTimeRequestDto requestDto = new GetStockLevelOverTimeRequestDto(
                variantId, fromDate, toDate, trendDays
        );
        GetStockLevelOverTimeRequest grpcRequest = statisticsGrpcMapper.toGrpcGetStockLevelOverTimeRequest(requestDto);
        GetStockLevelOverTimeResponse grpcResponse = statisticsGrpcClient.getStockLevelOverTime(grpcRequest, wrapper);
        GetStockLevelOverTimeResponseDto response = statisticsGrpcMapper.toGetStockLevelOverTimeResponse(grpcResponse);

        return ResponseEntity.ok(response);
    }
}
