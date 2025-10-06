package com.ecmsp.gatewayservice.api.grpc.order.returns;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.order.v1.returns.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class ReturnGrpcClient {

    @GrpcClient("order-service")
    private ReturnServiceGrpc.ReturnServiceBlockingStub returnServiceStub;

    public CreateReturnResponse createReturn(CreateReturnRequest request, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        ReturnServiceGrpc.ReturnServiceBlockingStub stubWithMetadata =
                returnServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.createReturn(request);
    }

    public GetReturnResponse getReturn(String returnId, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        ReturnServiceGrpc.ReturnServiceBlockingStub stubWithMetadata =
                returnServiceStub.withCallCredentials(credentials);

        GetReturnRequest request = GetReturnRequest.newBuilder()
                .setReturnId(returnId)
                .build();

        return stubWithMetadata.getReturn(request);
    }

    public ListReturnsResponse listReturns(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        ReturnServiceGrpc.ReturnServiceBlockingStub stubWithMetadata =
                returnServiceStub.withCallCredentials(credentials);

        ListReturnsRequest request = ListReturnsRequest.newBuilder()
                .build();

        return stubWithMetadata.listReturns(request);
    }

    public ListReturnsByUserIdResponse listReturnsByUserId(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        ReturnServiceGrpc.ReturnServiceBlockingStub stubWithMetadata =
                returnServiceStub.withCallCredentials(credentials);

        ListReturnsByUserIdRequest request = ListReturnsByUserIdRequest.newBuilder()
                .build();

        return stubWithMetadata.listReturnsByUserId(request);
    }
}
