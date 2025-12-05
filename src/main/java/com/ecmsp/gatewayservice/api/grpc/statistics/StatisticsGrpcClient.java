package com.ecmsp.gatewayservice.api.grpc.statistics;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.statistics.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class StatisticsGrpcClient {

    @GrpcClient("statistics-service")
    private StatisticsServiceGrpc.StatisticsServiceBlockingStub statisticsServiceStub;

    public GetAvailableVariantsResponse getAvailableVariants(GetAvailableVariantsRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        StatisticsServiceGrpc.StatisticsServiceBlockingStub stubWithMetadata =
                statisticsServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.getAvailableVariants(request);
    }

    public GetVariantSalesOverTimeResponse getVariantSalesOverTime(GetVariantSalesOverTimeRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        StatisticsServiceGrpc.StatisticsServiceBlockingStub stubWithMetadata =
                statisticsServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.getVariantSalesOverTime(request);
    }

    public GetStockLevelOverTimeResponse getStockLevelOverTime(GetStockLevelOverTimeRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        StatisticsServiceGrpc.StatisticsServiceBlockingStub stubWithMetadata =
                statisticsServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.getStockLevelOverTime(request);
    }
}
