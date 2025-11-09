package com.ecmsp.gatewayservice.api.grpc.order;

import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderResponseDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderItemDetailsDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderStatusResponseDto;
import com.ecmsp.order.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderGrpcMapper {

    public List<GetOrderResponseDto> toOrders(ListOrdersByUserIdResponse grpcResponse) {
        return grpcResponse.getOrdersList().stream()
                .map(this::toOrderFromResponse)
                .toList();
    }

    public List<GetOrderResponseDto> toOrders(ListOrdersResponse grpcResponse) {
        return grpcResponse.getOrdersList().stream()
                .map(this::toOrderFromResponse)
                .toList();
    }

    public GetOrderResponseDto toOrder(GetOrderResponse grpcResponse) {
        List<OrderItemDetailsDto> items = grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new GetOrderResponseDto(
                grpcResponse.getOrderId(),
                grpcResponse.getOrderStatus().name(),
                grpcResponse.getDate(),
                items,
                grpcResponse.getClientId()
        );
    }

    public List<OrderItemDetailsDto> toOrderItems(GetOrderItemsResponse grpcResponse) {
        return grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();
    }

    public GetOrderStatusResponseDto toOrderStatus(GetOrderStatusResponse grpcResponse) {
        return new GetOrderStatusResponseDto(
                grpcResponse.getOrderId(),
                grpcResponse.getOrderStatus().name()
        );
    }

    private GetOrderResponseDto toOrderFromResponse(GetOrderResponse grpcOrder) {
        List<OrderItemDetailsDto> items = grpcOrder.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new GetOrderResponseDto(
                grpcOrder.getOrderId(),
                grpcOrder.getOrderStatus().name(),
                grpcOrder.getDate(),
                items,
                grpcOrder.getClientId()
        );
    }

    private OrderItemDetailsDto toOrderItem(OrderItemDetails grpcItem) {
        return new OrderItemDetailsDto(
                grpcItem.getItemId(),
                grpcItem.getVariantId(),
                grpcItem.getQuantity(),
                grpcItem.getPrice(),
                grpcItem.getImageUrl(),
                grpcItem.getDescription(),
                grpcItem.getIsReturnable()
        );
    }
}
