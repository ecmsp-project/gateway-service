package com.ecmsp.gatewayservice.api.grpc.order;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.order.v1.ListOrdersByUserIdRequest;
import com.ecmsp.order.v1.ListOrdersByUserIdResponse;
import com.ecmsp.order.v1.OrderServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class OrderGrpcClient {

    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceStub;

    public ListOrdersByUserIdResponse listOrdersByUserId(String userId, String login) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(userId, login);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
            orderServiceStub.withCallCredentials(credentials);


        //TODO: should be empty ListOrders and userId will be provided in metadata
        ListOrdersByUserIdRequest request = ListOrdersByUserIdRequest.newBuilder()
            .setUserId(userId)
            .build();

        return stubWithMetadata.listOrdersByUserId(request);
    }
}
