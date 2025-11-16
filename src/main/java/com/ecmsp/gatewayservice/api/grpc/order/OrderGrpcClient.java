package com.ecmsp.gatewayservice.api.grpc.order;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.grpc.exceptions.UnauthorizedException;
import com.ecmsp.gatewayservice.api.grpc.user.PermissionsEnum;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.order.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import static com.ecmsp.gatewayservice.api.grpc.user.PermissionsEnum.READ_ORDERS;

@Component
public class OrderGrpcClient {

    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceStub;


    public GetOrderResponse getOrder(String orderId, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
            orderServiceStub.withCallCredentials(credentials);

        GetOrderRequest request = GetOrderRequest.newBuilder()
            .setOrderId(orderId)
            .build();

        return stubWithMetadata.getOrder(request);
    }

    public GetOrderStatusResponse getOrderStatus(String orderId, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
                orderServiceStub.withCallCredentials(credentials);

        GetOrderStatusRequest request = GetOrderStatusRequest.newBuilder()
                .setOrderId(orderId)
                .build();

        return stubWithMetadata.getOrderStatus(request);
    }

    public GetOrderItemsResponse getOrderItems(String orderId, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
            orderServiceStub.withCallCredentials(credentials);

        GetOrderItemsRequest request = GetOrderItemsRequest.newBuilder()
            .setOrderId(orderId)
            .build();

        return stubWithMetadata.getOrderItems(request);
    }


    //TODO: should be used by admin
    public ListOrdersResponse listOrders(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
                orderServiceStub.withCallCredentials(credentials);

        ListOrdersRequest request = ListOrdersRequest.newBuilder()
                .build();

        return stubWithMetadata.listOrders(request);
    }



    public ListOrdersByUserIdResponse listOrdersByUserId(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
                orderServiceStub.withCallCredentials(credentials);

        ListOrdersByUserIdRequest request = ListOrdersByUserIdRequest.newBuilder()
                .build();

        return stubWithMetadata.listOrdersByUserId(request);
    }


    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        OrderServiceGrpc.OrderServiceBlockingStub stubWithMetadata =
                orderServiceStub.withCallCredentials(credentials);

        return stubWithMetadata.createOrder(createOrderRequest);
    }






}
